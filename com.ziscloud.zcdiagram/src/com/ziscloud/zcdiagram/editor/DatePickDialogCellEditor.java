package com.ziscloud.zcdiagram.editor;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.ziscloud.zcdiagram.dialog.CalendarDialog;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class DatePickDialogCellEditor extends DialogCellEditor {
	private Shell shell;
	private boolean canBlank;

	public DatePickDialogCellEditor(Table table, Shell shell, boolean canBlank) {
		super(table);
		this.shell = shell;
		this.canBlank = canBlank;
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		String defaultValue = getValue().toString();
		if (defaultValue.equals(SWTHelper.UNKNOWN)) {
			defaultValue = DateFormatUtils.format(new Date(),
					SWTHelper.DATE_PATERN);
		}
		CalendarDialog dialog = new CalendarDialog(shell, "选择日期", "为工序选择时间：",
				defaultValue, canBlank);
		dialog.open();
		return dialog.getDateAsString();
	}
}
