package com.ziscloud.zcdiagram.optimize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


//所有关键节点数组
//
public class Test{
	
	Date projectBeginTime;
	//String projectBeginTime="2008-10-12";//工程开工时间
	private List<Info> list;
	int[][] obj;//紧前紧后关系矩阵
	int[]time;//每个节点的持续时间
	int [] betterTime;
	int[]total1;//最早开工时间
	int[]total2;//最迟开工时间
	int [] total1_model2;
	int [] total2_model2;
	int [] temp;//表示节点是否是关键路线上的节点
	int [] guanjian_temp;
	int [] guanjian;//一条关键路线上的节点
	int count=1;//一条关键路线上节点个数
	//Date [] betterBeginTime_model1=new Date [11];//MODEL1优化后开工时间
	//Date [] betterEndTime_model1=new Date [11];//MODEL1优化后完工时间
	//Date [] betterBeginTime_model2=new Date [11];//MODEL2优化后开工时间
	//Date [] betterEndTime_model2=new Date [11];//MODEL2优化后完工时间
	
	List<Info> temp_list;
	
	public int level=1;
	public int []order;
	int[][] obj2;
	
	double [] e;  /*用来存放每道工序的费用变化率*/
	int [] aa;/*用来存放每道工序的最大可压缩天数*/
	/**
	 * 模拟数据
	 * 初始化数据
	 */
	public void init(){
		System.out.println(level);
		
		
		Info p1 = new Info(4, "A4","工序2", 2,0.0,0.0,"A1",0,0,0,0);
		Info p2 = new Info(11, "A5","工序3", 4,0,0,"A1",0,0,0,0);
		Info p3 = new Info(3, "A3","工序4", 1,0,0,"A1",0,0,0,0);
		//Info p5 = new Info(5, "A5","工序5", 4,0,0,"A1",0,0);
		Info p4 = new Info(7, "A7","工序6", 3,0,0,"A4",0,0,0,0);
		Info p5 = new Info(8, "A8","工序7", 5,0,0,"A4,A5",0,0,0,0);
		Info p6 = new Info(1,"A1", "工序1", 6,0.0,0.0,null,2,250,0,0);
		Info p7 = new Info(2, "A2","工序8", 4,0,0,"A1",0,0,0,0);
		//Info p4 = new Info(4, "A4","工序4", 2,0,0,"A1",0,0);
		Info p8 = new Info(6,"A6","工序5", 8,0,0,"A3",0,0,0,0);
		Info p9 = new Info(9, "A9","工序9", 5,0,0,"A7",0,0,0,0);
		Info p10 = new Info(10,"A10","工序10", 3,0,0,"A2,A6,A8,A9",0,0,0,0);

		list = new ArrayList<Info>();
		
		
		list.add(p10);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		list.add(p1);
		list.add(p6);
		list.add(p7);
		list.add(p8);
		list.add(p9);
		
		
		
		projectBeginTime=new Date();
		//String projectBeginTime="2008-10-12";//工程开工时间
		int size=list.size()+1;
		obj=new int[size][size];//紧前紧后关系矩阵
		time=new int[size];//每个节点的持续时间
		betterTime=new int [size];
		total1=new int[size];//最早开工时间
		total2=new int[size];//最迟开工时间
		total1_model2=new int [size];
		total2_model2=new int[size];
		temp=new int[size];//表示节点是否是关键路线上的节点
		guanjian_temp=new int [size];
		//Date [] betterBeginTime_model1=new Date [size];//MODEL1优化后开工时间
		//Date [] betterEndTime_model1=new Date [size];//MODEL1优化后完工时间
		//Date [] betterBeginTime_model2=new Date [size];//MODEL2优化后开工时间
		//Date [] betterEndTime_model2=new Date [size];//MODEL2优化后完工时间
		
		temp_list=new ArrayList<Info>();
		
		order=new int [size];
		obj2=new int [size][size];
		
		e=new double[size];  /*用来存放每道工序的费用变化率*/
		aa=new int[size];/*用来存放每道工序的最大可压缩天数*/
		
			
		
	}

	public void b(int t,List list,int [][] obj2)
	{
		//int i,j;
		for(int i=0;i<list.size();i++)
			//for(int j=0;j<4;j++)
				if(obj2[t][i]==1)
				{
					Info info=(Info)list.get(i);
					if(info.getFlag()==0)
					{	
						info.setLevel(level);
						info.setFlag(1);
					}
					else if(info.getLevel()<level)
							{info.setLevel(level);}
					level++;
					b(i,list,obj2);
				}

		level--;
		return;
	}

	
	/**
	 * 解决第一个模型
	 * 得到每一个节点的最早开工时间，最迟开工时间
	 * 关键路线
	 */
	public int[] model1(){
		init();
		/*int x=0;
		for(int i=0;i<list.size();i++){
			Info info1=list.get(i);
			String workNo=info1.getWorkNo();
			if(info1.getPriviousWorkNo()==null||info1.getPriviousWorkNo().trim().equals("")){
				temp_list.add(x, info1);
			}
			for(int j=0;j<list.size();j++){
				Info info2=list.get(j);
				String priviousWork=info2.getPriviousWorkNo();
				if(priviousWork!=null&&!priviousWork.trim().equals("")){
					String [] privious=priviousWork.split(",");
					for(int m=0;m<privious.length;m++){
						if(privious[m].equals(workNo)){
							temp_list.add(x+1,info2);
						}
					}
				}
			}
		}
s		
		for(int i=0;i<temp_list.size();i++){
			System.out.println(temp_list.get(i).getWorkNo());
		}*/

		//System.out.print(list.get(0).getWorkNo());
		Calendar cal = Calendar.getInstance(); 
		Calendar cal1= Calendar.getInstance();
		total1[0]=0;
		
		//得到time,obj
		/*for(Iterator<Info> it = list.iterator();it.hasNext();){
			Info p=it.next();
			int j=p.getId();
			String jinqiangongxu=p.getPriviousWorkNo();
			if(jinqiangongxu!=null&&!jinqiangongxu.trim().equals("")){
				String[] jq=jinqiangongxu.split(",");
				for(int m=0;m<jq.length;m++){
					 for(Iterator<Info> it2 = list.iterator();it2.hasNext();){
						 Info p2=it2.next();
						 if( p2.getWorkNo().equals(jq[m])){
							 int i=p2.getId();
							 if(i!=0){
								obj[i-1][j-1]=1;
							}
						 }
					 }		 
				}
			}
			time[j-1]=p.getLastTime();
			aa[j-1]=p.getMaxReduceTime();
			e[j-1]=p.getReduceCost();
			
		}*/
		
		/*int no=0;
		for(int b=0;b<list.size();b++){
			Info info4=list.get(b);
			if(info4.getPriviousWorkNo()==null||info4.getPriviousWorkNo().trim().equals("")){
				no++;
			}
		}*/
		
		//if(no>1){
		//插入头结点
			Info info=new Info(0, "A0","工序0",0,0,0,null,0,0,0,0);	
			for(int i=0;i<list.size();i++){
				Info p=list.get(i);
				if(p.getPriviousWorkNo()==null||p.getPriviousWorkNo().trim().equals("")){
					obj[0][i]=1;
					p.setPriviousWorkNo("A0");
				}
			}
			list.add(0,info);
		//}
	
		for(int i=0;i<list.size();i++){
			Info p=(Info)list.get(i);
			//int j=p.getId();
			String jinqiangongxu=p.getPriviousWorkNo();
			if(jinqiangongxu!=null&&!jinqiangongxu.trim().equals("")){
				String[] jq=jinqiangongxu.split(",");
				for(int m=0;m<jq.length;m++){
					 for(int j=0;j<list.size();j++){
						 Info p2=(Info)list.get(j);
						 if( p2.getWorkNo().trim().equals(jq[m].trim())){
							 //int i=p2.getId();
							 
								obj2[j][i]=1;
							
						 }
					 }		 
				}
		}
			time[i]=p.getLastTime();
			aa[i]=p.getMaxReduceTime();
			e[i]=p.getReduceCost();
		}
		
		for(int i=0;i<obj2.length;i++)
			for(int j=0;j<obj2[i].length;j++){
				System.out.print(obj2[i][j]);
				if(j==18)
					System.out.print("\n");
			}
		//递归
		int x=0;
		for(x=0;x<list.size();x++){
			Info p=list.get(x);
			String priviousWork=p.getPriviousWorkNo();
			if(priviousWork==null||priviousWork.trim().equals("")){
				p.setLevel(0);
				p.setFlag(0);
				break;
			}
		}
		
		 b(x,list,obj2);
		 
//		 int s=0;
//		 for(int i=0;i<list.size();i++){
//				for(int j=0;j<list.size();j++){
//					Info info=list.get(j);
//					if(info.getLevel()==i)
//					   order[s++]=j;
//					}}
//		 
//		 for(int i=0;i<order.length;i++)
//			 System.out.println(order[i]);
		 
		 //整理list
		 //temp_list=list;
		 
		/* for(int i=0;i<list.size();i++){
			 System.out.println("level="+list.get(i).getLevel()+"flag="+list.get(i).getFlag());
		 }*/

		 int s=0;
		 int d = 0;
		 for(int i=0;i<list.size();i++){
				for(int j=0;j<list.size();j++){
					Info p=list.get(j);
					
					if(p.getLevel()==i)
					   order[s++]=j;
					}
				d = i + 1;
				
		 }
		 
		 for(int i=0;i<order.length;i++){
			 System.out.println("order"+i+"="+order[i]);
		 }
		 
		 for(int i = 0; i < d; i++)
		 {
			 temp_list.add(null);
		 }
		 
		for(int g=0;g<d;g++){
			Info p=list.get(order[g]);
			temp_list.set(g, p);
			
		}

		for(int i=0;i<temp_list.size();i++){
			System.out.println(temp_list.get(i).getWorkNo());
		}
		//整理结束
		
		for(int i=0;i<temp_list.size();i++){
			Info p=(Info)temp_list.get(i);
			//int j=p.getId();
			String jinqiangongxu=p.getPriviousWorkNo();
			if(jinqiangongxu!=null&&!jinqiangongxu.trim().equals("")){
				String[] jq=jinqiangongxu.split(",");
				for(int m=0;m<jq.length;m++){
					 for(int j=0;j<temp_list.size();j++){
						 Info p2=(Info)temp_list.get(j);
						 if( p2.getWorkNo().equals(jq[m])){
							 //int i=p2.getId();
							 
								obj[j][i]=1;
							
						 }
					 }		 
				}
		}
			time[i]=p.getLastTime();
			aa[i]=p.getMaxReduceTime();
			e[i]=p.getReduceCost();
		}
		
		
		//得到total1
		int temp1=0;
		for(int j=0;j<obj.length;j++)
			for(int i=0;i<obj[j].length;i++){
				if(obj[i][j]==1){
					temp1=total1[i]+time[i];				
					for(int k=i+1;k<obj[j].length;k++){
						if(obj[k][j]==1){
							if(total1[k]+time[k]>temp1){
								temp1=total1[k]+time[k];
							}
						}
					}
					total1[j]=temp1;
					break;
				}
		}

		for(int i=0;i<total1.length;i++){
			System.out.println((i+1)+"工序的最短开工时间:"+total1[i]);
		}
		
		// 得到total2
		total2[total2.length-1]=total1[total1.length-1];
		int temp2=0;
		
		for(int i=obj.length-1;i>=0;i--)
			for(int j=obj[i].length-1;j>=0;j--){
				if(obj[i][j]==1){
					temp2=total2[j]-time[i];
					for(int k=j-1;k>=0;k--){
						if(obj[i][k]==1){
							if(total2[k]-time[i]<temp2){
								temp2=total2[k]-time[i];
							}
						}
					}
					total2[i]=temp2;
					break;
				}
			}
		
		for(int i=0;i<total2.length;i++){
			System.out.println(temp_list.get(i).getWorkNo()+"工序的最迟开工时间:"+total2[i]);
		}
		
		//找关键节点
			
		for(int i=0;i<total1.length;i++){
			if(total1[i]-total2[i]==0){
				System.out.println("关键节点有："+temp_list.get(i).getWorkNo());
				temp[i]=1;
			}
		}
		
		
		//找某一条关键路线上所有节点
		guanjian_temp[0]=1;
		for(int i = 0; i < guanjian_temp.length;i++ )
		{
			if(temp[i] == 0)
				continue;
			for(int j = i; j < guanjian_temp.length; j++)
			{
				if (temp[j] == 1 && obj[i][j] == 1)//temp表示是否关键工序,obj表示紧前紧后关系
				{
					guanjian_temp[count++] = j + 1;
					i=j;
				}

			}
		}		
		guanjian=new int[count];
		for(int i=0;i<count;i++){
			guanjian[i]=guanjian_temp[i];
		}
		for(int i=0;i<guanjian.length;i++){
			System.out.println("网络图其中一条关键路线的节点有："+guanjian[i]);
		}
		
		System.out.println("这条路线上关键工序个数为："+count);
		
		/*for(int i=0;i<obj2.length;i++)
			for(int j=0;j<obj2[i].length;j++)
			{
				System.out.print(obj2[i][j]+",");
				if(j==9)
					System.out.print("\n");
			}
		System.out.println("dasdfagadf");
		for(int i=0;i<obj.length;i++)
			for(int j=0;j<obj[i].length;j++)
			{
				System.out.print(obj[i][j]+",");
				if(j==9)
					System.out.print("\n");
			}*/
		
//		for(int i=0;i<temp_list.size();i++){
//			System.out.println(temp_list.get(i).getWorkNo());
//		}
		
		//计算model1优化后开工和完工时间
		for(int i=0;i<total1.length;i++){
			//sdf.format(projectOpenTime);
			//try {
				cal.setTime(projectBeginTime);
				
			//} catch (ParseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}   
		    cal.add(Calendar.DAY_OF_MONTH, total1[i]); 
		    Info p=temp_list.get(i);
		    p.setBetterBeginTime_model1(cal.getTime());
		    //betterBeginTime_model1[i] = ; 
		   // try {
				cal1.setTime(p.getBetterBeginTime_model1());
			//} catch (ParseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		    cal1.add(Calendar.DAY_OF_MONTH, time[i]);    
		    p.setBetterEndTime_model1(cal1.getTime());
		    //betterEndTime_model1[i]=cal1.getTime();
		}
		/*for(int i=0;i<total1.length;i++)
		for(int j=i;j<total1.length;j++){
			if(obj[i][j]==1){
				try {
					cal.setTime(sdf.parse(betterEndTime_model1[i]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cal.add(Calendar.DAY_OF_MONTH, 1);
				betterBeginTime_model1[j]=sdf.format(cal.getTime());
				try {
					cal.setTime(sdf.parse(betterEndTime_model1[j]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cal.add(Calendar.DAY_OF_MONTH, 1);    

			    betterEndTime_model1[j]=sdf.format(cal.getTime());
			}
		}*/
		
		
		/*for(int i=0;i<total1.length;i++){
			System.out.println("工序"+temp_list.get(i).getWorkNo()+"优化后开工时间"+betterBeginTime_model1[i]);
			System.out.println("工序"+temp_list.get(i).getWorkNo()+"优化后完工时间"+betterEndTime_model1[i]);
		}*/
		
		return guanjian;
	}
	
	public void model2(){
		int [] dd=model1();
		double MAXSY=0.0;     /*MAXSY表示最后的压缩后的最大收益*/
		double  bonus=260;        /*用来表示压缩一天 带来的收益*/
		int temp0=0;         /*用来表示最后一道工序的紧前工序的初始值*/
		double [] cc=new double[dd.length];    /*用来存放每道关键工序的费用变化率*/
		int [] mm=new int[dd.length];        /*用来存放每道关键工序的最后实际压缩的天数*/
		int [] nowOpenTime=new int[dd.length];
		int sum=0;            /*表示目前为止已经压缩的总天数*/
		int [] ff=new int[temp.length];         /*用来标记每道关键工序是否已经压缩过*/
		
		for(int k=0;k<temp.length;k++){
			if(obj[k][temp.length-1]==1&&temp[k]!=1&&total1[k]+time[k]>temp0){
				temp0=total1[k]+time[k];
			}
		}
		int MAXKYS=total1[total1.length-1]-temp0;    /*用来表示整个项目可压缩的天数总合*/
		System.out.println("最大可压缩总天数"+MAXKYS);
		
		for(int i=0;i<dd.length;i++){
			cc[i]=e[dd[i]-1];
		}

		for(int i=0;i<count;i++)
		{
				double temp1=1e9;    // 用来表示关键工序的费用变化率的初始值
				int k=0,s=0;         //K表示实际网络图中关键工序的接点序号，s表示存放关键工序费用变化率数组中该工序的下标号，其范围是从0到number-1,并不能代表实际的惯技工序接点号
				for(int j=0;j<count;j++)
				{
					if((cc[j] < temp1) && (ff[j] == 0) && (cc[j] <= bonus) && (cc[j] >0))
					{
						s=j;
						temp1=cc[s];
					}

				}
				
				if(temp1==1e9)  break;
				k=dd[s]-1;
				System.out.println("可压缩的关键工序按费用变化率由小到大的节点依次为："+(k+1));
				if(sum>MAXKYS)
					break;
				if(sum+aa[k]>MAXKYS)
					mm[s]=MAXKYS-sum;
				else
					mm[s]=aa[k];
				ff[s]=1;
				sum+=mm[s];
			}

			double cost=0.0;    // 用来表示压缩所有工序带来的总成本
			int total=0;     // 用来表示压缩关键工序的总天数
			for(int i=0;i<count;i++)
			{
				cost+=(mm[i]*cc[i]);
				total+=mm[i];
				System.out.println("关键工序"+temp_list.get(dd[i]-1).getWorkNo()+"压缩的天数是"+mm[i]);
			}
			
			for(int i=0;i<dd.length;i++){
				System.out.println("节点"+temp_list.get(dd[i]-1).getWorkNo());
			}
			nowOpenTime[0]=0;
	
			for(int i=0;i<dd.length-1;i++){
				nowOpenTime[i+1]=nowOpenTime[i]+time[dd[i]-1]-mm[i];
			}
			
			//相对的时间
			for(int i=0;i<dd.length;i++){
				System.out.println("实际开工时间:"+nowOpenTime[i]);
			}
			MAXSY=bonus*total-cost;
			System.out.println("由于压缩带来的总成本为："+cost);
			System.out.println("压缩的总天数为："+total);
			System.out.println("最大的压缩收益为："+MAXSY);
			
			//计算model2优化后开工和完工时间
			betterTime=time;
			for(int i=0;i<mm.length;i++){
				betterTime[dd[i]-1]=betterTime[dd[i]-1]-mm[i];
			}
			
			total1_model2[0]=0;
			int temp1=0;
			for(int j=0;j<obj.length;j++)
				for(int i=0;i<obj[j].length;i++){
					if(obj[i][j]==1){
						temp1=total1_model2[i]+betterTime[i];				
						for(int k=i+1;k<obj[j].length;k++){
							if(obj[k][j]==1){
								if(total1_model2[k]+betterTime[k]>temp1){
									temp1=total1_model2[k]+betterTime[k];
								}
							}
						}
						total1_model2[j]=temp1;
						break;
					}
				
			}

			for(int i=0;i<total1.length;i++){
				System.out.println(temp_list.get(i).getWorkNo()+"工序的最短开工时间:"+total1_model2[i]);
			}
			
			// 得到total2
			total2_model2[total2_model2.length-1]=total1_model2[total1_model2.length-1];
			int temp2=0;
			
			for(int i=obj.length-1;i>=0;i--)
				for(int j=obj[i].length-1;j>=0;j--){
					if(obj[i][j]==1){
						temp2=total2_model2[j]-betterTime[i];
						for(int k=j-1;k>=0;k--){
							if(obj[i][k]==1){
								if(total2_model2[k]-betterTime[i]<temp2){
									temp2=total2_model2[k]-betterTime[i];
								}
							}
						}
						total2_model2[i]=temp2;
						break;
					}
				}
			
			for(int i=0;i<total2.length;i++){
				System.out.println(temp_list.get(i).getWorkNo()+"工序的最迟开工时间:"+total2_model2[i]);
			}
			
			Calendar cal = Calendar.getInstance();
			
			for(int i=0;i<total1.length;i++){
				//sdf.format(projectOpenTime);
				//try {
					cal.setTime(projectBeginTime);
					Info p=temp_list.get(i);
					
				//} catch (ParseException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}   
			    cal.add(Calendar.DAY_OF_MONTH, total1_model2[i]);   
			    p.setBetterBeginTime_model2(cal.getTime());
			    //betterBeginTime_model2[i] = cal.getTime(); 
			   // try {
					cal.setTime(p.getBetterBeginTime_model2());
				//} catch (ParseException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
			    cal.add(Calendar.DAY_OF_MONTH, betterTime[i]);    
			    p.setBetterEndTime_model2(cal.getTime());
			    //betterEndTime_model2[i]=cal.getTime();
			}
		/*	for(int i=0;i<total1.length;i++)
			for(int j=i;j<total1.length;j++){
				if(obj[i][j]==1){
					try {
						cal.setTime(sdf.parse(betterEndTime_model2[i]));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cal.add(Calendar.DAY_OF_MONTH, 1);
					betterBeginTime_model2[j]=sdf.format(cal.getTime());
					try {
						cal.setTime(sdf.parse(betterEndTime_model2[j]));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cal.add(Calendar.DAY_OF_MONTH, 1);    

				    betterEndTime_model2[j]=sdf.format(cal.getTime());
				
				}
				
			}*/
			
			 SimpleDateFormat f=new SimpleDateFormat("yyyy年MM月dd日");

			
			for(int i=0;i<total1.length;i++){
				System.out.println("工序"+temp_list.get(i).getWorkNo()+"优化前开工时间"+f.format(temp_list.get(i).getBetterBeginTime_model1()));
				System.out.println("工序"+temp_list.get(i).getWorkNo()+"优化前完工时间"+f.format(temp_list.get(i).getBetterEndTime_model1()));
			}
			
			for(int i=0;i<total1.length;i++){
				System.out.println("工序"+temp_list.get(i).getWorkNo()+"优化后开工时间"+f.format(temp_list.get(i).getBetterBeginTime_model2()));
				System.out.println("工序"+temp_list.get(i).getWorkNo()+"优化后完工时间"+f.format(temp_list.get(i).getBetterEndTime_model2()));
			}

	}
	
	public static void  main(String[] xyz) {
		Test test = new Test();
		test.model1();
	}
	
}
