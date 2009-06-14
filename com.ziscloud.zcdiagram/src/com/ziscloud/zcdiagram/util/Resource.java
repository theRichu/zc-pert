package com.ziscloud.zcdiagram.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Resource {
	private static final String BUNDLE_NAME = "com.ziscloud.zcdiagram.util.resource"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	// KEY For Field Name Of Project
	public static final String P_BUILDER = "FieldName_Project_Builder"; //$NON-NLS-1$
	public static final String P_DESIGNER = "FieldName_Project_Designer"; //$NON-NLS-1$
	public static final String P_MANAGER = "FieldName_Project_Manager"; //$NON-NLS-1$
	public static final String P_NAME = "FieldName_Project_Name"; //$NON-NLS-1$
	public static final String P_PLANPERIOD_UINT = "FieldName_Project_Period_Unit"; //$NON-NLS-1$
	public static final String P_PLANCOST = "FieldName_Project_PlanCost"; //$NON-NLS-1$
	public static final String P_PLANCOST_UNIT = "FieldName_Project_PlanCost_Unit"; //$NON-NLS-1$
	public static final String P_PLANENDDATE = "FieldName_Project_PlanEndDate"; //$NON-NLS-1$
	public static final String P_PLANPERIOD = "FieldName_Project_PlanPeriod"; //$NON-NLS-1$
	public static final String P_PLANSTARTDATE = "FieldName_Project_PlanStartDate"; //$NON-NLS-1$
	public static final String P_REMARKS = "FieldName_Project_Remarks"; //$NON-NLS-1$
	// KEY For Label Text Of Project
	public static final String P_L_BUILDER = "Label_Project_Builder"; //$NON-NLS-1$
	public static final String P_L_DESIGNER = "Label_Project_Designer"; //$NON-NLS-1$
	public static final String P_L_MANAGER = "Label_Project_Manager"; //$NON-NLS-1$
	public static final String P_L_NAME = "Label_Project_Name"; //$NON-NLS-1$
	public static final String P_L_PLANPERIOD_UINT = "Label_Project_Period_Unit"; //$NON-NLS-1$
	public static final String P_L_PLANCOST = "Label_Project_PlanCost"; //$NON-NLS-1$
	public static final String P_L_PLANCOST_UNIT = "Label_Project_PlanCost_Unit"; //$NON-NLS-1$
	public static final String P_L_PLANENDDATE = "Label_Project_PlanEndDate"; //$NON-NLS-1$
	public static final String P_L_PLANPERIOD = "Label_Project_PlanPeriod"; //$NON-NLS-1$
	public static final String P_L_PLANSTARTDATE = "Label_Project_PlanStartDate"; //$NON-NLS-1$
	public static final String P_L_REMARKS = "Label_Project_Remarks"; //$NON-NLS-1$
	// KEY For Field Name Of Activity
	public static final String A_NAME = "Activity_name";
	public static final String A_SYBOL = "Activity_symbol";
	public static final String A_P_PERIOD = "Activity_p_period";
	public static final String A_P_COST = "Activity_p_cost";
	public static final String A_OUTPUT = "Activity_output";
	public static final String A_P_START = "Activity_p_start";
	public static final String A_P_END = "Activity_p_end";
	public static final String A_M_START = "Activity_m_start";
	public static final String A_M_END = "Activity_m_end";
	public static final String A_L_START = "Activity_l_start";
	public static final String A_L_END = "Activity_l_end";
	public static final String A_E_START = "Activity_e_start";
	public static final String A_E_END = "Activity_e_end";
	public static final String A_A_START = "Activity_a_start";
	public static final String A_A_END = "Activity_a_end";
	public static final String A_A_PERIOD = "Activity_a_period";
	public static final String A_A_COST = "Activity_a_cost";
	public static final String A_BUILDER = "Activity_builder";
	public static final String A_R_DAYS = "Activity_r_days";
	public static final String A_R_COST = "Activity_r_cost";
	public static final String A_RMARKS = "Activity_rmarks";
	public static final String A_PRE = "Activity_pre";
	// KEY For Field Name Of Activity
	public static final String L_A_A_COST = "Label_Activity_a_cost";
	public static final String L_A_A_END = "Label_Activity_a_end";
	public static final String L_A_A_PERIOD = "Label_Activity_a_period";
	public static final String L_A_A_START = "Label_Activity_a_start";
	public static final String L_A_BUILDER = "Label_Activity_builder";
	public static final String L_A_E_END = "Label_Activity_e_end";
	public static final String L_A_E_START = "Label_Activity_e_start";
	public static final String L_A_L_END = "Label_Activity_l_end";
	public static final String L_A_L_START = "Label_Activity_l_start";
	public static final String L_A_M_END = "Label_Activity_m_end";
	public static final String L_A_M_START = "Label_Activity_m_start";
	public static final String L_A_NAME = "Label_Activity_name";
	public static final String L_A_OUTPUT = "Label_Activity_output";
	public static final String L_A_P_COST = "Label_Activity_p_cost";
	public static final String L_A_P_END = "Label_Activity_p_end";
	public static final String L_A_P_PERIOD = "Label_Activity_p_period";
	public static final String L_A_P_START = "Label_Activity_p_start";
	public static final String L_A_R_COST = "Label_Activity_r_cost";
	public static final String L_A_R_DAYS = "Label_Activity_r_days";
	public static final String L_A_RMARKS = "Label_Activity_rmarks";
	public static final String L_A_SYBOL = "Label_Activity_symbol";
	public static final String L_A_PRE = "Label_Activity_pre";
	
	public static final String L_TO = "Lable_to";

	private Resource() {
	}

	public static String get(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
