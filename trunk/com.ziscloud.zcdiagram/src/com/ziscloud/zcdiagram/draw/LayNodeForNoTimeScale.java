package com.ziscloud.zcdiagram.draw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<Integer, NodeAndXY> nodeAndXYMap;
	private int firstNodeId;

	public LayNodeForNoTimeScale(Project project, int model) {
		this.project = project;
		firstNodeId = new Integer(project.getId() + "" + model + "1");
		FindAllRoute findAllRoute = new FindAllRoute(project, firstNodeId,
				model);
		routeList = findAllRoute.find();
		nodeAndXYMap = new HashMap<Integer, NodeAndXY>();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void layNode() {
		layXandY();
		saveCoordinate();
	}

	private void layXandY() {
		int flagY = 1;
		NodeAndXY node = null;
		int y = 0;
		int x = 0;
		nodeAndXYMap.put(firstNodeId, new NodeAndXY(firstNodeId, 1));
		for (Route route : routeList) {
			boolean isSetY = false;
			int flagX = 1;
			for (EndAndPeriod tp : route.getNodeList()) {
				if (!nodeAndXYMap.containsKey(tp.getEnd())) {
					nodeAndXYMap
							.put(tp.getEnd(), new NodeAndXY(tp.getEnd(), 1));
				}
				node = nodeAndXYMap.get(tp.getEnd());
				if (null != node) {
					y = node.getY();
					if (0 == y) {
						node.setY(flagY);
						isSetY = true;
					}
					x = node.getX();
					if (x < flagX) {
						node.setX(flagX);
						// System.out.println("id:" + node.getNode() +
						// ", x:"+node.getX());
					}

				}
				flagX++;
				// System.out.println(nodeAndXYMap.keySet().size());
			}
			if (isSetY) {
				flagY++;
			}
		}
	}

	private void saveCoordinate() {
		DrawNodeDAO nodeDAO = new DrawNodeDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			for (NodeAndXY nx : nodeAndXYMap.values()) {
				// System.out.println(nx.getNode());
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
			this.y = 0;
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
