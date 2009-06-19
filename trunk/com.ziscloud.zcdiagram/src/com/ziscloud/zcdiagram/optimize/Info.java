package com.ziscloud.zcdiagram.optimize;

public class Info {

	private int id;
	private String workNo;// 工序代号
	private String name;// 工序名称
	private int lastTime;// 计划工期
	private double cost;// 预算成本
	private double benifit;// 年产出
	private String priviousWorkNo;// 紧前工序
	private int maxReduceTime;// 最大可压缩天数
	private double ReduceCost;// 压缩成本
	private int level;
	private int flag;

	public Info() {
	}

	public Info(int id, String workNo, String name, int lastTime, double cost,
			double benifit, String priviousWorkNo, int maxReduceTime,
			double reduceCost, int level, int flag) {
		super();
		this.id = id;
		this.workNo = workNo;
		this.name = name;
		this.lastTime = lastTime;
		this.cost = cost;
		this.benifit = benifit;
		this.priviousWorkNo = priviousWorkNo;
		this.maxReduceTime = maxReduceTime;
		ReduceCost = reduceCost;
		this.level = level;
		this.flag = flag;
	}

	public Info(int id, String workNo, String name, int lastTime, double cost,
			double benifit, String priviousWorkNo, int maxReduceTime,
			double reduceCost) {
		super();
		this.id = id;
		this.workNo = workNo;
		this.name = name;
		this.lastTime = lastTime;
		this.cost = cost;
		this.benifit = benifit;
		this.priviousWorkNo = priviousWorkNo;
		this.maxReduceTime = maxReduceTime;
		ReduceCost = reduceCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getBenifit() {
		return benifit;
	}

	public void setBenifit(double benifit) {
		this.benifit = benifit;
	}

	public String getPriviousWorkNo() {
		return priviousWorkNo;
	}

	public void setPriviousWorkNo(String priviousWorkNo) {
		this.priviousWorkNo = priviousWorkNo;
	}

	public int getMaxReduceTime() {
		return maxReduceTime;
	}

	public void setMaxReduceTime(int maxReduceTime) {
		this.maxReduceTime = maxReduceTime;
	}

	public double getReduceCost() {
		return ReduceCost;
	}

	public void setReduceCost(double reduceCost) {
		ReduceCost = reduceCost;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
