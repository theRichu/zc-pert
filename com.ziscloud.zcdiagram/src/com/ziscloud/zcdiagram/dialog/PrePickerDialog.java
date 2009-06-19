package com.ziscloud.zcdiagram.dialog;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.ziscloud.zcdiagram.dao.ActivitiyDAO;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class PrePickerDialog extends Dialog {
	public static final String PRE_TOKEN = ",";
	public static final String ITEM_TOKEN = ":";
	private Project project;
	private Activity activity;
	private String preActs;
	private TableViewer available;
	private TableViewer selected;
	private Button select;
	private Button selectAll;
	private Button unselect;
	private Button unselectAll;

	public PrePickerDialog(Shell parentShell, Activity activity, String preActs) {
		super(parentShell);
		this.activity = activity;
		this.project = this.activity.getProject();
		this.preActs = preActs;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Shell shell = this.getShell();
		shell.setText("选择紧前工序");
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 11;
		layout.marginHeight = 20;
		container.setLayout(layout);
		createLabel(container, "选择紧前工序：", 3);
		SWTHelper.createSeparator(container, 3);
		createLabel(container, "可选紧前工序：", 1);
		SWTHelper.placeHolder(container);
		createLabel(container, "已选紧前工序：", 1);
		available = createList(container);
		SWTHelper.placeHolder(container);
		selected = createList(container);
		SWTHelper.placeHolder(container);
		select = createButton(container, ImageUtil.SELECTONE);
		selectAll = createButton(container, ImageUtil.SELECTALL);
		unselect = createButton(container, ImageUtil.UNSELECTONE);
		unselectAll = createButton(container, ImageUtil.UNSELECTALL);
		SWTHelper.placeHolder(container);
		SWTHelper.placeHolder(container);
		SWTHelper.createSeparator(container, 3);
		// initiate both the available list and the selected list
		initList();
		initButton();
		return container;
	}

	private Label createLabel(Composite container, String text, int hSapn) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hSapn;
		label.setLayoutData(gd);
		return label;
	}

	private void initList() {
		List<Activity> aList = null;
		if (null == activity.getPlanStartDate()) {
			aList = new ActivitiyDAO().findByProject(project);
		} else {
			aList = new ActivitiyDAO().findByProject(project, activity
					.getPlanStartDate());
		}
		List<String> preList = Arrays.asList(preActs.split(PRE_TOKEN));

		for (Activity activity : aList) {
			if (preList.contains(activity.getSymbol())) {
				selected.add(activity);
			} else {
				available.add(activity);
			}
		}
	}

	private void initButton() {
		select.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : available.getTable().getSelection()) {
					Activity ai = (Activity) item.getData();
					selected.add(ai);
					available.remove(ai);
				}
			}
		});
		selectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : available.getTable().getItems()) {
					Activity ai = (Activity) item.getData();
					selected.add(ai);
					available.remove(ai);
				}
			}
		});
		unselect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : selected.getTable().getSelection()) {
					Activity ai = (Activity) item.getData();
					available.add(ai);
					selected.remove(ai);
				}
			}
		});
		unselectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : selected.getTable().getItems()) {
					Activity ai = (Activity) item.getData();
					available.add(ai);
					selected.remove(ai);
				}
			}
		});
	}

	private TableViewer createList(Composite container) {
		TableViewer list = new TableViewer(container, SWT.BORDER | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		list.setContentProvider(new ArrayContentProvider());
		list.setLabelProvider(new PreListLabelProvider());
		list.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				Activity a1 = (Activity) e1;
				Activity a2 = (Activity) e2;
				return a1.getId() - a2.getId();
			}
		});
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 8;
		list.getTable().setLayoutData(gridData);
		return list;
	}

	private Button createButton(Composite container,
			ImageDescriptor imageDescriptor) {
		Button button = new Button(container, SWT.PUSH);
		button.setImage(imageDescriptor.createImage());
		button.setLayoutData(new GridData(GridData.CENTER));
		return button;
	}

	// @Override
	// protected Point getInitialSize() {
	// return new Point(600, 360);
	// }

	@Override
	protected void okPressed() {
		StringBuilder sb = new StringBuilder();
		for (TableItem item : selected.getTable().getItems()) {
			Activity activity = (Activity) item.getData();
			sb.append(activity.getSymbol()).append(PRE_TOKEN);
		}
		if (sb.length() > 1) {
			sb.delete(sb.length() - 1, sb.length());
		}
		this.preActs = sb.toString();
		this.close();
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	public String getPreActs() {
		return this.preActs;
	}

	private class PreListLabelProvider extends LabelProvider {
		@Override
		public Image getImage(Object element) {
			return ImageUtil.BLANK.createImage();
		}

		@Override
		public String getText(Object element) {
			Activity activity = (Activity) element;
			return activity.getSymbol() + ITEM_TOKEN + activity.getName();
		}
	}
}
