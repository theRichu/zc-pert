package com.autopertdiagram.diagram;

/**
 * User: shunyunwang
 * Date: 2009-3-25
 * Time: 20:45:30
 */
public class NodeAndPeriod {
    private int source;
    private int target;
    private int period;

    public NodeAndPeriod() {
    }

    public NodeAndPeriod(int source, int target, int period) {
        this.source = source;
        this.target = target;
        this.period = period;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setCurrent(int current) {
        this.target = current;
    }

    public int getCurrent() {
        return this.target;
    }
}

/**
 * 表示以节点N为起始节点的节点，及以N与Target作为起止节点的工序的工期，
 * 将N的所有结束节点入栈时使用，同时，如果一个节点是最后的节点，
 * 那么，该节点的TargetAndPeriod将为null。
 */
class TargetAndPeriod {
    /**
     * 某个起始节点对应的结束节点
     */
    private int target;
    /**
     * 以起始节点和target为结束节点的工序的工期
     */
    private int period;

    TargetAndPeriod() {
    }

    TargetAndPeriod(int target, int period) {
        this.target = target;
        this.period = period;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TargetAndPeriod other = (TargetAndPeriod) obj;
        if (this.target != other.target) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.target;
        return hash;
    }
}