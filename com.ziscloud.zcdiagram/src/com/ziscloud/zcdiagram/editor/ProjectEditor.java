package com.ziscloud.zcdiagram.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.WorkbenchPart;

public class ProjectEditor extends FormEditor {
	public static final String ID = "com.ziscloud.zcdiagram.editor.projectEditor";
	private DiagramEditor diagramEditor = new DiagramEditor();

	public ProjectEditor() {
	}

	@Override
	protected void addPages() {
		try {
			addPage(new ProjectOverviewPage(this));
			addPage(new ActivityPage(this));
			addPage(new ProgressPage(this));
			// addPage(diagramEditor, new DiagramEditorInput(
			// ((ProjectEditorInput) getEditorInput()).getProject()));
			// setPageText(2, "双代号网络图");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		diagramEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * set the part name of this editor, the part name will be shown in the tab
	 * area for the part.
	 * 
	 * @see WorkbenchPart#getPartName()
	 */
	@Override
	public String getPartName() {
		return getEditorInput().getName() + " - 数据";
	}

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}

}
