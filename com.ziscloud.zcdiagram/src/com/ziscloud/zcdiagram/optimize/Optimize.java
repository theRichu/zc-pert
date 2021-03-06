package com.ziscloud.zcdiagram.optimize;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ziscloud.zcdiagram.pojo.Activity;

public class Optimize extends AOptimize {
	private Date projectBeginTime;// 工程开工时间
	private List<Info> list = new ArrayList<Info>();;
	private int[][] obj;// 紧前紧后关系矩阵
	private int[] time;// 每个节点的持续时间
	private int[] betterTime;
	private int[] total1_model2;
	private int[] total2_model2;
	private int[] total1;// 最早开工时间
	private int[] total2;// 最迟开工时间
	private int[] temp;// 表示节点是否是关键路线上的节点
	private int[] guanjian_temp;
	private int[] guanjian;// 一条关键路线上的节点
	private int count = 1;// 一条关键路线上节点个数
	private List<Info> temp_list = new ArrayList<Info>();

	private int level = 1;
	private int[] order;
	private int[][] obj2;

	private double[] e; /* 用来存放每道工序的费用变化率 */
	private int[] aa;/* 用来存放每道工序的最大可压缩天数 */

	private double MAXSY = 0.0; /* MAXSY表示最后的压缩后的最大收益 */
	
	private int totalDays = 0;

	public Optimize(List<Activity> activities) {
		super(activities);
	}

	/**
	 * 模拟数据 初始化数据
	 */
	public void init() {
		if (null != activities && activities.size() > 0) {
			this.projectBeginTime = activities.get(0).getPlanStartDate();
			int size = activities.size() + 1;
			obj = new int[size][size];
			time = new int[size];
			total1 = new int[size];
			total2 = new int[size];
			temp = new int[size];
			guanjian_temp = new int[size];
			betterTime = new int[size];
			total1_model2 = new int[size];
			total2_model2 = new int[size];
			order = new int[size];
			obj2 = new int[size][size];

			e = new double[size];
			aa = new int[size];

			for (Activity act : activities) {
				Info info = new Info();
				info.setId(act.getId());
				info.setWorkNo(act.getSymbol());
				info.setName(act.getName());
				info.setLastTime(act.getPlanPeriod());
				info.setCost(act.getPlanCost());
				info.setBenifit(act.getOutput());
				info.setPriviousWorkNo(act.getPreActivity());
				if (null == act.getRarDays()) {
					info.setMaxReduceTime(0);
				} else {
					info.setMaxReduceTime(act.getRarDays());
				}
				if (null == act.getRarCost()) {
					info.setReduceCost(0.0);
				} else {
					info.setReduceCost(act.getRarCost());
				}
				list.add(info);
			}

		} else {
			throw new RuntimeException("工程项目没有工序,无需优化");
		}
	}

	public void b(int t, List<Info> list, int[][] obj2) {
		for (int i = 0; i < list.size(); i++)
			if (obj2[t][i] == 1) {
				Info info = (Info) list.get(i);
				if (info.getFlag() == 0) {
					info.setLevel(level);
					info.setFlag(1);
				} else if (info.getLevel() < level)
					info.setLevel(level);
				level++;
				b(i, list, obj2);
			}

		level--;
		return;
	}

	@Override
	public List<Info> modelOneOptimize() {
		init();
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		total1[0] = 0;

		// 插入头结点
		Info info = new Info(0, "A0", "工序0", 0, 0, 0, null, 0, 0, 0, 0);
		for (int i = 0; i < list.size(); i++) {
			Info p = list.get(i);
			if (p.getPriviousWorkNo() == null
					|| p.getPriviousWorkNo().trim().equals("")) {
				obj[0][i] = 1;
				p.setPriviousWorkNo("A0");
			}
		}
		list.add(0, info);
		// }

		for (int i = 0; i < list.size(); i++) {
			Info p = (Info) list.get(i);
			// int j=p.getId();
			String jinqiangongxu = p.getPriviousWorkNo();
			if (jinqiangongxu != null && !jinqiangongxu.trim().equals("")) {
				String[] jq = jinqiangongxu.split(",");
				for (int m = 0; m < jq.length; m++) {
					for (int j = 0; j < list.size(); j++) {
						Info p2 = (Info) list.get(j);
						if (p2.getWorkNo().trim().equals(jq[m].trim())) {
							obj2[j][i] = 1;
						}
					}
				}
			}
			time[i] = p.getLastTime();
			aa[i] = p.getMaxReduceTime();
			e[i] = p.getReduceCost();
		}

		// 递归
		int x = 0;
		for (x = 0; x < list.size(); x++) {
			Info p = list.get(x);
			String priviousWork = p.getPriviousWorkNo();
			if (priviousWork == null || priviousWork.trim().equals("")) {
				p.setLevel(0);
				p.setFlag(0);
				break;
			}
		}

		b(x, list, obj2);

		int s = 0;
		int d = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				Info p = list.get(j);

				if (p.getLevel() == i)
					order[s++] = j;
			}
			d = i + 1;

		}

		for (int i = 0; i < d; i++) {
			temp_list.add(null);
		}

		for (int g = 0; g < d; g++) {
			Info p = list.get(order[g]);
			temp_list.set(g, p);

		}

		// 整理结束

		for (int i = 0; i < temp_list.size(); i++) {
			Info p = (Info) temp_list.get(i);
			String jinqiangongxu = p.getPriviousWorkNo();
			if (jinqiangongxu != null && !jinqiangongxu.trim().equals("")) {
				String[] jq = jinqiangongxu.split(",");
				for (int m = 0; m < jq.length; m++) {
					for (int j = 0; j < temp_list.size(); j++) {
						Info p2 = (Info) temp_list.get(j);
						if (p2.getWorkNo().equals(jq[m])) {
							obj[j][i] = 1;
						}
					}
				}
			}
			time[i] = p.getLastTime();
			aa[i] = p.getMaxReduceTime();
			e[i] = p.getReduceCost();
		}

		// 得到total1
		int temp1 = 0;
		for (int j = 0; j < obj.length; j++)
			for (int i = 0; i < obj[j].length; i++) {
				if (obj[i][j] == 1) {
					temp1 = total1[i] + time[i] + 1;
					for (int k = i + 1; k < obj[j].length; k++) {
						if (obj[k][j] == 1) {
							if (total1[k] + time[k] + 1 > temp1) {
								temp1 = total1[k] + time[k] + 1;
							}
						}
					}
					total1[j] = temp1;
					break;
				}
			}

		// 得到total2
		total2[total2.length - 1] = total1[total1.length - 1];
		int temp2 = 0;

		for (int i = obj.length - 1; i >= 0; i--)
			for (int j = obj[i].length - 1; j >= 0; j--) {
				if (obj[i][j] == 1) {
					temp2 = total2[j] - time[i] - 1;
					for (int k = j - 1; k >= 0; k--) {
						if (obj[i][k] == 1) {
							if (total2[k] - time[i] - 1 < temp2) {
								temp2 = total2[k] - time[i] - 1;
							}
						}
					}
					total2[i] = temp2;
					break;
				}
			}

		for (int i = 1; i < total1.length; i++) {
			total1[i] = total1[i] - 1;
			total2[i] = total2[i] - 1;
		}

		// 找关键节点

		for (int i = 0; i < total1.length; i++) {
			if (total1[i] - total2[i] == 0) {
				temp[i] = 1;
			}
		}

		// 找某一条关键路线上所有节点
		guanjian_temp[0] = 1;
		for (int i = 0; i < guanjian_temp.length; i++) {
			if (temp[i] == 0)
				continue;
			for (int j = i; j < guanjian_temp.length; j++) {
				if (temp[j] == 1 && obj[i][j] == 1)// temp表示是否关键工序,obj表示紧前紧后关系
				{
					guanjian_temp[count++] = j + 1;
					i = j;
				}

			}
		}
		guanjian = new int[count];
		for (int i = 0; i < count; i++) {
			guanjian[i] = guanjian_temp[i];
		}

		// 计算model1优化后开工和完工时间
		for (int i = 0; i < total1.length; i++) {
			cal.setTime(projectBeginTime);
			cal.add(Calendar.DAY_OF_MONTH, total1[i]);
			Info p = temp_list.get(i);
			p.setBetterBeginTime_model1(cal.getTime());
			cal1.setTime(p.getBetterBeginTime_model1());
			cal1.add(Calendar.DAY_OF_MONTH, time[i]);
			p.setBetterEndTime_model1(cal1.getTime());
		}
		return temp_list;
	}

	@Override
	public List<Info> modelTwoOptimize(double bonus) {
		modelOneOptimize();
		int[] dd = guanjian;
		// @SuppressWarnings("unused")
		// double MAXSY = 0.0; /* MAXSY表示最后的压缩后的最大收益 */
		// double bonus = 260; /* 用来表示压缩一天 带来的收益 */
		int temp0 = 0; /* 用来表示最后一道工序的紧前工序的初始值 */
		double[] cc = new double[dd.length]; /* 用来存放每道关键工序的费用变化率 */
		int[] mm = new int[dd.length]; /* 用来存放每道关键工序的最后实际压缩的天数 */
		int[] nowOpenTime = new int[dd.length];
		int sum = 0; /* 表示目前为止已经压缩的总天数 */
		int[] ff = new int[temp.length]; /* 用来标记每道关键工序是否已经压缩过 */

		for (int k = 0; k < temp.length; k++) {
			if (obj[k][temp.length - 1] == 1 && temp[k] != 1
					&& total1[k] + time[k] + 1 > temp0) {
				temp0 = total1[k] + time[k] + 1;
			}
		}
		int MAXKYS = total1[total1.length - 1] - temp0; /* 用来表示整个项目可压缩的天数总合 */

		for (int i = 0; i < dd.length; i++) {
			cc[i] = e[dd[i] - 1];
		}

		for (int i = 0; i < count; i++) {
			double temp1 = 1e9; // 用来表示关键工序的费用变化率的初始值
			int k = 0, s = 0; // K表示实际网络图中关键工序的接点序号，s表示存放关键工序费用变化率数组中该工序的下标号，其范围是从0到number-1,并不能代表实际的惯技工序接点号
			for (int j = 0; j < count; j++) {
				if ((cc[j] < temp1) && (ff[j] == 0) && (cc[j] <= bonus)
						&& (cc[j] > 0)) {
					s = j;
					temp1 = cc[s];
				}

			}

			if (temp1 == 1e9)
				break;
			k = dd[s] - 1;
			if (sum > MAXKYS)
				break;
			if (sum + aa[k] > MAXKYS)
				mm[s] = MAXKYS - sum;
			else
				mm[s] = aa[k];
			ff[s] = 1;
			sum += mm[s];
		}

		double cost = 0.0; // 用来表示压缩所有工序带来的总成本
		int total = 0; // 用来表示压缩关键工序的总天数
		for (int i = 0; i < count; i++) {
			cost += (mm[i] * cc[i]);
			total += mm[i];
		}

		nowOpenTime[0] = 0;

		for (int i = 0; i < dd.length - 1; i++) {
			nowOpenTime[i + 1] = nowOpenTime[i] + time[dd[i] - 1] - mm[i];
		}

		// 相对的时间
		MAXSY = bonus * total - cost;

		// 计算model2优化后开工和完工时间
		betterTime = time;
		for (int i = 0; i < mm.length; i++) {
			betterTime[dd[i] - 1] = betterTime[dd[i] - 1] - mm[i];
		}

		total1_model2[0] = 0;
		int temp1 = 0;
		for (int j = 0; j < obj.length; j++)
			for (int i = 0; i < obj[j].length; i++) {
				if (obj[i][j] == 1) {
					temp1 = total1_model2[i] + betterTime[i] + 1;
					for (int k = i + 1; k < obj[j].length; k++) {
						if (obj[k][j] == 1) {
							if (total1_model2[k] + betterTime[k] + 1 > temp1) {
								temp1 = total1_model2[k] + betterTime[k] + 1;
							}
						}
					}
					total1_model2[j] = temp1;
					break;
				}

			}

		// 得到total2
		total2_model2[total2_model2.length - 1] = total1_model2[total1_model2.length - 1];
		int temp2 = 0;

		for (int i = obj.length - 1; i >= 0; i--)
			for (int j = obj[i].length - 1; j >= 0; j--) {
				if (obj[i][j] == 1) {
					temp2 = total2_model2[j] - betterTime[i] - 1;
					for (int k = j - 1; k >= 0; k--) {
						if (obj[i][k] == 1) {
							if (total2_model2[k] - betterTime[i] - 1 < temp2) {
								temp2 = total2_model2[k] - betterTime[i] - 1;
							}
						}
					}
					total2_model2[i] = temp2;
					break;
				}
			}

		for (int i = 1; i < total1.length; i++) {
			total1_model2[i] = total1_model2[i] - 1;
			total2_model2[i] = total2_model2[i] - 1;
		}
		
		totalDays=total1[total1.length-1]+time[time.length-1]+total;//压缩后总工期

		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < total1.length; i++) {
			cal.setTime(projectBeginTime);
			Info p = temp_list.get(i);
			cal.add(Calendar.DAY_OF_MONTH, total1_model2[i]);
			p.setBetterBeginTime_model2(cal.getTime());
			cal.setTime(p.getBetterBeginTime_model2());
			cal.add(Calendar.DAY_OF_MONTH, betterTime[i]);
			p.setBetterEndTime_model2(cal.getTime());
		}
		return temp_list;
	}

	public double getMAXSY() {
		return MAXSY;
	}

	public int getTotalDays() {
		return totalDays;
	}

}
