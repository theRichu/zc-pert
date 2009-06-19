package com.ziscloud.zcdiagram.editor;

import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;

import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.dao.ActivitiyDAO;
import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.dialog.ActivityFilterDialog;
import com.ziscloud.zcdiagram.dialog.ColumnVisibilityDialog;
import com.ziscloud.zcdiagram.optimize.Optimize;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.Resource;

public class OptimizeModelTwoPage extends TableFormPage implements IModelChangedListener {
	private static final String ID = "com.ziscloud.zcdiagram.formpage.optimizeModelTwoPage";
	private static final String PAGE_TITLE = "模型 II 优化";
	private static final String FORM_TITLE = "使用模型 II 对工程项目进行优化";
	private static final TableColumns TABLE_COLUMNS = new TableColumns(
			new String[] { Resource.A_NAME, Resource.A_SYBOL, Resource.A_PRE,
					Resource.A_P_PERIOD, Resource.A_P_COST, Resource.A_OUTPUT,
					Resource.A_P_START, Resource.A_P_END, Resource.A_R_DAYS,
					Resource.A_R_COST, Resource.A_OP_TWO_START,
					Resource.A_OP_TWO_END }, new Boolean[] { false, false,
					false, false, false, false, false, false, false, false,
					false, false });

	public OptimizeModelTwoPage(FormEditor projectEditor) {
		super(projectEditor, ID, PAGE_TITLE, FORM_TITLE, TABLE_COLUMNS);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		// create the tool bar and its item
		IToolBarManager toolBarManager = managedForm.getForm()
				.getToolBarManager();
		toolBarManager.add(new Action("开始优化", ImageUtil.OPT) {
			@Override
			public void run() {
				if (project.getModifyTime() > project.getOptTwoTime()) {
					List<Activity> activities = new ActivitiyDAO()
							.findByProjectOrdered(project);
					Optimize optimize = new Optimize(activities);
					Date[][] result = optimize.modelTwoOptimize();
					for (int i = 0; i < activities.size(); i++) {
						Activity act = activities.get(i);
						act.setOptopStartDate(result[0][i]);
						act.setOptopEndDate(result[1][i]);
						 DAOUtil.updateActivityToDababase(act);
					}
					// update the optimize run time for this project
					project.setOptTwoTime(new Date().getTime());
					 DAOUtil.updateProjectToDatabase(project);
					tableViewer.refresh();
				} else {
					MessageDialog
							.openInformation(shell, "模型 II 优化", "工程项目已经优化！");
				}
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