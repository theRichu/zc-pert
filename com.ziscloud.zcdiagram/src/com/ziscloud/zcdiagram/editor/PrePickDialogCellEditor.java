package com.ziscloud.zcdiagram.editor;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.ziscloud.zcdiagram.dialog.PrePickerDialog;
import com.ziscloud.zcdiagram.pojo.Activity;

public class PrePickDialogCellEditor extends DialogCellEditor {
	private Shell shell;
	private Table table;

	public PrePickDialogCellEditor(Table table, Shell shell) {
		super(table);
		this.table = table;
		this.shell = shell;
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		String preActs = getValue().toString();
		TableItem[] items = table.getSelection();
		Activity activity = (Activity) items[0].getData();
		PrePickerDialog dialog = new PrePickerDialog(shell, activity, preActs);
		dialog.open();
		return dialog.getPreActs();
	}

}
