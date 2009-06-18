package com.ziscloud.zcdiagram.draw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dao.DrawNodeDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.pojo.Project;

/**
 * @author ZisCloud
 */
public class LayNodeForNoTimeScale implements ILayNode {
	private Project project;
	private List<Route> routeList;
	private HashMap<Integer, List<EndAndPeriod>> endsForStart;
	private Map<Integer, NodeAndXY> nodeAndXYMap;
	private int firstNodeId;

	public LayNodeForNoTimeScale(Project project, int model) {
		this.project = project;
		firstNodeId = new Integer(project.getId() + "" + model + "1");
		FindAllRoute findAllRoute = new FindAllRoute(project, firstNodeId,
				model);
		routeList = findAllRoute.find();
		endsForStart = findAllRoute.getEndsForStart();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void layNode() {
		nodeAndXYMap = layX();
		layY();
		saveCoordinate();
	}

	private Map<Integer, NodeAndXY> layX() {
		Stack<NodeAndXY> nodeAndXYStack = new Stack<NodeAndXY>();
		Map<Integer, NodeAndXY> nodeAndXYMap = new HashMap<Integer, NodeAndXY>();
		nodeAndXYStack.push(new NodeAndXY(firstNodeId, 1));
		while (!nodeAndXYStack.empty()) {
			// 出栈
			NodeAndXY nx = nodeAndXYStack.pop();
			// 如果节点的坐标是否已经被设置，且大于flag的话，跳过此次循环
			if (nodeAndXYMap.containsKey(nx.getNode())
					&& nodeAndXYMap.get(nx.getNode()).getX() >= nx.getFlag()) {
				continue;
			}
			// 设置X坐标
			nx.setX(nx.getFlag());
			// 如果map中不包含节点的坐标信息或坐标信息被更新，那么添加
			nodeAndXYMap.put(nx.getNode(), nx);
			// 将其以其为开始节点的结束节点入栈
			List<EndAndPeriod> tps = endsForStart.get(nx.getNode());
			if (null != tps) {
				for (EndAndPeriod tp : tps) {
					nodeAndXYStack.push(new NodeAndXY(tp.getEnd(),
							nx.getX() + 1));
				}
			}
		}
		return nodeAndXYMap;
	}

	private void layY() {
		int flag = 1;
		NodeAndXY node = null;
		int y = 0;

		for (Route route : routeList) {
			boolean isSetY = false;
			for (EndAndPeriod tp : route.getNodeList()) {
				// System.out.println(tp.getEnd());
				node = nodeAndXYMap.get(tp.getEnd());
				if (null != node) {
					y = node.getY();
					if (0 == y) {
						node.setY(flag);
						isSetY = true;
					}
				}
			}
			if (isSetY) {
				flag++;
			}
		}
	}

	private void saveCoordinate() {
		DrawNodeDAO nodeDAO = new DrawNodeDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			for (NodeAndXY nx : nodeAndXYMap.values()) {
				nodeDAO.updateCoordinate(nx.getNode(), nx.getX() * X_RATIO, nx
						.getY()
						* Y_RATIO);
			}
			nodeDAO.deleteRedundancy();
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx)
				tx.rollback();
			throw new RuntimeException("保存工序的布点结果时发生错误!", he);
		} finally {
			SessionFactory.closeSession();
		}
		// for (NodeAndXY nx : nodeAndXYMap.values()) {
		// System.out.println("DrawNode:" + nx.getNode() + ", X=" + nx.getX()
		// + ", Y=" + nx.getY());
		// }
	}

	private class NodeAndXY {
		private int node;
		private int x;
		private int y;
		private int flag;

		private NodeAndXY(int node, int flag) {
			this.node = node;
			this.x = 0;
			this.flag = flag;
		}

		public int getNode() {
			return node;
		}

		public void setNode(int node) {
			this.node = node;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final NodeAndXY other = (NodeAndXY) obj;
			if (this.node != other.node) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 17 * hash + this.node;
			return hash;
		}
	}
}
