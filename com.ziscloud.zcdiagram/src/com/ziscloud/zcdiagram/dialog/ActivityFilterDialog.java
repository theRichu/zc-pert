package com.ziscloud.zcdiagram.dialog;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.editor.ActivityFilter;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.wizard.WizardUtil;

public class ActivityFilterDialog extends Dialog {
	private Text symbol, name, period, cost, output, rDays, rCost, pStartFrom,
			pEndFrom, mStartFrom, mEndFrom, eStartFrom, eEndFrom, lStartFrom,
			lEndFrom, pStartTo, pEndTo, mStartTo, mEndTo, eStartTo, eEndTo,
			lStartTo, lEndTo;
	private Map<String, Text> fields = new HashMap<String, Text>();
	private TableViewer viewer;

	protected ActivityFilterDialog(Shell parentShell, String title,
			String message) {
		super(parentShell, title, message);
	}

	public ActivityFilterDialog(Shell parentShell, TableViewer viewer) {
		this(parentShell, "筛选工序", "筛选符合条件的工序");
		this.viewer = viewer;
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(6, false);
		layout.marginWidth = 11;
		layout.marginHeight = 20;
		container.setLayout(layout);
		name = WizardUtil.createText(container, 6, Resource.L_A_NAME);
		symbol = WizardUtil.createText(container, 6, Resource.L_A_SYBOL);
		period = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_P_PERIOD, Resource.PERIOD_UINT, "");
		cost = WizardUtil.createTextWithUnit(container, 6, Resource.L_A_P_COST,
				Resource.COST_UNIT, "");
		output = WizardUtil.createText(container, 6, Resource.L_A_OUTPUT);
		rDays = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_R_DAYS, Resource.PERIOD_UINT, "");
		rCost = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_R_COST, Resource.COST_UNIT, "");
		//
		pStartFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_P_START, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		pStartTo = WizardUtil.createDate(container, getShell(), false);
		//
		pEndFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_P_END, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		pEndTo = WizardUtil.createDate(container, getShell(), false);
		//
		mStartFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_M_START, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		mStartTo = WizardUtil.createDate(container, getShell(), false);
		//
		mEndFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_M_END, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		mEndTo = WizardUtil.createDate(container, getShell(), false);
		//
		eStartFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_E_START, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		eStartTo = WizardUtil.createDate(container, getShell(), false);
		//
		eEndFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_E_END, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		eEndTo = WizardUtil.createDate(container, getShell(), false);
		//
		lStartFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_L_START, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		lStartTo = WizardUtil.createDate(container, getShell(), false);
		//
		lEndFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_L_END, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		lEndTo = WizardUtil.createDate(container, getShell(), false);
		//
		fields.put("Symbol", symbol);
		fields.put("Name", name);
		fields.put("PlanPeriod", period);
		fields.put("PlanCost", cost);
		fields.put("Output", output);
		fields.put("RarDays", rDays);
		fields.put("RarCost", rCost);
		//
		fields.put("PlanStartDateFrom", pStartFrom);
		fields.put("PlanEndDateFrom", pEndFrom);
		fields.put("MustStartDateFrom", mStartFrom);
		fields.put("MustEndDateFrom", mEndFrom);
		fields.put("EarlyStartDateFrom", eStartFrom);
		fields.put("EarlyEndDateFrom", eEndFrom);
		fields.put("LaterStartDateFrom", lStartFrom);
		fields.put("LaterEndDateFrom", lEndFrom);
		//
		fields.put("PlanStartDateTo", pStartTo);
		fields.put("PlanEndDateTo", pEndTo);
		fields.put("MustStartDateTo", mStartTo);
		fields.put("MustEndDateTo", mEndTo);
		fields.put("EarlyStartDateTo", eStartTo);
		fields.put("EarlyEndDateTo", eEndTo);
		fields.put("LaterStartDateTo", lStartTo);
		fields.put("LaterEndDateTo", lEndTo);
		return container;
	}

	@Override
	protected Point getInitialSize() {
		Point p = super.getInitialSize();
		p.x = 550;
		return p;
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		viewer.setFilters(new ViewerFilter[] { new ActivityFilter(fields) });
		viewer.refresh();
		super.okPressed();
	}

}
