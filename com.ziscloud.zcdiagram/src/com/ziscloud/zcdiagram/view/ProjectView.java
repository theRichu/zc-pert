package com.ziscloud.zcdiagram.view;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.provider.ProjectListLabelProvider;

public class ProjectView extends ViewPart implements IModelChangedListener {
	public static final String ID = "com.ziscloud.zcdiagram.ProjectView";
	private static final Logger LOGGER = Logger.getLogger(ProjectView.class);
	private TableViewer projectList;

	public ProjectView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		projectList = new TableViewer(parent, SWT.BORDER);
		projectList.setContentProvider(new ArrayContentProvider());
		projectList.setLabelProvider(new ProjectListLabelProvider());
		projectList.setInput(new ProjectDAO().findByIsDeleted("false"));
		projectList.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IHandlerService handlerService = (IHandlerService) getSite()
						.getService(IHandlerService.class);
				try {
					handlerService
							.executeCommand(
									"com.ziscloud.zcdiagram.commands.openProject",
									null);
				} catch (ExecutionException e) {
					LOGGER.debug("open project failed.", e);
				} catch (NotDefinedException e) {
					LOGGER.debug("open project failed.", e);
				} catch (NotEnabledException e) {
					LOGGER.debug("open project failed.", e);
				} catch (NotHandledException e) {
					LOGGER.debug("open project failed.", e);
				}
			}
		});
		getSite().setSelectionProvider(projectList);
	}

	@Override
	public void setFocus() {

	}

	@Override
	public void modelChanged(IModelChangedEvent event) {
		if (event.getChangeType() == IModelChangedEvent.REMOVE) {
			projectList.remove(event.getOldValue());
			projectList.refresh(event.getOldValue());
		}
		if (event.getChangeType() == IModelChangedEvent.INSERT) {
			projectList.add(event.getNewValue());
			projectList.refresh(event.getNewValue());
		}
		if (event.getChangeType() == IModelChangedEvent.CHANGE) {
			projectList.refresh(event.getNewValue());
		}
	}

}