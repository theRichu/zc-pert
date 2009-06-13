package com.ziscloud.zcdiagram.wizard;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.Wizard;

import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.Project;

public class AddActivityWizard extends Wizard {
	private Project project;
	private Activity activity;
	private AddActivityWizardPage page;
	private TableViewer tableViewer;

	public AddActivityWizard(Project project, TableViewer tableViewer) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("新工序");
		this.project = project;
		this.tableViewer = tableViewer;
	}

	@Override
	public void addPages() {
		page = new AddActivityWizardPage("添加工序", project);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		this.activity = page.getActivity();
		// calculate the plan end date
		activity.setPlanEndDate(DateUtils.addDays(activity.getPlanStartDate(),
				activity.getPlanPeriod()));
		activity.setSymbol("TEMP");
		// save it to database
		DAOUtil.saveActivityToDatabase(activity);
		// add it to activity page
		tableViewer.add(activity);
		tableViewer.refresh(activity);
		return true;
	}

}
