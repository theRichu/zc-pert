package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.ziscloud.zcdiagram.pojo.Project;

public class ProjectEditorInput implements IEditorInput {
	private Project project;

	public ProjectEditorInput(Project project) {
		super();
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return project.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return project.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj instanceof ProjectEditorInput) {
			return project.equals(((ProjectEditorInput) obj).getProject());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return project.hashCode();
	}
}
