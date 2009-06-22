package com.ziscloud.zcdiagram.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PlatformUI;

import com.ziscloud.zcdiagram.core.IModelChangeProvider;
import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.core.ModelChangedEvent;
import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.editor.ProjectEditor;
import com.ziscloud.zcdiagram.editor.ProjectEditorInput;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.view.ProjectView;

public class UpdateProjectAction extends Action implements IModelChangeProvider {
	private List<IModelChangedListener> listeners = new ArrayList<IModelChangedListener>();
	private ProjectEditor editor;
	private Project project;

	public UpdateProjectAction(String text, ImageDescriptor image,
			ProjectEditor editor, Project project) {
		super(text, image);
		this.editor = editor;
		this.project = project;
	}

	@Override
	public void run() {
		DAOUtil.updateProjectToDatabase(project);
		((ProjectEditorInput)editor.getEditorInput()).setProject(project);
		ProjectView view = (ProjectView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView(
						ProjectView.ID);
		addModelChangedListener(view);
		fireModelChanged(new ModelChangedEvent(this, IModelChangedEvent.CHANGE,
				project, project));
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
