package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
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
import com.ziscloud.zcdiagram.dao.ProjectDAO;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.provider.ActivityTableLabelProvider;
import com.ziscloud.zcdiagram.strategy.DoubleClickColumnViewerEditorActivationStrategy;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.validator.DecimalVerifyListener;
import com.ziscloud.zcdiagram.validator.NumberVerifyListener;

public class TableFormPage extends FormPage {
	protected Project project;
	protected Table table;
	protected String formTitle;
	protected TableViewer tableViewer;
	protected String[] columns;
	protected Shell shell;
	protected TableColumns tableColumns;
	protected ActivityCellModifier cellModifier;

	public TableFormPage(FormEditor projectEditor, String id, String pageTitle,
			String formTitle, TableColumns tableColumns) {
		super(projectEditor, id, pageTitle);
		this.formTitle = formTitle;
		if (null != tableColumns) {
			this.tableColumns = tableColumns;
			this.columns = tableColumns.getColumns();
		} else {
			this.tableColumns = new TableColumns();
			this.columns = new String[] {};
		}
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		this.shell = getSite().getWorkbenchWindow().getWorkbench()
				.getActiveWorkbenchWindow().getShell();
		int projectId = ((ProjectEditorInput) getEditorInput()).getProject()
				.getId();
		this.project = new ProjectDAO().findById(projectId);
		//
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm scrolledForm = managedForm.getForm();
		scrolledForm.setText(formTitle);
		final Form form = scrolledForm.getForm();
		toolkit.decorateFormHeading(form);
		// create content for this page
		scrolledForm.getBody().setLayout(new FillLayout());
		table = toolkit.createTable(scrolledForm.getBody(), SWT.FULL_SELECTION
				| SWT.V_SCROLL | SWT.H_SCROLL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createTableColumn(table);
		tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new ActivityTableLabelProvider(columns));
		tableViewer.setInput(new ActivitiyDAO().findByProject(this.project));
		//
		createTableEditor(table, tableViewer);
		cellModifier = new ActivityCellModifier(tableViewer);
		tableViewer.setCellModifier(cellModifier);
		// double click to edit cell
		TableViewerEditor
				.create(tableViewer,
						new DoubleClickColumnViewerEditorActivationStrategy(
								tableViewer), ColumnViewerEditor.DEFAULT);
	}

	private void createTableColumn(Table table) {
		for (String cn : columns) {
			TableColumn column = new TableColumn(table, SWT.LEFT);
			column.setText(Resource.get(cn));
			column.setWidth(100);
		}
	}

	private void createTableEditor(Table table, TableViewer tableViewer) {
		tableViewer.setColumnProperties(columns);
		CellEditor[] cellEditors = new CellEditor[columns.length];
		for (int i = 0; i < columns.length; i++) {
			if (!tableColumns.isEditable(columns[i])) {
				cellEditors[i] = null;
				continue;
			}
			if (columns[i].endsWith(Resource.A_SYBOL)) {
				cellEditors[i] = null;
				continue;
			}
			if (columns[i].equals(Resource.A_P_END)) {
				cellEditors[i] = null;
				continue;
			}
			if (columns[i].equals(Resource.A_A_END)) {
				cellEditors[i] = null;
				continue;
			}
			if (columns[i].endsWith("start") || columns[i].endsWith("end")) {
				cellEditors[i] = new DatePickDialogCellEditor(table, getSite()
						.getShell(), !columns[i].startsWith("Activity_p"));
				continue;
			}
			if (columns[i].equals(Resource.A_PRE)) {
				cellEditors[i] = new PrePickDialogCellEditor(table, getSite()
						.getShell());
				continue;
			}
			cellEditors[i] = new TextCellEditor(table);
			// add verify listener
			if (columns[i].endsWith("period")) {
				((Text) cellEditors[i].getControl())
						.addVerifyListener(new NumberVerifyListener());
			}
			if (columns[i].endsWith("cost")) {
				((Text) cellEditors[i].getControl())
						.addVerifyListener(new DecimalVerifyListener());
			}
		}
		tableViewer.setCellEditors(cellEditors);
	}

	public ActivityCellModifier getCellModifier() {
		return cellModifier;
	}

	public void setCellModifier(ActivityCellModifier cellModifier) {
		this.cellModifier = cellModifier;
	}

}
