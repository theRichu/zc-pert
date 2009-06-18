package com.ziscloud.zcdiagram.draw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dao.DrawMetaDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.pojo.Project;

/**
 * User: shunyunwang Date: 2009-3-25 Time: 17:21:36
 */
public class FindAllRoute {
	private List<Route> routeList = new ArrayList<Route>();
	private List<NodeAndPeriod> nodePeriodList = new ArrayList<NodeAndPeriod>();
	private HashMap<Integer, List<EndAndPeriod>> targetsForSource = new HashMap<Integer, List<EndAndPeriod>>();
	private int firstNodeId;

	public FindAllRoute(Project project, int firstNodeId, int model) {
		this.firstNodeId = firstNodeId;
		// 从数据库中查询出所有工序的节点及工期信息
		nodePeriodList.addAll(new DrawMetaDAO().findAllNodeAndPeriod(project,
				model));
		// 找出所有开始节点相应的结束节点
		findEndsForStart();
		// printTargetAndSource();
	}

	public List<Route> find() {
		// 建立所需要的栈
		Stack<NodeAndPeriod> sourceAndCurrentStack = new Stack<NodeAndPeriod>();
		sourceAndCurrentStack.push(new NodeAndPeriod(0, firstNodeId, 0));
		Route currentRoute = new Route(0);
		int maxTotalPeriod = 0;
		while (!sourceAndCurrentStack.empty()) {
			// 出栈
			NodeAndPeriod sourceAndCurrent = sourceAndCurrentStack.pop();
			// 重新计算当前路线的总工期
			int period = sourceAndCurrent.getPeriod();
			currentRoute.addPeriod(period);
			// 将节点加入的路线当中
			int current = sourceAndCurrent.getCurrent();
			currentRoute.addNode(new EndAndPeriod(current, period));
			// 将节点的对应的结束节点入栈
			if (null != targetsForSource.get(current)) {
				List<EndAndPeriod> targets = targetsForSource.get(current);
				for (EndAndPeriod tp : targets) {
					sourceAndCurrentStack.push(new NodeAndPeriod(current, tp
							.getEnd(), tp.getPeriod()));
				}
			}
			if (!sourceAndCurrentStack.empty()
					&& null == targetsForSource.get(current)) {
				// 更新关键路线标志位
				if (currentRoute.getTotalPeriod() > maxTotalPeriod) {
					maxTotalPeriod = currentRoute.getTotalPeriod();
				}
				routeList.add(currentRoute);
				currentRoute = currentRoute.copy(currentRoute.getId() + 1,
						sourceAndCurrentStack.peek().getStart());
			}
		}
		// 根据最后一条路线的总工期，更新关键路线标志位
		if (currentRoute.getTotalPeriod() > maxTotalPeriod) {
			maxTotalPeriod = currentRoute.getTotalPeriod();
		}
		// 将最后一条路线添加到集合中
		routeList.add(currentRoute);
		// 判断关键路线，如果是则设置相应的标志位
		for (Route route : routeList) {
			if (route.getTotalPeriod() == maxTotalPeriod) {
				route.setCritical(true);
				List<EndAndPeriod> nodelist = route.getNodeList();
				DrawMetaDAO drawMetaDAO = new DrawMetaDAO();
				Transaction tx = null;
				try {
					tx = SessionFactory.getSession().beginTransaction();
					for (int i = 0; i < nodelist.size() - 1; i++) {
						drawMetaDAO.updateCoordinate("true", nodelist.get(i)
								.getEnd(), nodelist.get(i + 1).getEnd());
					}
					tx.commit();
				} catch (HibernateException he) {
					if (null != tx)
						tx.rollback();
					throw new RuntimeException("更新关键工序是出错！", he);
				} finally {
					SessionFactory.closeSession();
				}
			}
		}
		// printNodeAndPeriod();
		// printRoute();
		// Collections.sort(routeList);
		return routeList;
	}

	/**
	 * 找出所有开始节点相应的结束节点
	 */
	private void findEndsForStart() {
		for (NodeAndPeriod np : nodePeriodList) {
			// 如果已经存在np的结束节点集合
			if (targetsForSource.containsKey(np.getStart())) {
				// 取得np的结束节点集合
				List<EndAndPeriod> targets = targetsForSource
						.get(np.getStart());
				EndAndPeriod tp = new EndAndPeriod(np.getEnd(), np.getPeriod());
				// 如果此结束节点并未包含在结束节点集合中，那么就将其添加到集合中
				if (!targets.contains(tp)) {
					targets.add(tp);
				}
			} else {
				// 如果np的结束节点集合不存在，那么就新建一个集合，同事添加当前结束节点到集合中
				List<EndAndPeriod> targets = new ArrayList<EndAndPeriod>();
				targets.add(new EndAndPeriod(np.getEnd(), np.getPeriod()));
				targetsForSource.put(np.getStart(), targets);
			}
		}
	}

	public HashMap<Integer, List<EndAndPeriod>> getEndsForStart() {
		return targetsForSource;
	}

	@SuppressWarnings("unused")
	private void printRoute() {
		for (Route route : routeList) {
			System.out.println(route);
		}
	}

	@SuppressWarnings("unused")
	private void printNodeAndPeriod() {
		for (NodeAndPeriod np : nodePeriodList) {
			System.out.print("Source DrawNode:" + np.getStart());
			System.out.print(",");
			System.out.print("Target DrawNode:" + np.getEnd());
			System.out.print(",");
			System.out.println("Period:" + np.getPeriod());
		}
	}

	@SuppressWarnings("unused")
	private void printTargetAndSource() {
		for (Integer i : targetsForSource.keySet()) {
			System.out.print("source:" + i + "targets:");
			for (EndAndPeriod tp : targetsForSource.get(i)) {
				System.out.print(tp.getEnd() + ", period:" + tp.getPeriod());
			}
			System.out.println();
		}
	}

}