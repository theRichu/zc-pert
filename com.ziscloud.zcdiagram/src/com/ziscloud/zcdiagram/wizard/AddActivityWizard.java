package com.ziscloud.zcdiagram.wizard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.Wizard;

import com.ziscloud.zcdiagram.core.IModelChangeProvider;
import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.core.ModelChangedEvent;
import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.Project;

public class AddActivityWizard extends Wizard implements IModelChangeProvider {
	private Project project;
	private Activity activity;
	private AddActivityWizardPage page;
	private TableViewer tableViewer;
	private List<IModelChangedListener> listeners = new ArrayList<IModelChangedListener>();

	public AddActivityWizard(Project project, TableViewer tableViewer,
			IModelChangedListener[] listeners) {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("新工序");
		this.project = project;
		this.tableViewer = tableViewer;
		if (null != listeners) {
			for (IModelChangedListener lst : listeners) {
				this.addModelChangedListener(lst);
			}
		}
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
		// fire the event
		fireModelObjectChanged(null, activity, activity);
		// add it to activity page
		tableViewer.add(activity);
		tableViewer.refresh(activity);
		return true;
	}

	@Override
	public void addModelChangedListener(IModelChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void fireModelChanged(IModelChangedEvent event) {

	}

	@Override
	public void fireModelObjectChanged(Object object, Object oldValue,
			Object newValue) {
		for (IModelChangedListener listener : listeners) {
			listener.modelChanged(new ModelChangedEvent(this,
					IModelChangedEvent.INSERT, oldValue, newValue));
		}
	}

	@Override
	public void removeModelChangedListener(IModelChangedListener listener) {
		listeners.remove(listener);
	}
}
