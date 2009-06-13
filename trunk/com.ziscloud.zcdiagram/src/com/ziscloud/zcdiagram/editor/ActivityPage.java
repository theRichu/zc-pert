package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.ziscloud.zcdiagram.dao.ActivitiyDAO;
import com.ziscloud.zcdiagram.dialog.ActivityFilterDialog;
import com.ziscloud.zcdiagram.dialog.ColumnVisibilityDialog;
import com.ziscloud.zcdiagram.provider.ActivityTableLabelProvider;
import com.ziscloud.zcdiagram.strategy.DoubleClickColumnViewerEditorActivationStrategy;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.validator.DecimalVerifyListener;
import com.ziscloud.zcdiagram.validator.NumberVerifyListener;
import com.ziscloud.zcdiagram.wizard.AddActivityWizard;

public class ActivityPage extends FormPage {
	private static final String ID = "com.ziscloud.zcdiagram.formpage.activitypage";
	private static final String TITLE = "工程工序";
	private Table table;
//	private static TableViewer tableViewer;
	private TableViewer tableViewer;
	private String[] columnProperties = new String[] { Resource.A_NAME,
			Resource.A_SYBOL, Resource.A_PRE, Resource.A_P_PERIOD,
			Resource.A_P_COST, Resource.A_OUTPUT, Resource.A_P_START,
			Resource.A_P_END, Resource.A_M_START, Resource.A_M_END,
			Resource.A_L_START, Resource.A_L_END, Resource.A_E_START,
			Resource.A_E_END, Resource.A_A_START, Resource.A_A_END,
			Resource.A_A_PERIOD, Resource.A_A_COST, Resource.A_BUILDER,
			Resource.A_R_DAYS, Resource.A_R_COST, Resource.A_RMARKS };
	private Shell shell;

	public ActivityPage(FormEditor projectEditor) {
		super(projectEditor, ID, TITLE);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		this.shell = getSite().getWorkbenchWindow().getWorkbench()
		.getActiveWorkbenchWindow().getShell();
		//
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm scrolledForm = managedForm.getForm();
		scrolledForm.setText("工程项目工序信息");
		final Form form = scrolledForm.getForm();
		toolkit.decorateFormHeading(form);
		// create the tool bar and its item
		IToolBarManager toolBarManager = form.getToolBarManager();
		toolBarManager.add(new Action("添加工序", ImageUtil.NEW_ACT) { //$NON-NLS-1$
					@Override
					public void run() {
						AddActivityWizard wizard = new AddActivityWizard(
								((ProjectEditorInput) (getEditorInput()))
										.getProject(), tableViewer);
						WizardDialog dialog = new WizardDialog(shell, wizard);
						dialog.open();
					}
				});
		toolBarManager
				.add(new Action("选择显示的列", ImageUtil.COLUMNVISIBILITY) {
					@Override
					public void run() {
						TableColumn[] tableColumns = table.getColumns();
						ColumnVisibilityDialog dialog = new ColumnVisibilityDialog(
								shell, columnProperties, tableColumns);
						dialog.open();
					}

				});
		toolBarManager.add(new Action("查找工序", ImageUtil.FILTER) {
			@Override
			public void run() {
				ActivityFilterDialog dialog = new ActivityFilterDialog(shell,
						tableViewer);
				System.out.println(dialog);
				dialog.open();
			}
		});
		toolBarManager.update(true);
		// create content for this page
		scrolledForm.getBody().setLayout(new FillLayout());
		table = toolkit.createTable(scrolledForm.getBody(), SWT.MULTI
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createTableColumn(table);
		tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new ActivityTableLabelProvider());
		tableViewer.setInput(new ActivitiyDAO()
				.findByProject(((ProjectEditorInput) getEditorInput())
						.getProject()));
		createTableEditor(table, tableViewer);
		tableViewer.setCellModifier(new ActivityCellModifier(tableViewer));
		// double click to edit cell
		TableViewerEditor
				.create(tableViewer,
						new DoubleClickColumnViewerEditorActivationStrategy(
								tableViewer), ColumnViewerEditor.DEFAULT);
		//
	}

	private void createTableColumn(Table table) {
		for (String cn : columnProperties) {
			TableColumn column = new TableColumn(table, SWT.LEFT);
			column.setText(Resource.get(cn));
			column.setWidth(100);
		}
	}

	private void createTableEditor(Table table, TableViewer tableViewer) {
		tableViewer.setColumnProperties(columnProperties);
		CellEditor[] cellEditors = new CellEditor[columnProperties.length];
		for (int i = 0; i < columnProperties.length; i++) {
			if (columnProperties[i].endsWith(Resource.A_SYBOL)) {
				cellEditors[i] = null;
				continue;
			}
			if (columnProperties[i].equals(Resource.A_P_END)) {
				cellEditors[i] = null;
				continue;
			}
			if (columnProperties[i].equals(Resource.A_A_END)) {
				cellEditors[i] = null;
				continue;
			}
			if (columnProperties[i].endsWith("start")
					|| columnProperties[i].endsWith("end")) {
				cellEditors[i] = new DatePickDialogCellEditor(table, getSite()
						.getShell(),!columnProperties[i].startsWith("Activity_p"));
				continue;
			}
			if (columnProperties[i].equals(Resource.A_PRE)) {
				cellEditors[i] = new PrePickDialogCellEditor(table, getSite()
						.getShell());
				continue;
			}
			cellEditors[i] = new TextCellEditor(table);
			// add verify listener
			if (columnProperties[i].endsWith("period")) {
				((Text) cellEditors[i].getControl())
						.addVerifyListener(new NumberVerifyListener());
			}
			if (columnProperties[i].endsWith("cost")) {
				((Text) cellEditors[i].getControl())
						.addVerifyListener(new DecimalVerifyListener());
			}
		}
		tableViewer.setCellEditors(cellEditors);
	}

//	public static TableViewer getTableViewer() {
//		return tableViewer;
//	}

}