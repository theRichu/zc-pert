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
	private Text symbol, name, period, cost, output, rDays, rCost, pStart,
			pEnd, mStart, mEnd, eStart, eEnd, lStart, lEnd;
	private Map<String, Text> fields = new HashMap<String, Text>();
	private TableViewer viewer;

	protected ActivityFilterDialog(Shell parentShell, String title,
			String message) {
		super(parentShell, title, message);
	}

	public ActivityFilterDialog(Shell parentShell, TableViewer viewer) {
		this(parentShell, "查找工序", "查找符合条件的工序");
		this.viewer = viewer;
		System.out.println(this.viewer);
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 11;
		layout.marginHeight = 20;
		container.setLayout(layout);
		name = WizardUtil.createText(container, Resource.L_A_NAME);
		symbol = WizardUtil.createText(container, Resource.L_A_SYBOL);
		period = WizardUtil.createTextWithUnit(container,
				Resource.L_A_P_PERIOD, Resource.P_PLANPERIOD_UINT, "");
		cost = WizardUtil.createTextWithUnit(container, Resource.L_A_P_COST,
				Resource.P_PLANCOST_UNIT, "");
		output = WizardUtil.createText(container, Resource.L_A_OUTPUT);
		rDays = WizardUtil.createTextWithUnit(container, Resource.L_A_R_DAYS,
				Resource.P_PLANPERIOD_UINT, "");
		rCost = WizardUtil.createTextWithUnit(container, Resource.L_A_R_COST,
				Resource.P_PLANCOST_UNIT, "");
		pStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_P_START, false, false);
		pEnd = WizardUtil.createDate(container, getShell(), Resource.L_A_P_END,
				false, false);
		mStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_M_START, false, false);
		mEnd = WizardUtil.createDate(container, getShell(), Resource.L_A_M_END,
				false, false);
		eStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_E_START, false, false);
		eEnd = WizardUtil.createDate(container, getShell(), Resource.L_A_E_END,
				false, false);
		lStart = WizardUtil.createDate(container, getShell(),
				Resource.L_A_L_START, false, false);
		lEnd = WizardUtil.createDate(container, getShell(), Resource.L_A_L_END,
				false, false);
		fields.put("Symbol", symbol);
		fields.put("Name", name);
		fields.put("PlanPeriod", period);
		fields.put("PlanCost", cost);
		fields.put("Output", output);
		fields.put("RarDays", rDays);
		fields.put("RarCost", rCost);
		fields.put("PlanStartDate", pStart);
		fields.put("PlanEndDate", pEnd);
		fields.put("MustStartDate", mStart);
		fields.put("MustEndDate", mEnd);
		fields.put("EarlyStartDate", eStart);
		fields.put("EarlyEndDate", eEnd);
		fields.put("LaterStartDate", lStart);
		fields.put("LaterEndDate", lEnd);
		return container;
	}

	@Override
	protected Point getInitialSize() {
		Point p = super.getInitialSize();
		p.x = 450;
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
