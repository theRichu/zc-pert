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

import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.provider.ProjectListLabelProvider;

public class ProjectView extends ViewPart {
	public static final String ID = "com.ziscloud.zcdiagram.ProjectView";
	private static final Logger LOGGER = Logger.getLogger(ProjectView.class);
	private static TableViewer listViewer;

	public ProjectView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		listViewer = new TableViewer(parent, SWT.BORDER);
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new ProjectListLabelProvider());
		listViewer.setInput(new ProjectDAO().findAll());
		listViewer.addDoubleClickListener(new IDoubleClickListener() {
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
	}

	@Override
	public void setFocus() {

	}

	public static TableViewer getListViewer() {
		return listViewer;
	}
	
}