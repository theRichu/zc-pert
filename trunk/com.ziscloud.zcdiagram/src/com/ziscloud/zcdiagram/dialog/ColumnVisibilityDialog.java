package com.ziscloud.zcdiagram.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;

import com.ziscloud.zcdiagram.util.Resource;

public class ColumnVisibilityDialog extends Dialog {

	private String[] columnProperties;
	private Button[] buttons;
	private Button selectAllBtn;
	private TableColumn[] tableColumns;
	private boolean[] visibilities;

	public ColumnVisibilityDialog(Shell parentShell, String[] columnProperties,
			TableColumn[] tableColumns) {
		super(parentShell, "选择显示的列", "选择显示的列:");
		this.columnProperties = columnProperties;
		this.tableColumns = tableColumns;
		this.visibilities = new boolean[tableColumns.length];
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite container = (Composite) new Composite(parent, SWT.NONE);
		GridLayout columnSectionLayout = new GridLayout();
		columnSectionLayout.numColumns = 4;
		columnSectionLayout.verticalSpacing = 10;
		container.setLayout(columnSectionLayout);
		buttons = new Button[columnProperties.length];
		for (int i = 0; i < columnProperties.length; i++) {
			String column = columnProperties[i];
			Button button = new Button(container, SWT.CHECK);
			button.setText(Resource.get(column));
			button.setSelection(true);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Button b = (Button) e.getSource();
					String text = b.getText();
					outer: for (int j = 0; j < tableColumns.length; j++) {
						TableColumn tc = tableColumns[j];
						if (text.equals(tc.getText())) {
							visibilities[j] = b.getSelection();
							selectAllBtn.setSelection(b.getSelection());
							if (b.getSelection()) {
								for (Button btn : buttons) {
									if (!btn.getSelection()) {
										selectAllBtn.setSelection(false);
										continue outer;
									}
								}
							}
							break;
						}
					}
				}

			});
			buttons[i] = button;
		}
		selectAllBtn = new Button(container, SWT.CHECK);
		selectAllBtn.setText("全部显示");
		selectAllBtn.setSelection(true);
		selectAllBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < tableColumns.length; i++) {
					visibilities[i] = selectAllBtn.getSelection();
				}
				for (Button btn : buttons) {
					btn.setSelection(selectAllBtn.getSelection());
				}
			}
		});
		for (int i = 0; i < tableColumns.length; i++) {
			buttons[i].setSelection(tableColumns[i].getResizable());
			visibilities[i] = tableColumns[i].getResizable();
			if (!tableColumns[i].getResizable()) {
				selectAllBtn.setSelection(tableColumns[i].getResizable());
			}
		}
		return container;
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		for (int i = 0; i < tableColumns.length; i++) {
			boolean v = visibilities[i];
			tableColumns[i].setWidth(v ? 100 : 0);
			tableColumns[i].setResizable(v);
		}
		super.okPressed();
	}

}
