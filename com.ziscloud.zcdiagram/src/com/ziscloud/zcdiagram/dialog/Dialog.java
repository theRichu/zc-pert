package com.ziscloud.zcdiagram.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.ziscloud.zcdiagram.util.SWTHelper;

public class Dialog extends org.eclipse.jface.dialogs.Dialog {
	protected String title;
	protected String message;

	protected Dialog(Shell parentShell, String title, String message) {
		super(parentShell);
		this.title = title;
		this.message = message;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Shell shell = this.getShell();
		shell.setText(title);
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(1, true);
		layout.marginWidth = 0;
		layout.marginHeight = 20;
		container.setLayout(layout);
		Label label = new Label(container, SWT.NONE);
		label.setText(message);
		GridData gd = new GridData(GridData.FILL);
		gd.horizontalIndent = 5;
		label.setLayoutData(gd);
		SWTHelper.createSeparator(container, 1);
		createContent(container)
				.setLayoutData(new GridData(GridData.FILL_BOTH));
		SWTHelper.createSeparator(container, 1);
		return container;
	}

	protected Composite createContent(Composite parent) {
		return null;
	}

}
