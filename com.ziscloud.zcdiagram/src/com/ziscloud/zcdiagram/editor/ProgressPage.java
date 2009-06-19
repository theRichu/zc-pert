package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;

import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.dialog.ColumnVisibilityDialog;
import com.ziscloud.zcdiagram.dialog.ProgressFilterDialog;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.Resource;

public class ProgressPage extends TableFormPage implements
		IModelChangedListener {
	private static final String ID = "com.ziscloud.zcdiagram.formpage.progressPage";
	private static final String PAGE_TITLE = "工程项目进度控制";
	private static final String FORM_TITLE = "工程项目进度情况";
	private static final TableColumns TABLE_COLUMNS = new TableColumns(
			new String[] { Resource.A_NAME, Resource.A_SYBOL,
					Resource.A_P_PERIOD, Resource.A_P_COST, Resource.A_P_START,
					Resource.A_P_END, Resource.A_A_START, Resource.A_A_PERIOD,
					Resource.A_A_END, Resource.A_A_COST }, new Boolean[] {
					false, false, false, false, false, false, true, true,
					false, true });

	public ProgressPage(FormEditor projectEditor) {
		super(projectEditor, ID, PAGE_TITLE, FORM_TITLE, TABLE_COLUMNS);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		// create the tool bar and its item
		IToolBarManager toolBarManager = managedForm.getForm()
				.getToolBarManager();
		toolBarManager.add(new Action("显示未开工的工序", ImageUtil.NOTDO_ACT) {

			@Override
			public void run() {
				tableViewer.setFilters(new ViewerFilter[] { new ProgressFilter(
						new String[] { "notDO" }) });
				tableViewer.refresh();
			}

		});
		toolBarManager.add(new Action("显示正在施工的工序", ImageUtil.DOING_ACT) {

			@Override
			public void run() {
				tableViewer.setFilters(new ViewerFilter[] { new ProgressFilter(
						new String[] { "doing" }) });
				tableViewer.refresh();
			}

		});
		toolBarManager.add(new Action("显示已完工的工序", ImageUtil.DONE_ACT) {

			@Override
			public void run() {
				tableViewer.setFilters(new ViewerFilter[] { new ProgressFilter(
						new String[] { "done" }) });
				tableViewer.refresh();
			}

		});
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
				ProgressFilterDialog dialog = new ProgressFilterDialog(shell,
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
	}

	@Override
	public void modelChanged(IModelChangedEvent event) {
		if (event.getChangeType() == IModelChangedEvent.CHANGE) {
			Object o = event.getNewValue();
			if (o instanceof Activity && null != tableViewer) {
				Activity act = (Activity) o;
				tableViewer.refresh(act);
			}
		}
	}

}
