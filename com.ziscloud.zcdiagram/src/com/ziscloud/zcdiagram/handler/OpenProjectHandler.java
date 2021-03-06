package com.ziscloud.zcdiagram.handler;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.ziscloud.zcdiagram.editor.ProjectEditor;
import com.ziscloud.zcdiagram.editor.ProjectEditorInput;
import com.ziscloud.zcdiagram.pojo.Project;

public class OpenProjectHandler extends AbstractHandler implements IHandler {
	private static final Logger LOGGER = Logger
			.getLogger(OpenProjectHandler.class);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		// Get the selection
		ISelection selection = (StructuredSelection) window
				.getSelectionService().getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			// If we had a selection lets open the editor
			if (obj != null) {
				Project project = (Project) obj;
				try {
					page.openEditor(new ProjectEditorInput(project),
							ProjectEditor.ID);
				} catch (PartInitException e) {
					LOGGER.debug("Failed to open project Editor.", e);
				}
			}
		}
		return null;
	}

}
