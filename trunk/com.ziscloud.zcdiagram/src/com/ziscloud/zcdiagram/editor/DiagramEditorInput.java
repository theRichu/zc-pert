package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.ziscloud.zcdiagram.pojo.Project;

public class DiagramEditorInput implements IEditorInput {
	public static final int PLAN = 1;
	public static final int MODELONE = 2;
	public static final int MODELTWO = 3;
	public static final int MODELTHREE = 4;
	
	private Project project;
	private int model;

	public DiagramEditorInput(Project project, int model) {
		super();
		this.project = project;
		this.model = model;
	}

	public Project getProject() {
		return project;
	}

	public int getModel() {
		return model;
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
		if (obj instanceof DiagramEditorInput) {
			return project.equals(((DiagramEditorInput) obj).getProject());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return project.hashCode();
	}

}
