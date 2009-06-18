package com.ziscloud.zcdiagram.optimize;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ziscloud.zcdiagram.pojo.Activity;

//所有关键节点数组
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
	private Date[] betterBeginTime_model1;// MODEL1优化后开工时间
	private Date[] betterEndTime_model1;// MODEL1优化后完工时间
	private Date[] betterBeginTime_model2;// MODEL2优化后开工时间
	private Date[] betterEndTime_model2;// MODEL2优化后完工时间

	private double[] e; /* 用来存放每道工序的费用变化率 */
	private int[] aa;/* 用来存放每道工序的最大可压缩天数 */

	public Optimize(List<Activity> activities) {
		super(activities);
	}

	/**
	 * 模拟数据 初始化数据
	 */
	public void init() {
		if (null != activities && activities.size() > 0) {
			this.projectBeginTime = activities.get(0).getPlanStartDate();
			int size = activities.size();
			obj = new int[size][size];
			time = new int[size];
			total1 = new int[size];
			total2 = new int[size];
			temp = new int[size];
			guanjian_temp = new int[size];
			betterBeginTime_model1 = new Date[size];
			betterEndTime_model1 = new Date[size];
			betterBeginTime_model2 = new Date[size];
			betterEndTime_model2 = new Date[size];
			betterTime = new int[size];
			total1_model2 = new int[size];
			total2_model2 = new int[size];

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
			// for (Info i : list) {
			// System.out.println(i);
			// }
		} else {
			throw new RuntimeException("工程项目没有工序,无需优化");
		}
	}

	@Override
	public Date[][] modelOneOptimize() {
		init();
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		total1[0] = 0;
		// System.out.println(list.size());
		// 得到time,obj

		// int firstId = list.get(0).getId() - 1;
		// for (Iterator<Info> it = list.iterator(); it.hasNext();) {
		// Info p = it.next();
		// int j = p.getId();
		// String jinqiangongxu = p.getPriviousWorkNo();
		// if (jinqiangongxu != null && !jinqiangongxu.trim().equals("")) {
		// String[] jq = jinqiangongxu.split(",");
		// for (int m = 0; m < jq.length; m++) {
		// for (Iterator<Info> it2 = list.iterator(); it2.hasNext();) {
		// Info p2 = it2.next();
		// if (p2.getWorkNo().equals(jq[m])) {
		// int i = p2.getId();
		// if (i != 0) {
		// obj[i - firstId - 1][j - firstId - 1] = 1;
		// }
		// }
		// }
		// }
		// }
		// time[j - firstId - 1] = p.getLastTime();
		// aa[j - firstId - 1] = p.getMaxReduceTime();
		// e[j - firstId - 1] = p.getReduceCost();
		// System.out.println("id:" + j);
		//
		// }

		for (int i = 0; i < list.size(); i++) {
			Info p = (Info) list.get(i);
			String jinqiangongxu = p.getPriviousWorkNo();
			if (jinqiangongxu != null && !jinqiangongxu.trim().equals("")) {
				String[] jq = jinqiangongxu.split(",");
				for (int m = 0; m < jq.length; m++) {
					for (int j = 0; j < list.size(); j++) {
						Info p2 = (Info) list.get(j);
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

		// for (int i = 0; i < obj.length; i++)
		// for (int j = 0; j < obj[i].length; j++) {
		// System.out.print(obj[i][j] + ",");
		// if (j == 17)
		// System.out.print("\n");
		// System.out.println(time[i]);
		// }
		// 得到total1
		int temp1 = 0;
		for (int j = 0; j < obj.length; j++)
			for (int i = 0; i < obj[j].length; i++) {
				if (obj[i][j] == 1) {
					temp1 = total1[i] + time[i];
					for (int k = i + 1; k < obj[j].length; k++) {
						if (obj[k][j] == 1) {
							if (total1[k] + time[k] > temp1) {
								temp1 = total1[k] + time[k];
							}
						}
					}
					total1[j] = temp1;
					break;
				}

			}

		// for (int i = 0; i < total1.length; i++) {
		// System.out.println((i + 1) + "工序的最短开工时间:" + total1[i]);
		// }

		// 得到total2
		total2[total2.length - 1] = total1[total1.length - 1];
		int temp2 = 0;

		for (int i = obj.length - 1; i >= 0; i--)
			for (int j = obj[i].length - 1; j >= 0; j--) {
				if (obj[i][j] == 1) {
					temp2 = total2[j] - time[i];
					for (int k = j - 1; k >= 0; k--) {
						if (obj[i][k] == 1) {
							if (total2[k] - time[i] < temp2) {
								temp2 = total2[k] - time[i];
							}
						}
					}
					total2[i] = temp2;
					break;
				}
			}

		// for (int i = 0; i < total2.length; i++) {
		// System.out.println((i + 1) + "工序的最迟开工时间:" + total2[i]);
		// }

		// 找关键节点

		for (int i = 0; i < total1.length; i++) {
			if (total1[i] - total2[i] == 0) {
				// System.out.println("关键节点有：" + (i + 1));
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
		// for (int i = 0; i < guanjian.length; i++) {
		// System.out.println("网络图其中一条关键路线的节点有：" + guanjian[i]);
		// }

		// System.out.println("这条路线上关键工序个数为：" + count);

		// for (int i = 0; i < obj.length; i++)
		// for (int j = 0; j < obj[i].length; j++) {
		// System.out.print(obj[i][j] + ",");
		// if (j == 17)
		// System.out.print("\n");
		// }

		// 计算model1优化后开工和完工时间
		// for (int i = 0; i < total1.length; i++) {
		// // sdf.format(projectOpenTime);
		// cal.setTime(projectBeginTime);
		// cal.add(Calendar.DAY_OF_MONTH, total1[i]);
		// betterBeginTime_model1[i] = cal.getTime();
		// cal1.setTime(betterBeginTime_model1[i]);
		// cal1.add(Calendar.DAY_OF_MONTH, time[i]);
		//
		// betterEndTime_model1[i] = cal1.getTime();
		// }
		for (int i = 0; i < total1.length; i++) {
			// sdf.format(projectOpenTime);
			// try {
			cal.setTime(projectBeginTime);

			// } catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			cal.add(Calendar.DAY_OF_MONTH, total1[i]);
			betterBeginTime_model1[i] = cal.getTime();// sdf.format(cal.getTime());
			// try {
			cal1.setTime(betterBeginTime_model1[i]);
			// } catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			cal1.add(Calendar.DAY_OF_MONTH, time[i]);

			betterEndTime_model1[i] = cal1.getTime();// sdf.format(cal1.getTime());
		}

		// for (int i = 0; i < total1.length; i++)
		// for (int j = i; j < total1.length; j++) {
		// if (obj[i][j] == 1)
		// cal.setTime(betterEndTime_model1[i]);
		// cal.add(Calendar.DAY_OF_MONTH, 1);
		// betterBeginTime_model1[j] = cal.getTime();
		// }

		// for (int i = 0; i < total1.length; i++) {
		// System.out.println("工序" + (i + 1) + "优化后开工时间"
		// + betterBeginTime_model1[i]);
		// System.out.println("工序" + (i + 1) + "优化后完工时间"
		// + betterEndTime_model1[i]);
		// }
		return new Date[][] { betterBeginTime_model1, betterEndTime_model1 };
	}

	@Override
	public Date[][] modelTwoOptimize() {
		modelOneOptimize();
		int[] dd = guanjian;
		@SuppressWarnings("unused")
		double MAXSY = 0.0; /* MAXSY表示最后的压缩后的最大收益 */
		double bonus = 260; /* 用来表示压缩一天 带来的收益 */
		int temp0 = 0; /* 用来表示最后一道工序的紧前工序的初始值 */
		double[] cc = new double[dd.length]; /* 用来存放每道关键工序的费用变化率 */
		int[] mm = new int[dd.length]; /* 用来存放每道关键工序的最后实际压缩的天数 */
		int[] nowOpenTime = new int[dd.length];
		int sum = 0; /* 表示目前为止已经压缩的总天数 */
		int[] ff = new int[temp.length]; /* 用来标记每道关键工序是否已经压缩过 */

		for (int k = 0; k < temp.length; k++) {
			if (obj[k][temp.length - 1] == 1 && temp[k] != 1
					&& total1[k] + time[k] > temp0) {
				temp0 = total1[k] + time[k];
			}
		}
		int MAXKYS = total1[total1.length - 1] - temp0; /* 用来表示整个项目可压缩的天数总合 */
		// System.out.println("最大可压缩总天数" + MAXKYS);

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
			// System.out.println("可压缩的关键工序按费用变化率由小到大的节点依次为：" + (k + 1));
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
			// System.out.println("关键工序" + dd[i] + "压缩的天数是" + mm[i]);
		}

		// for (int i = 0; i < dd.length; i++) {
		// System.out.println("节点" + dd[i]);
		// }
		nowOpenTime[0] = 0;

		for (int i = 0; i < dd.length - 1; i++) {
			nowOpenTime[i + 1] = nowOpenTime[i] + time[dd[i] - 1] - mm[i];
		}

		// 相对的时间
		// for (int i = 0; i < dd.length; i++) {
		// System.out.println("实际开工时间:" + nowOpenTime[i]);
		// }
		MAXSY = bonus * total - cost;
		// System.out.println("由于压缩带来的总成本为：" + cost);
		// System.out.println("压缩的总天数为：" + total);
		// System.out.println("最大的压缩收益为：" + MAXSY);

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
					temp1 = total1_model2[i] + betterTime[i];
					for (int k = i + 1; k < obj[j].length; k++) {
						if (obj[k][j] == 1) {
							if (total1_model2[k] + betterTime[k] > temp1) {
								temp1 = total1_model2[k] + betterTime[k];
							}
						}
					}
					total1_model2[j] = temp1;
					break;
				}

			}

		// for (int i = 0; i < total1.length; i++) {
		// System.out.println((i + 1) + "工序的最短开工时间:" + total1_model2[i]);
		// }

		// 得到total2
		total2_model2[total2_model2.length - 1] = total1_model2[total1_model2.length - 1];
		int temp2 = 0;

		for (int i = obj.length - 1; i >= 0; i--)
			for (int j = obj[i].length - 1; j >= 0; j--) {
				if (obj[i][j] == 1) {
					temp2 = total2_model2[j] - betterTime[i];
					for (int k = j - 1; k >= 0; k--) {
						if (obj[i][k] == 1) {
							if (total2_model2[k] - betterTime[i] < temp2) {
								temp2 = total2_model2[k] - betterTime[i];
							}
						}
					}
					total2_model2[i] = temp2;
					break;
				}
			}

		// for (int i = 0; i < total2.length; i++) {
		// System.out.println((i + 1) + "工序的最迟开工时间:" + total2_model2[i]);
		// }

		// Calendar cal = Calendar.getInstance();
		// for (int i = 0; i < total1.length; i++) {
		// // sdf.format(projectOpenTime);
		// cal.setTime(projectBeginTime);
		// cal.add(Calendar.DAY_OF_MONTH, total1_model2[i]);
		// betterBeginTime_model2[i] = cal.getTime();
		// cal.setTime(betterBeginTime_model2[i]);
		// cal.add(Calendar.DAY_OF_MONTH, betterTime[i]);
		//
		// betterEndTime_model2[i] = cal.getTime();
		// }
		betterBeginTime_model2 = betterBeginTime_model1;
		betterEndTime_model2 = betterEndTime_model1;
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		for (int i = 0; i < dd.length; i++) {
			// try {
			cal.setTime(projectBeginTime);
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			cal.add(Calendar.DAY_OF_MONTH, nowOpenTime[i]);
			betterBeginTime_model2[dd[i] - 1] = cal.getTime();// sdf.format(cal.getTime());
			// try {
			cal1.setTime(betterBeginTime_model2[dd[i] - 1]);
			// } catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			cal1.add(Calendar.DAY_OF_MONTH, time[dd[i] - 1]);
			cal1.add(Calendar.DAY_OF_MONTH, -1 * mm[i]);
			betterEndTime_model2[dd[i] - 1] = cal1.getTime();// sdf.format(cal1.getTime());
		}
		/*
		 * for(int i=0;i<total1.length;i++) for(int j=i;j<total1.length;j++){
		 * if(obj[i][j]==1){ try {
		 * cal.setTime(sdf.parse(betterEndTime_model2[i])); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } cal.add(Calendar.DAY_OF_MONTH, 1);
		 * betterBeginTime_model2[j]=sdf.format(cal.getTime()); try {
		 * cal.setTime(sdf.parse(betterEndTime_model2[j])); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } cal.add(Calendar.DAY_OF_MONTH, 1);
		 * 
		 * betterEndTime_model2[j]=sdf.format(cal.getTime());
		 * 
		 * }
		 * 
		 * }
		 */

		// for (int i = 0; i < total1.length; i++) {
		// System.out.println("工序" + (i + 1) + "优化后开工时间"
		// + betterBeginTime_model2[i]);
		// System.out.println("工序" + (i + 1) + "优化后完工时间"
		// + betterEndTime_model2[i]);
		// }
		return new Date[][] { betterBeginTime_model2, betterEndTime_model2 };
	}

}
