package com.ziscloud.zcdiagram.wizard;

import java.util.Date;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.BindUtil;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;
import com.ziscloud.zcdiagram.validator.DecimalVerifyListener;
import com.ziscloud.zcdiagram.validator.NumberVerifyListener;

public class AddProjectWizardPage extends WizardPage {
	private Project project;
	private Composite container;
	private Text remarks;
	private Text designer;
	private Text manager;
	private Text builder;
	private Text planStartDate;
	private Text planCost;
	private Text period;
	private Text name;

	protected AddProjectWizardPage() {
		super("工程项目");
		setTitle("工程项目");
		setDescription("新建一个工程项目");
		this.project = new Project();
		this.project.setPlanStartDate(new Date());
		setPageComplete(false);
	}

	@Override
	public void createControl(final Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		WizardUtil.createTip(container, "以下信息为必填内容：");
		name = WizardUtil.createText(container, Resource.P_L_NAME);
		period = WizardUtil.createTextWithUnit(container,
				Resource.P_L_PLANPERIOD, Resource.PERIOD_UINT, "0");
		planCost = WizardUtil.createTextWithUnit(container,
				Resource.P_L_PLANCOST, Resource.COST_UNIT, "0.0");
		planStartDate = WizardUtil.createDate(container, getShell(),
				Resource.P_L_PLANSTARTDATE, false);
		builder = WizardUtil.createText(container, Resource.P_L_BUILDER);
		manager = WizardUtil.createText(container, Resource.P_L_MANAGER);
		SWTHelper.createSeparator(container, 3);
		WizardUtil.createTip(container, "以下信息为选填内容：");
		designer = WizardUtil.createText(container, Resource.P_L_DESIGNER);
		remarks = WizardUtil.createTextarea(container, Resource.P_L_REMARKS);
		configVerifyListener();
		bindValues();
		setControl(container);
	}

	private void configVerifyListener() {
		period.addVerifyListener(new NumberVerifyListener());
		planCost.addVerifyListener(new DecimalVerifyListener());
	}

	private void bindValues() {
		DataBindingContext context = new DataBindingContext();
		BindUtil.bindString(context, true, project, name, "name",
				Resource.P_NAME, SWTHelper.FIELD_MAXLEN);
		BindUtil.bindInteger(context, true, project, period, "planPeriod",
				Resource.P_PLANPERIOD);
		BindUtil.bindDouble(context, true, project, planCost, "planCost",
				Resource.P_PLANCOST);
		BindUtil.bindDate(context, project, planStartDate, "planStartDate",
				Resource.P_PLANSTARTDATE);
		BindUtil.bindString(context, true, project, builder, "builder",
				Resource.P_BUILDER, SWTHelper.FIELD_MAXLEN);
		BindUtil.bindString(context, false, project, remarks, "remarks",
				Resource.P_REMARKS, SWTHelper.TEXT_MAXLEN);
		BindUtil.bindString(context, false, project, designer, "designer",
				Resource.P_DESIGNER, SWTHelper.FIELD_MAXLEN);
		BindUtil.bindString(context, true, project, manager, "manager",
				Resource.P_MANAGER, SWTHelper.FIELD_MAXLEN);

		WizardUtil.updateWizardMessage(this, context);
	}

	public Project getProject() {
		return project;
	}

}