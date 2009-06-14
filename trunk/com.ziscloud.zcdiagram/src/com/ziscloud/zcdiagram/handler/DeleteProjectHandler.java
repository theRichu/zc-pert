package com.ziscloud.zcdiagram.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.ziscloud.zcdiagram.core.IModelChangeProvider;
import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.core.ModelChangedEvent;
import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.editor.ProjectEditorInput;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.view.ProjectView;

public class DeleteProjectHandler extends AbstractHandler implements IHandler,
		IModelChangeProvider {
	private List<IModelChangedListener> listeners = new ArrayList<IModelChangedListener>();

	public DeleteProjectHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the view
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		// confirm the deletion
		if (MessageDialog.openConfirm(window.getShell(), "删除工程项目",
				"工程项目删除后，工程项目所包含的工序信息将会被同时删除，是否继续？")) {
			// get the selection
			Project selected = (Project) ((StructuredSelection) window
					.getSelectionService().getSelection()).getFirstElement();
			// check whether the project is opening.
			IEditorReference[] editorRefs = page.getEditorReferences();
			for (IEditorReference er : editorRefs) {
				IEditorInput input;
				try {
					input = er.getEditorInput();
					if (input instanceof ProjectEditorInput) {
						Project inputProject = ((ProjectEditorInput) input)
								.getProject();
						if (selected.equals(inputProject)) {
							page.closeEditor(er.getEditor(false), false);
						}
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
			selected.setIsDeleted("true");
			DAOUtil.updateProjectToDatabase(selected);
			// fire the event to the project view
			if (page.getActivePart() instanceof ProjectView) {
				addModelChangedListener((ProjectView) page.getActivePart());
				fireModelChanged(new ModelChangedEvent(this,
						IModelChangedEvent.REMOVE, selected, selected));
			}
		}
		return null;
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

	@Override
	public void dispose() {
		listeners.clear();
		super.dispose();
	}

}
