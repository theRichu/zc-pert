package com.autopertdiagram.diagram;

import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.ProcessDAO;
import com.autopertdiagram.pojo.Project;
import net.sourceforge.stripes.exception.StripesServletException;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * User: shunyunwang
 * Date: 2009-3-25
 * Time: 17:21:36
 */
public class FindAllRoute {
    private Project project;
    private List<Route> routeList = new ArrayList<Route>();
    private List<NodeAndPeriod> nodePeriodList = new ArrayList<NodeAndPeriod>();
    private HashMap<Integer, List<TargetAndPeriod>> targetsForSource = new HashMap<Integer, List<TargetAndPeriod>>();

    public FindAllRoute(Project project) {
        this.project = project;
        //从数据库中查询出所有工序的节点及工期信息
        nodePeriodList.addAll(new ProcessDAO().findAllNodeAndPeriod(project));
        //找出所有开始节点相应的结束节点
        findTargetsForSource();
        //printTargetAndSource();
    }

    public List<Route> find() throws StripesServletException {
        //建立所需要的栈
        Stack<NodeAndPeriod> sourceAndCurrentStack = new Stack<NodeAndPeriod>();
        sourceAndCurrentStack.push(new NodeAndPeriod(0, new Integer(project.getId() + "1"), 0));
        Route currentRoute = new Route(0);
        int maxTotalPeriod = 0;
        while (!sourceAndCurrentStack.empty()) {
            //出栈
            NodeAndPeriod sourceAndCurrent = sourceAndCurrentStack.pop();
            //重新计算当前路线的总工期
            int period = sourceAndCurrent.getPeriod();
            currentRoute.addPeriod(period);
            //将节点加入的路线当中
            int current = sourceAndCurrent.getCurrent();
            currentRoute.addNode(new TargetAndPeriod(current, period));
            //将节点的对应的结束节点入栈
            if (null != targetsForSource.get(current)) {
                List<TargetAndPeriod> targets = targetsForSource.get(current);
                for (TargetAndPeriod tp : targets) {
                    sourceAndCurrentStack.push(new NodeAndPeriod(current, tp.getTarget(), tp.getPeriod()));
                }
            }
            if (!sourceAndCurrentStack.empty() && null == targetsForSource.get(current)) {
                //更新关键路线标志位
                if (currentRoute.getTotalPeriod() > maxTotalPeriod) {
                    maxTotalPeriod = currentRoute.getTotalPeriod();
                }
                routeList.add(currentRoute);
                currentRoute = currentRoute.copy(currentRoute.getId() + 1, sourceAndCurrentStack.peek().getSource());
            }
        }
        //根据最后一条路线的总工期，更新关键路线标志位
        if (currentRoute.getTotalPeriod() > maxTotalPeriod) {
            maxTotalPeriod = currentRoute.getTotalPeriod();
        }
        //将最后一条路线添加到集合中
        routeList.add(currentRoute);
        //判断关键路线，如果是则设置相应的标志位
        for (Route route : routeList) {
            if (route.getTotalPeriod() == maxTotalPeriod) {
                route.setCritical(true);
                List<TargetAndPeriod> nodelist = route.getNodeList();
                ProcessDAO processDAO = new ProcessDAO();
                Transaction tx = null;
                try {
                    tx = HibernateSessionFactory.getSession().beginTransaction();
                    for (int i = 0; i < nodelist.size() - 1; i++) {
                        processDAO.updateCoordinate("true", nodelist.get(i).getTarget(), nodelist.get(i + 1).getTarget());
                    }
                    tx.commit();
                } catch (HibernateException he) {
                    if (null != tx)
                        tx.rollback();
                    throw new StripesServletException("更新关键工序是出错！", he);
                } finally {
                    HibernateSessionFactory.closeSession();
                }
            }
        }
        //printNodeAndPeriod();
        //printRoute();
        return routeList;
    }

    /**
     * 找出所有开始节点相应的结束节点
     */
    private void findTargetsForSource() {
        for (NodeAndPeriod np : nodePeriodList) {
            //如果已经存在np的结束节点集合
            if (targetsForSource.containsKey(np.getSource())) {
                //取得np的结束节点集合
                List<TargetAndPeriod> targets = targetsForSource.get(np.getSource());
                TargetAndPeriod tp = new TargetAndPeriod(np.getTarget(), np.getPeriod());
                //如果此结束节点并未包含在结束节点集合中，那么就将其添加到集合中
                if (!targets.contains(tp)) {
                    targets.add(tp);
                }
            } else {
                //如果np的结束节点集合不存在，那么就新建一个集合，同事添加当前结束节点到集合中
                List<TargetAndPeriod> targets = new ArrayList<TargetAndPeriod>();
                targets.add(new TargetAndPeriod(np.getTarget(), np.getPeriod()));
                targetsForSource.put(np.getSource(), targets);
            }
        }
    }

    public HashMap<Integer, List<TargetAndPeriod>> getTargetsForSource() {
        return targetsForSource;
    }

    private void printRoute() {
        for (Route route : routeList) {
            System.out.println(route);
        }
    }

    private void printNodeAndPeriod() {
        for (NodeAndPeriod np : nodePeriodList) {
            System.out.print("Source Node:" + np.getSource());
            System.out.print(",");
            System.out.print("Target Node:" + np.getTarget());
            System.out.print(",");
            System.out.println("Period:" + np.getPeriod());
        }
    }

    private void printTargetAndSource() {
        for (Integer i : targetsForSource.keySet()) {
            System.out.print("source:" + i + "targets:");
            for (TargetAndPeriod tp : targetsForSource.get(i)) {
                System.out.print(tp.getTarget() + ", period:" + tp.getPeriod());
            }
            System.out.println();
        }
    }

}