package com.autopertdiagram.diagram;

import com.autopertdiagram.dao.HibernateSessionFactory;
import com.autopertdiagram.dao.NodeDAO;
import com.autopertdiagram.pojo.Node;
import com.autopertdiagram.pojo.Process;
import com.autopertdiagram.pojo.Project;
import net.sourceforge.stripes.exception.StripesServletException;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author ZisCloud
 */
public class LayNodeForNoTimeScale implements LayNode {
    private Project project;
    private List<Process> prcsList;
    private List<Node> nodeList;
    private List<Route> routeList;
    private HashMap<Integer, List<TargetAndPeriod>> targetsForSource;
    private Map<Integer, NodeAndXY> nodeAndXYMap;

    public LayNodeForNoTimeScale(Project project) throws StripesServletException{
        this.project = project;
        FindAllRoute findAllRoute = new FindAllRoute(project);
        routeList = findAllRoute.find();
        targetsForSource = findAllRoute.getTargetsForSource();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void layNode() throws StripesServletException {
        nodeAndXYMap = layX();
        layY();
        saveCoordinate();
    }

    private Map<Integer, NodeAndXY> layX() {
        Stack<NodeAndXY> nodeAndXYStack = new Stack<NodeAndXY>();
        Map<Integer, NodeAndXY> nodeAndXYMap = new HashMap<Integer, NodeAndXY>();
        nodeAndXYStack.push(new NodeAndXY(new Integer(project.getId() + "1"), 0, 1));
        while (!nodeAndXYStack.empty()) {
            //出栈
            NodeAndXY nx = nodeAndXYStack.pop();
            //设置X坐标
            if (nx.getX() < nx.getFlag()) {
                nx.setX(nx.getFlag());
            }
            nodeAndXYMap.put(nx.getNode(), nx);
            //将其以其为开始节点的结束节点入栈
            List<TargetAndPeriod> tps = targetsForSource.get(nx.getNode());
            if (null != tps) {
                for (TargetAndPeriod tp : tps) {
                    nodeAndXYStack.push(new NodeAndXY(tp.getTarget(), nx.getX(), nx.getX() + 1));
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
            for (TargetAndPeriod tp : route.getNodeList()) {
                node = nodeAndXYMap.get(tp.getTarget());
                if (null != node) {
                    y = node.getY();
                    if (0 == y) {
                        node.setY(flag);
                    }
                }
            }
            flag++;
        }
    }

    private void saveCoordinate() throws StripesServletException {
        NodeDAO nodeDAO = new NodeDAO();
        Transaction tx = null;
        try {
            tx = HibernateSessionFactory.getSession().beginTransaction();
            for (NodeAndXY nx : nodeAndXYMap.values()) {
                nodeDAO.updateCoordinate(nx.getNode(), nx.getX(), nx.getY());    
            }
            nodeDAO. deleteRedundancy();
            tx.commit();
        } catch (HibernateException he) {
            if (null != tx)
                tx.rollback();
            throw new StripesServletException("保存工序的布点结果时发生错误!", he);
        } finally {
            HibernateSessionFactory.closeSession();
        }
        //for (NodeAndXY nx : nodeAndXYMap.values()) {
        //    System.out.println("Node:" + nx.getNode() + ", X=" + nx.getX() + ", Y=" + nx.getY());
        //}
    }

    private class NodeAndXY {
        private int node;
        private int x;
        private int y;
        private int flag;

        private NodeAndXY(int node, int x, int flag) {
            this.node = node;
            this.x = x;
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
