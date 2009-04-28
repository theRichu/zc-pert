package com.autopertdiagram.diagram;

import java.util.ArrayList;
import java.util.List;

/**
 * User: shunyunwang
 * Date: 2009-3-25
 * Time: 19:01:27
 */

/**
 * 网络图中的一条路线
 */
public class Route {
    /**
     * 路线的id，标识路线的唯一性
     */
    private int id;
    /**
     * 路线上的所有节点的集合
     */
    private List<TargetAndPeriod> nodeList = new ArrayList<TargetAndPeriod>();
    /**
     * 路线上所有工序工期的合计
     */
    private int totalPeriod = 0;
    /**
     * 标识此条路线是否是关键路线
     */
    private boolean isCritical = false;

    public Route(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TargetAndPeriod> getNodeList() {
        return nodeList;
    }

    private void setNodeList(List<TargetAndPeriod> nodeList) {
        this.nodeList = nodeList;
    }

    /**
     * 将一个节点添加到路线上
     *
     * @param tp 节点的ID
     */
    public void addNode(TargetAndPeriod tp) {
        this.nodeList.add(tp);
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    private void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    /**
     * 将路线上的工序的工期加到路线的工期合计上
     *
     * @param period 工序的工期
     */
    public void addPeriod(int period) {
        this.totalPeriod += period;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public Route copy(int id) {
        Route copyedRoute = new Route(id);
        List<TargetAndPeriod> copyedNodeList = new ArrayList<TargetAndPeriod>();
        copyedNodeList.addAll(this.nodeList);
        copyedRoute.setNodeList(copyedNodeList);
        copyedRoute.setTotalPeriod(this.totalPeriod);
        copyedRoute.setCritical(this.isCritical);
        return copyedRoute;
    }

    public Route copy(int id, int end) {
        Route copyedRoute = new Route(id);
        List<TargetAndPeriod> copyedNodeList = new ArrayList<TargetAndPeriod>();
        int period = 0;
        for (TargetAndPeriod node : this.nodeList) {
            if (node.getTarget() <= end) {
                period += node.getPeriod();
                copyedNodeList.add(node);
            } else {
                break;
            }
        }
        copyedRoute.setNodeList(copyedNodeList);
        copyedRoute.setTotalPeriod(period);
        copyedRoute.setCritical(this.isCritical);
        return copyedRoute;
    }

    @Override
    public String toString() {
        StringBuilder routeString = new StringBuilder();
        routeString.append("路线【" + this.id + "】:");
        for (TargetAndPeriod node : this.nodeList) {
            routeString.append("[" + node.getTarget() + "," + node.getPeriod() + "]").append("-->");
        }
        routeString.delete(routeString.length() - 3, routeString.length());
        routeString.append(",工期：" + this.totalPeriod);
        routeString.append(",关键路线：" + this.isCritical);
        return routeString.toString();
    }
}
