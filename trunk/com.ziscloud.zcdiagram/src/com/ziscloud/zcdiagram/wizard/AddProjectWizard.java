package com.ziscloud.zcdiagram.wizard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.core.IModelChangeProvider;
import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.core.ModelChangedEvent;
import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.SWTHelper;
import com.ziscloud.zcdiagram.view.ProjectView;

public class AddProjectWizard extends Wizard implements IModelChangeProvider {
	private List<IModelChangedListener> listeners = new ArrayList<IModelChangedListener>();
	private AddProjectWizardPage page;
	private static final Logger LOGGER = Logger
			.getLogger(AddProjectWizard.class);

	public AddProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("新工程");
	}

	@Override
	public void addPages() {
		page = new AddProjectWizardPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		Project project = page.getProject();
		// 通过计划工期和计划开工时间来计算计划完工时间
		project.setPlanEndDate(DateUtils.addDays(project.getPlanStartDate(),
				project.getPlanPeriod()));
		project.setModifyTime(new Date().getTime());
		project.setSymbol(SWTHelper.randomSymbol());
		project.setDrawTime(0L);
		project.setIsDeleted("false");
		ProjectDAO projectDAO = new ProjectDAO();
		Transaction tx = null;
		try {
			tx = SessionFactory.getSession().beginTransaction();
			projectDAO.save(project);
			tx.commit();
		} catch (HibernateException he) {
			if (null != tx) {
				tx.rollback();
			}
			LOGGER.debug("save the info of new project error!", he);
		} finally {
			SessionFactory.closeSession();
		}
		
		// get the project view and register it as the listener
		ProjectView view = (ProjectView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView(
						ProjectView.ID);
		addModelChangedListener(view);
		fireModelChanged(new ModelChangedEvent(this, IModelChangedEvent.INSERT,
				project, project));
		return true;
	}

	@Override
	public void dispose() {
		listeners.clear();
		super.dispose();
	}

	@Override
	public void addModelChangedListener(IModelChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void fireModelChanged(IModelChangedEvent event) {
		for (IModelChangedListener listener : listeners) {
			listener.modelChanged(event);
		}
	}

	@Override
	public void fireModelObjectChanged(Object object, Object oldValue,
			Object newValue) {

	}

	@Override
	public void removeModelChangedListener(IModelChangedListener listener) {
		listeners.remove(listener);
	}

}
