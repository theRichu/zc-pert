package com.ziscloud.zcdiagram.wizard;

import java.util.Date;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.BindUtil;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;
import com.ziscloud.zcdiagram.validator.DecimalVerifyListener;
import com.ziscloud.zcdiagram.validator.NumberVerifyListener;

public class AddActivityWizardPage extends WizardPage {
	private Activity activity = new Activity();
	private Composite container;
	private Text name;
	private Text planPeriod;
	private Text preActivity;
	private Text planCost;
	private Text output;
	private Text planStart;
	private Text mustStart;
	private Text mustEnd;
	private Text laterStart;
	private Text laterEnd;
	private Text earlyStart;
	private Text earlyEnd;
	private Text rarDays;
	private Text rarCost;
	private Text builder;
	private Text remarks;

	protected AddActivityWizardPage(String pageName, Project project) {
		super(pageName);
		setTitle("工程项目工序");
		setDescription("新建一个工程项目的工序");
		this.activity.setProject(project);
		this.activity.setPlanStartDate(new Date());
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		WizardUtil.createTip(container, "以下信息为必填内容：");
		name = WizardUtil.createText(container, Resource.L_A_NAME);
		planPeriod = WizardUtil.createTextWithUnit(container,
				Resource.L_A_P_PERIOD, Resource.P_PLANPERIOD_UINT, "0");
		planStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_P_START, false);
		preActivity = WizardUtil.createPreText(container, getShell(), activity);
		planCost = WizardUtil.createTextWithUnit(container,
				Resource.L_A_P_COST, Resource.P_PLANCOST_UNIT, "0.0");
		output = WizardUtil.createText(container, Resource.L_A_OUTPUT, "0.0");
		SWTHelper.createSeparator(container, 3);
		WizardUtil.createTip(container, "以下信息为选填内容：");
		mustStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_M_START, true);
		mustEnd = WizardUtil.createDate(container, getShell(),
				Resource.L_A_M_END, true);
		laterStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_L_START, true);
		laterEnd = WizardUtil.createDate(container, getShell(),
				Resource.L_A_L_END, true);
		earlyStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_E_START, true);
		earlyEnd = WizardUtil.createDate(container, getShell(),
				Resource.L_A_E_END, true);
		rarDays = WizardUtil.createTextWithUnit(container, Resource.L_A_R_DAYS,
				Resource.P_PLANPERIOD_UINT, "0");
		rarCost = WizardUtil.createTextWithUnit(container, Resource.L_A_R_COST,
				Resource.P_PLANCOST_UNIT, "0.0");
		builder = WizardUtil.createText(container, Resource.L_A_BUILDER);
		remarks = WizardUtil.createTextarea(container, Resource.L_A_RMARKS);
		configVerifyListener();
		bindValues();
		setControl(container);
	}

	private void configVerifyListener() {
		planPeriod.addVerifyListener(new NumberVerifyListener());
		planCost.addVerifyListener(new DecimalVerifyListener());
		output.addVerifyListener(new DecimalVerifyListener());
		rarDays.addVerifyListener(new NumberVerifyListener());
		rarCost.addVerifyListener(new DecimalVerifyListener());
	}

	private void bindValues() {
		DataBindingContext context = new DataBindingContext();
		BindUtil.bindString(context, true, activity, name, "name",
				Resource.A_NAME, SWTHelper.FIELD_MAXLEN);
		BindUtil.bindString(context, false, activity, preActivity,
				"preActivity", Resource.A_PRE, SWTHelper.FIELD_MAXLEN);
		BindUtil.bindInteger(context, true, activity, planPeriod, "planPeriod",
				Resource.A_P_PERIOD);
		BindUtil.bindDouble(context, true, activity, planCost, "planCost",
				Resource.A_P_COST);
		BindUtil.bindDouble(context, true, activity, output, "output",
				Resource.A_OUTPUT);
		BindUtil.bindDate(context, activity, planStart, "planStartDate",
				Resource.A_P_START);
		BindUtil.bindDate(context, activity, mustStart, "mustStartDate",
				Resource.A_M_START);
		BindUtil.bindDate(context, activity, mustEnd, "mustEndDate",
				Resource.A_M_END);
		BindUtil.bindDate(context, activity, laterStart, "laterStartDate",
				Resource.A_L_START);
		BindUtil.bindDate(context, activity, laterEnd, "laterEndDate",
				Resource.A_L_END);
		BindUtil.bindDate(context, activity, earlyStart, "earlyStartDate",
				Resource.A_E_START);
		BindUtil.bindDate(context, activity, earlyEnd, "earlyEndDate",
				Resource.A_E_END);
		BindUtil.bindInteger(context, false, activity, rarDays, "rarDays",
				Resource.A_R_DAYS);
		BindUtil.bindDouble(context, false, activity, rarCost, "rarCost",
				Resource.A_R_COST);
		BindUtil.bindString(context, false, activity, builder, "builder",
				Resource.A_BUILDER, SWTHelper.FIELD_MAXLEN);
		BindUtil.bindString(context, false, activity, remarks, "remarks",
				Resource.A_RMARKS, SWTHelper.TEXT_MAXLEN);
		WizardUtil.updateWizardMessage(this, context);
	}

	public Activity getActivity() {
		return this.activity;
	}
}
