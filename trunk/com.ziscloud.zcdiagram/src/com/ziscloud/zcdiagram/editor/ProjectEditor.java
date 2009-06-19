package com.ziscloud.zcdiagram.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.WorkbenchPart;

import com.ziscloud.zcdiagram.core.IModelChangedListener;

public class ProjectEditor extends FormEditor {
	public static final String ID = "com.ziscloud.zcdiagram.editor.projectEditor";
	private DiagramEditor diagramEditor = new DiagramEditor();
	private ProjectOverviewPage overviewPage;
	private ActivityPage activityPage;
	private ProgressPage progressPage;
	private OptimizeModelOnePage modelOnePage;
	private OptimizeModelTwoPage modelTwoPage;

	public ProjectEditor() {
	}

	@Override
	protected void addPages() {
		try {
			overviewPage = new ProjectOverviewPage(this);
			progressPage = new ProgressPage(this);
			modelOnePage = new OptimizeModelOnePage(this);
			modelTwoPage = new OptimizeModelTwoPage(this);

			activityPage = new ActivityPage(this, new IModelChangedListener[] {
					progressPage, modelOnePage, modelTwoPage });

			addPage(overviewPage);
			addPage(activityPage);
			addPage(progressPage);
			addPage(modelOnePage);
			addPage(modelTwoPage);
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
