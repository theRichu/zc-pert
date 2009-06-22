package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;

import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.dialog.ActivityFilterDialog;
import com.ziscloud.zcdiagram.dialog.ColumnVisibilityDialog;
import com.ziscloud.zcdiagram.handler.DeleteActivity;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.wizard.AddActivityWizard;

public class ActivityPage extends TableFormPage {
	private static final String ID = "com.ziscloud.zcdiagram.formpage.activitypage";
	private static final String PAGE_TITLE = "工程工序列表";
	private static final String FORM_TITLE = "工程项目工序信息";
	protected DeleteActivity delActivityAction;
	private IModelChangedListener[] listeners;
	private static final TableColumns TABLE_COLUMNS = new TableColumns(
			new String[] { Resource.A_NAME, Resource.A_SYBOL, Resource.A_PRE,
					Resource.A_P_PERIOD, Resource.A_P_COST, Resource.A_OUTPUT,
					Resource.A_P_START, Resource.A_P_END, Resource.A_M_START,
					Resource.A_M_END, Resource.A_L_START, Resource.A_L_END,
					Resource.A_E_START, Resource.A_E_END, Resource.A_BUILDER,
					Resource.A_R_DAYS, Resource.A_R_COST, Resource.A_RMARKS },
			new Boolean[] { true, false, true, true, true, true, true, false,
					true, true, true, true, true, true, true, true, true, true });

	public ActivityPage(FormEditor projectEditor,
			IModelChangedListener[] listeners) {
		super(projectEditor, ID, PAGE_TITLE, FORM_TITLE, TABLE_COLUMNS);
		this.listeners = listeners;
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		delActivityAction = new DeleteActivity(shell,listeners);
		// create the tool bar and its item
		IToolBarManager toolBarManager = managedForm.getForm()
				.getToolBarManager();
		toolBarManager.add(new Action("添加工序", ImageUtil.NEW_ACT) { //$NON-NLS-1$
					@Override
					public void run() {
						AddActivityWizard wizard = new AddActivityWizard(
								((ProjectEditorInput) (getEditorInput()))
										.getProject(), tableViewer,listeners);
						WizardDialog dialog = new WizardDialog(shell, wizard);
						dialog.open();
					}
				});
		toolBarManager.add(delActivityAction);
		toolBarManager.add(new Action("选择显示的列", ImageUtil.COLUMNVISIBILITY) {
			@Override
			public void run() {
				TableColumn[] tableColumns = table.getColumns();
				ColumnVisibilityDialog dialog = new ColumnVisibilityDialog(
						shell, columns, tableColumns);
				dialog.open();
			}

		});
		toolBarManager.add(new Action("过滤工序", ImageUtil.FILTER) {
			@Override
			public void run() {
				ActivityFilterDialog dialog = new ActivityFilterDialog(shell,
						tableViewer);
				dialog.open();
			}
		});
		toolBarManager.add(new Action("清除过滤器", ImageUtil.FILTER_NUll) {

			@Override
			public void run() {
				tableViewer.setFilters(new ViewerFilter[] {});
				tableViewer.refresh();
			}

		});
		toolBarManager.update(true);
		// register the delete activity action as selection changed listener
		tableViewer.addSelectionChangedListener(delActivityAction);
		for (IModelChangedListener listener : listeners) {
			getCellModifier().addModelChangedListener(listener);
		}
	}

}