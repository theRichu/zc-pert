package com.ziscloud.zcdiagram.wizard;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.dao.SessionFactory;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.SWTHelper;
import com.ziscloud.zcdiagram.view.ProjectView;

public class AddProjectWizard extends Wizard {
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
		// 添加新创建的工程项目到工程项目列表中
		ProjectView.getListViewer().add(project);
		return true;
	}

}
