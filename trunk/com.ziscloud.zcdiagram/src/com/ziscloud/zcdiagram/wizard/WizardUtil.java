package com.ziscloud.zcdiagram.wizard;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.dialog.CalendarDialog;
import com.ziscloud.zcdiagram.dialog.PrePickerDialog;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class WizardUtil {
	public static Label createLabel(Composite container, String key) {
		Label label = new Label(container, SWT.NONE);
		label.setText(Resource.get(key));
		return label;
	}

	public static Text createText(Composite container, String key) {
		return createTextWithUnit(container, key, null, "");
	}

	public static Text createText(Composite container, String key,
			String defaultValue) {
		return createTextWithUnit(container, key, null, defaultValue);
	}

	public static Text createTextWithUnit(Composite container, String label,
			String uintKey, String defaultValue) {
		createLabel(container, label);
		Text text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setText(defaultValue);
		if (null == uintKey) {
			SWTHelper.placeHolder(container);
		} else {
			createLabel(container, uintKey);
		}
		return text;
	}

	public static Text createTextarea(Composite container, String key) {
		createLabel(container, key);
		GridData remarksLData = new GridData(GridData.FILL_BOTH);
		remarksLData.verticalSpan = 18;
		Text text = new Text(container, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		text.setLayoutData(remarksLData);
		return text;
	}

	public static Text createDate(Composite container, final Shell shell,
			final String key, final boolean isDefault, final boolean canBlank) {
		createLabel(container, key);
		final Text text = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		text.setEditable(false);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (isDefault) {
			text.setText(DateFormatUtils.format(new Date(),
					SWTHelper.DATE_PATERN));
		}

		Button pickButton = new Button(container, SWT.PUSH);
		GridData pickButtonLData = new GridData(GridData.FILL);
		pickButton.setLayoutData(pickButtonLData);
		pickButton.setText("   选择日期   ");
		pickButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CalendarDialog dialog = null;
				if (isDefault) {
					dialog = new CalendarDialog(shell, "选择日期", "工序"
							+ Resource.get(key), text.getText(), canBlank);
				} else {
					dialog = new CalendarDialog(shell, "选择日期", "工序"
							+ Resource.get(key), DateFormatUtils.format(
							new Date(), SWTHelper.DATE_PATERN), canBlank);
				}
				dialog.open();
				text.setText(dialog.getDateAsString());
			}
		});
		return text;
	}

	public static Text createDate(Composite container, Shell shell, String key,
			boolean canBlank) {
		return createDate(container, shell, key, true, canBlank);
	}

	public static Text createPreText(Composite container, final Shell shell,
			final Activity activity) {
		createLabel(container, Resource.L_A_PRE);
		final Text text = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		text.setEditable(false);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button pickButton = new Button(container, SWT.PUSH);
		GridData pickButtonLData = new GridData(GridData.FILL);
		pickButton.setLayoutData(pickButtonLData);
		pickButton.setText("   选择紧前   ");
		pickButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PrePickerDialog dialog = new PrePickerDialog(shell, activity,
						text.getText());
				dialog.open();
				text.setText(dialog.getPreActs());
			}
		});

		return text;
	}

	public static Label createTip(Composite container, String tip) {
		Label tipLabel = new Label(container, SWT.NONE);
		GridData tipLabelLData = new GridData();
		tipLabelLData.heightHint = 20;
		tipLabelLData.horizontalSpan = 3;
		tipLabel.setLayoutData(tipLabelLData);
		tipLabel.setText(tip);
		return tipLabel;
	}

	public static void updateWizardMessage(final WizardPage wizardPage,
			DataBindingContext context) {
		new AggregateValidationStatus(context.getBindings(),
				AggregateValidationStatus.MAX_SEVERITY)
				.addChangeListener(new IChangeListener() {
					@Override
					public void handleChange(ChangeEvent event) {
						wizardPage.setPageComplete(false);
						IStatus status = (IStatus) ((IObservableValue) event
								.getSource()).getValue();
						int severity = status.getSeverity();
						if (severity == IStatus.ERROR) {
							wizardPage.setMessage(status.getMessage(),
									IMessageProvider.ERROR);
						} else if (severity == IStatus.INFO) {
							wizardPage.setMessage(status.getMessage(),
									IMessageProvider.INFORMATION);
						} else if (severity == IStatus.WARNING) {
							wizardPage.setMessage(status.getMessage(),
									IMessageProvider.WARNING);
						} else {
							wizardPage.setMessage(null, IMessageProvider.NONE);
							wizardPage.setPageComplete(true);
						}
					}
				});
	}
}
