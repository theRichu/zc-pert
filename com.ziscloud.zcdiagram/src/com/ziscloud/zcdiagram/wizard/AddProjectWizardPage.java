package com.ziscloud.zcdiagram.wizard;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.dialog.CalendarDialog;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class AddProjectWizardPage extends WizardPage implements ModifyListener {
	private Project project;
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
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);
		{
			Label requiredTip = new Label(container, SWT.NONE);
			GridData requiredTipLData = new GridData();
			requiredTipLData.horizontalSpan = 3;
			requiredTip.setLayoutData(requiredTipLData);
			requiredTip.setText("以下信息为必填内容：");
		}
		{
			new Label(container, SWT.NONE).setText("工程名称：");
		}
		{
			GridData nameLData = new GridData(GridData.FILL_HORIZONTAL);
			name = new Text(container, SWT.BORDER);
			name.setLayoutData(nameLData);
			name.addModifyListener(this);
		}
		{
			SWTHelper.placeHolder(container);
		}
		{
			new Label(container, SWT.NONE).setText("计划工期：");
		}
		{
			GridData periodLData = new GridData(GridData.FILL_HORIZONTAL);
			period = new Text(container, SWT.BORDER);
			period.setLayoutData(periodLData);
			period.addModifyListener(this);
			period.addVerifyListener(new VerifyListener() {
				@Override
				public void verifyText(VerifyEvent e) {
					if (!StringUtils.isNumeric(e.text)) {
						e.doit = false;
					}
				}

			});
		}
		{
			new Label(container, SWT.NONE).setText("日");
		}
		{
			new Label(container, SWT.NONE).setText("预算资金：");
		}
		{
			GridData planCostLData = new GridData(GridData.FILL_HORIZONTAL);
			planCost = new Text(container, SWT.BORDER);
			planCost.setLayoutData(planCostLData);
			planCost.addModifyListener(this);
			planCost.addVerifyListener(new VerifyListener() {
				@Override
				public void verifyText(VerifyEvent e) {
					if (!SWTHelper.isDecimal(new StringBuilder(planCost
							.getText()).insert(e.start, e.text).toString())) {
						e.doit = false;
					}
				}

			});
		}
		{
			new Label(container, SWT.NONE).setText("万元    ");
		}
		{
			new Label(container, SWT.NONE).setText("计划开工时间：");
		}
		{
			planStartDate = new Text(container, SWT.BORDER | SWT.READ_ONLY);
			planStartDate.setEditable(false);
			GridData planStartDateData = new GridData(GridData.FILL_HORIZONTAL);
			planStartDate.setLayoutData(planStartDateData);
			planStartDate.addModifyListener(this);
			planStartDate.setText(DateFormatUtils.format(new Date(),
					SWTHelper.DATE_PATERN));

			Button pickButton = new Button(container, SWT.PUSH);
			GridData pickButtonLData = new GridData(GridData.FILL);
			pickButton.setLayoutData(pickButtonLData);
			pickButton.setText("   选择日期   ");
			pickButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					CalendarDialog dialog = new CalendarDialog(getShell(),planStartDate,
							"选择日期", "工程项目计划开工时间：", planStartDate.getText(),false);
					dialog.open();
				}
			});
		}
		{
			new Label(container, SWT.NONE).setText("施工单位：");
		}
		{
			GridData builderLData = new GridData(GridData.FILL_HORIZONTAL);
			builder = new Text(container, SWT.BORDER);
			builder.setLayoutData(builderLData);
			builder.addModifyListener(this);
		}
		{
			SWTHelper.placeHolder(container);
		}
		{
			new Label(container, SWT.NONE).setText("负责人：");
		}
		{
			GridData managerLData = new GridData(GridData.FILL_HORIZONTAL);
			manager = new Text(container, SWT.BORDER);
			manager.setLayoutData(managerLData);
			manager.addModifyListener(this);
		}
		{
			SWTHelper.placeHolder(container);
		}
		{
			Label horizontal = new Label(container, SWT.SEPARATOR
					| SWT.HORIZONTAL);
			GridData horizontalLData = new GridData(GridData.FILL_HORIZONTAL);
			horizontalLData.heightHint = 20;
			horizontalLData.horizontalSpan = 3;
			horizontal.setLayoutData(horizontalLData);
		}
		{
			Label optionalTip = new Label(container, SWT.NONE);
			GridData optionalTipLData = new GridData(GridData.FILL_HORIZONTAL);
			optionalTipLData.horizontalSpan = 3;
			optionalTip.setLayoutData(optionalTipLData);
			optionalTip.setText("以下信息为可选内容：");
		}
		{
			new Label(container, SWT.NONE).setText("设计单位：");
		}
		{
			GridData designerLData = new GridData(GridData.FILL_HORIZONTAL);
			designer = new Text(container, SWT.BORDER);
			designer.setLayoutData(designerLData);
			designer.addModifyListener(this);
		}
		{
			SWTHelper.placeHolder(container);
		}
		{
			new Label(container, SWT.NONE).setText("备注：");
		}
		{
			GridData remarksLData = new GridData(GridData.FILL_BOTH);
			remarksLData.verticalSpan = 18;
			remarks = new Text(container, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
					| SWT.BORDER);
			remarks.setLayoutData(remarksLData);
			remarks.addModifyListener(this);
		}
		{
			SWTHelper.placeHolder(container);
		}

		setControl(container);
	}

	private void errorMessage(String message) {
		setErrorMessage(message);
		setPageComplete(false);
	}

	public Project getProject() {
		return project;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		// 工程名称
		String nameStr = name.getText();
		if (StringUtils.isBlank(nameStr)) {
			errorMessage("工程名称为必填项。");
			return;
		}
		nameStr = StringUtils.trim(nameStr);
		if (nameStr.length() > 255) {
			errorMessage("工程名称最大长度为255个字符。");
			return;
		}
		project.setName(nameStr);
		// 计划工期
		String periodStr = period.getText();
		if (StringUtils.isBlank(periodStr)) {
			errorMessage("计划工期为必填项。");
			return;
		}
		if (!StringUtils.isNumeric(periodStr)) {
			errorMessage("工程项目只能为正整数。");
			return;
		}
		int periodInt = 0;
		try {
			periodInt = Integer.parseInt(periodStr);
		} catch (NumberFormatException nfe) {
			errorMessage("工程工期的范围：0 - " + Integer.MAX_VALUE);
			return;
		}
		project.setPlanPeriod(periodInt);
		// 预算资金
		String costStr = planCost.getText();
		if (StringUtils.isBlank(costStr)) {
			errorMessage("预算资金为必填项。");
			return;
		}
		if (!SWTHelper.isDecimal(costStr)) {
			errorMessage("预算资金只能为数字");
			return;
		}
		double costdoub = 0.0;
		try {
			costdoub = Double.parseDouble(costStr);
		} catch (NumberFormatException nfe) {
			errorMessage("预算资金只能为数字");
			return;
		}
		if (Double.MAX_VALUE < costdoub) {
			errorMessage("预算资金的范围：0 - " + Double.MAX_VALUE);
			return;
		}
		project.setPlanCost(costdoub);
		// 计划开工时间
		try {
			project.setPlanStartDate(DateUtils.parseDate(planStartDate
					.getText(), new String[] { SWTHelper.DATE_PATERN }));
		} catch (ParseException pe) {
			errorMessage("将工程项目计划开工时间从String转换为Date时出错。");
			return;
		}
		// 施工单位
		String builderStr = builder.getText();
		if (StringUtils.isBlank(builderStr)) {
			errorMessage("施工单位为必填项。");
			return;
		}
		builderStr = StringUtils.trim(builderStr);
		if (builderStr.length() > 255) {
			errorMessage("施工单位最大长度为255个字符。");
			return;
		}
		project.setBuilder(builderStr);
		// 负责人
		String managerStr = manager.getText();
		if (StringUtils.isBlank(managerStr)) {
			errorMessage("负责人为必填项。");
			return;
		}
		managerStr = StringUtils.trim(managerStr);
		if (managerStr.length() > 255) {
			errorMessage("负责人最大长度为255个字符。");
			return;
		}
		project.setManager(managerStr);
		// 设计单位
		String designerStr = designer.getText();
		if (StringUtils.trimToEmpty(designerStr).length() > 255) {
			errorMessage("设计单位的最大长度为255个字符");
			return;
		}
		if (!StringUtils.isBlank(designerStr)) {
			project.setDesigner(StringUtils.trim(designerStr));
		}
		// 备注
		String remarkStr = remarks.getText();
		if (StringUtils.trimToEmpty(remarkStr).length() > 65535) {
			errorMessage("备注的最大长度为65535。");
			return;
		}
		if (!StringUtils.isBlank(remarkStr)) {
			project.setRemarks(StringUtils.trim(remarkStr));
		}
		setErrorMessage(null);
		setPageComplete(true);
	}
}