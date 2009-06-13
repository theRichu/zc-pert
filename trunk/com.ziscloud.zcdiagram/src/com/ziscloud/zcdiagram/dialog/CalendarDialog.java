package com.ziscloud.zcdiagram.dialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.ziscloud.zcdiagram.util.SWTHelper;

public class CalendarDialog extends Dialog {
	private DateTime calendar;
	private String title;
	private String massage;
	private String date;
	private Calendar c = Calendar.getInstance();
	private boolean canBlank;

	public CalendarDialog(Shell parentShell, String title, String massage,
			String initDate, boolean canBlank) {
		super(parentShell);
		this.title = title;
		this.massage = massage;
		this.date = initDate;
		this.canBlank = canBlank;
	}

	@Override
	protected void cancelPressed() {
		c.set(calendar.getYear(), calendar.getMonth(), calendar.getDay());
		this.date = DateFormatUtils.format(c, SWTHelper.DATE_PATERN);
		this.close();
	}

	@Override
	protected void okPressed() {
		System.out.println("ok");
		this.date = "";
		this.close();
	}

	protected void cancel() {
		super.cancelPressed();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Shell shell = this.getShell();
		shell.setText(title);
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = 11;
		layout.marginHeight = 20;
		container.setLayout(layout);
		Label label = new Label(container, SWT.NONE);
		label.setText(massage);
		SWTHelper.createSeparator(container, 1);
		calendar = new DateTime(container, SWT.CALENDAR | SWT.BORDER);
		calendar.setLayoutData(new GridData(GridData.FILL_BOTH));
		try {
			Calendar c = Calendar.getInstance();
			if (StringUtils.isBlank(date)) {
				c.setTime(new Date());
			} else {
				c.setTime(DateUtils.parseDate(date, SWTHelper.DATE_PATERNS));
			}
			calendar.setYear(c.get(Calendar.YEAR));
			calendar.setMonth(c.get(Calendar.MONTH));
			calendar.setDay(c.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SWTHelper.createSeparator(container, 1);
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(310, 300);
	}

	public String getDateAsString() {
		return this.date;
	}

	public Date getDate() {
		return c.getTime();
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control btnBar = super.createButtonBar(parent);
		if (canBlank) {
			getButton(IDialogConstants.OK_ID).setText("清空");
		}
		getButton(IDialogConstants.OK_ID).setVisible(canBlank);
		getButton(IDialogConstants.CANCEL_ID).setText("完成");
		return btnBar;

	}

	@Override
	protected void initializeBounds() {
		Composite comp = (Composite) getButtonBar();
		super.createButton(comp, IDialogConstants.CLOSE_ID, "取消", false)
				.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						super.widgetSelected(e);
						cancel();
					}

				});
		super.initializeBounds();
	}

}