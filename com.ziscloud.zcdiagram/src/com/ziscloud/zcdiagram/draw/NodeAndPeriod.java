package com.ziscloud.zcdiagram.draw;

/**
 * User: shunyunwang
 * Date: 2009-3-25
 * Time: 20:45:30
 */
public class NodeAndPeriod {
    private int start;
    private int end;
    private int period;

    public NodeAndPeriod() {
    }

    public NodeAndPeriod(int start, int end, int period) {
        this.start = start;
        this.end = end;
        this.period = period;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setCurrent(int current) {
        this.end = current;
    }

    public int getCurrent() {
        return this.end;
    }
}

/**
 * 表示以节点N为起始节点的节点，及以N与Target作为起止节点的工序的工期，
 * 将N的所有结束节点入栈时使用，同时，如果一个节点是最后的节点，
 * 那么，该节点的TargetAndPeriod将为null。
 */
class EndAndPeriod {
    /**
     * 某个起始节点对应的结束节点
     */
    private int end;
    /**
     * 以起始节点和target为结束节点的工序的工期
     */
    private int period;

    EndAndPeriod() {
    }

    EndAndPeriod(int end, int period) {
        this.end = end;
        this.period = period;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
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
        final EndAndPeriod other = (EndAndPeriod) obj;
        if (this.end != other.end) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.end;
        return hash;
    }
}