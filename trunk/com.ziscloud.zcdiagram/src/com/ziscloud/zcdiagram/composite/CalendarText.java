package com.ziscloud.zcdiagram.composite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.dialog.CalendarDialog;

public class CalendarText extends Composite {
	private Text dateText;
	private Button pickButton;
	private DateFormat dateFormat;
	private String massage;

	public CalendarText(Composite parent, int style, String massage) {
		super(parent, style);
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.massage = massage;
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = false;
			thisLayout.numColumns = 2;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			this.setLayout(thisLayout);
			{
				dateText = new Text(this, SWT.BORDER | SWT.READ_ONLY);
				dateText.setEditable(false);
				GridData dateTextLData = new GridData(GridData.FILL_HORIZONTAL);
				dateTextLData.horizontalSpan = 7;
				dateText.setLayoutData(dateTextLData);
				dateText.setText(dateFormat.format(new Date()));
			}
			{
				pickButton = new Button(this, SWT.PUSH);
				GridData pickButtonLData = new GridData(GridData.FILL);
				pickButton.setLayoutData(pickButtonLData);
				pickButton.setText("   选择日期   ");
				pickButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						CalendarDialog dialog = new CalendarDialog(getShell(),
								"选择日期", massage, dateText.getText(),false);
						dialog.open();
						dateText.setText(dialog.getDateAsString());
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDate() {
		return dateText.getText();
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
}