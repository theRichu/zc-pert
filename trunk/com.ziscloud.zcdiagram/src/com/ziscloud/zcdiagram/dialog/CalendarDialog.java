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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.util.SWTHelper;

public class CalendarDialog extends Dialog {
	private DateTime dateTime;
	private String title;
	private String massage;
	private String date;
	private Calendar calendar = Calendar.getInstance();
	private boolean canBlank;
	private Text text;

	public CalendarDialog(Shell parentShell, Text text, String title, String massage,
			String initDate, boolean canBlank) {
		super(parentShell);
		this.text = text;
		this.title = title;
		this.massage = massage;
		this.date = initDate;
		this.canBlank = canBlank;
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
		dateTime = new DateTime(container, SWT.CALENDAR | SWT.BORDER);
		dateTime.setLayoutData(new GridData(GridData.FILL_BOTH));
		try {
			if (StringUtils.isBlank(date)) {
				calendar.setTime(new Date());
			} else {
				calendar.setTime(DateUtils.parseDate(date,
						SWTHelper.DATE_PATERNS));
			}
			dateTime.setYear(calendar.get(Calendar.YEAR));
			dateTime.setMonth(calendar.get(Calendar.MONTH));
			dateTime.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SWTHelper.createSeparator(container, 1);
		return container;
	}

	// @Override
	// protected Point getInitialSize() {
	// return new Point(310, 300);
	// }

	public String getDateAsString() {
		return this.date;
	}

	public Date getDate() {
		return calendar.getTime();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			date = "";
			text.setText(date);
			close();
		}
		if (IDialogConstants.CANCEL_ID == buttonId) {
			calendar.set(dateTime.getYear(), dateTime.getMonth(), dateTime
					.getDay());
			date = DateFormatUtils.format(calendar, SWTHelper.DATE_PATERN);
			text.setText(date);
			close();
		}
		if (IDialogConstants.CLOSE_ID == buttonId) {
			close();
		}
	}

	@Override
	protected void initializeBounds() {
		Composite comp = (Composite) getButtonBar();
		if (canBlank) {
			super.createButton(comp, IDialogConstants.OK_ID, "清空", false);
		}
		super.createButton(comp, IDialogConstants.CANCEL_ID, "完成", false);
		super.createButton(comp, IDialogConstants.CLOSE_ID, "取消", false);
		super.initializeBounds();
	}

}