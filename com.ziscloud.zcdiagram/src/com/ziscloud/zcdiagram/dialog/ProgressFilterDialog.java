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

public class ProgressFilterDialog extends Dialog {
	private Text symbol, name, pPeriod, pCost, aPeriod, aCost, pStartFrom,
			pEndFrom, aStartFrom, aEndFrom, pStartTo, pEndTo, aStartTo, aEndTo;
	private Map<String, Text> fields = new HashMap<String, Text>();
	private TableViewer viewer;

	protected ProgressFilterDialog(Shell parentShell, String title,
			String message) {
		super(parentShell, title, message);
	}

	public ProgressFilterDialog(Shell parentShell, TableViewer viewer) {
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
		pPeriod = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_P_PERIOD, Resource.PERIOD_UINT, "");
		pCost = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_P_COST, Resource.COST_UNIT, "");
		aPeriod = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_A_PERIOD, Resource.PERIOD_UINT, "");
		aCost = WizardUtil.createTextWithUnit(container, 6,
				Resource.L_A_A_COST, Resource.COST_UNIT, "");
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
		aStartFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_A_START, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		aStartTo = WizardUtil.createDate(container, getShell(), false);
		//
		aEndFrom = WizardUtil.createDate(container, getShell(),
				Resource.L_A_A_END, false, false);
		WizardUtil.createLabel(container, Resource.L_TO);
		aEndTo = WizardUtil.createDate(container, getShell(), false);
		//
		fields.put("Symbol", symbol);
		fields.put("Name", name);
		fields.put("PlanPeriod", pPeriod);
		fields.put("PlanCost", pCost);
		fields.put("ActualPeriod", aPeriod);
		fields.put("ActualCost", aCost);
		//
		fields.put("PlanStartDateFrom", pStartFrom);
		fields.put("PlanEndDateFrom", pEndFrom);
		fields.put("ActualStartDateFrom", aStartFrom);
		fields.put("ActualEndDateFrom", aEndFrom);
		//
		fields.put("PlanStartDateTo", pStartTo);
		fields.put("PlanEndDateTo", pEndTo);
		fields.put("ActualStartDateTo", aStartTo);
		fields.put("ActualEndDateTo", aEndTo);
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
