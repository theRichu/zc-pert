package com.ziscloud.zcdiagram.editor;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.ziscloud.zcdiagram.dao.ActivitiyDAO;
import com.ziscloud.zcdiagram.dialog.CalendarDialog;
import com.ziscloud.zcdiagram.handler.UpdateProjectAction;
import com.ziscloud.zcdiagram.pojo.Project;
import com.ziscloud.zcdiagram.strategy.DateToTextUpdateStrategy;
import com.ziscloud.zcdiagram.strategy.NumberToTextUpdateStrategy;
import com.ziscloud.zcdiagram.strategy.TextToDateUpdateStrategy;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;
import com.ziscloud.zcdiagram.validator.DecimalVerifyListener;
import com.ziscloud.zcdiagram.validator.NumberVerifyListener;
import com.ziscloud.zcdiagram.validator.StrategyFactory;

public class ProjectOverviewPage extends FormPage {
	private static final String ID = "com.ziscloud.zcdiagram.formpage.projectoverviewpage";
	private static final String TITLE = "基本信息";
	private Project project;
	private Text remarks;
	private Text designer;
	private Text manager;
	private Text builder;
	private Text planCost;
	private Text planEndDate;
	private Text planStartDate;
	private Text planPeriod;
	private Text name;
	private Action saveAction;

	public ProjectOverviewPage(FormEditor editor) {
		super(editor, ID, TITLE); //$NON-NLS-1$
		this.project = ((ProjectEditorInput) editor.getEditorInput())
				.getProject();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm scrolledForm = managedForm.getForm();
		scrolledForm.setText("工程项目基本信息");
		final Form form = scrolledForm.getForm();
		toolkit.decorateFormHeading(form);
		// create the tool bar and its item
		IToolBarManager toolBarManager = form.getToolBarManager();
		saveAction = new UpdateProjectAction("Save", ImageUtil.SAVE,
				(ProjectEditor) getEditor(), project);
		toolBarManager.add(saveAction);
		toolBarManager.update(true);
		// create content for this page
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		layout.topMargin = 10;
		layout.horizontalSpacing = 10;
		scrolledForm.getBody().setLayout(layout);
		createInfoSection(scrolledForm, toolkit);
		bindValues(scrolledForm);
		createStaSection(scrolledForm, toolkit);
	}

	/**
	 * This method initializes infoSection
	 * 
	 */
	private void createInfoSection(final ScrolledForm form, FormToolkit toolkit) {
		Section infoSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR);
		infoSection.setText("工程项目基础数据");
		infoSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		createInfoSectionComposite(infoSection, toolkit);
		infoSection.setExpanded(true);
	}

	/**
	 * This method initializes staSection
	 * 
	 */
	private void createStaSection(final ScrolledForm form, FormToolkit toolkit) {
		Section staSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR);
		staSection.setText("工程项目数据统计");
		staSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		createStaSectionComposite(staSection, toolkit);
		staSection.setExpanded(true);
	}

	/**
	 * This method initializes infoSectionComposite
	 * 
	 */
	private void createInfoSectionComposite(Section section, FormToolkit toolkit) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.verticalSpacing = 10;
		gridLayout.marginBottom = 10;
		Composite infoSectionComposite = toolkit.createComposite(section);
		infoSectionComposite.setLayout(gridLayout);
		GridData gd = new GridData();
		gd.horizontalSpan = 3;
		toolkit.createLabel(infoSectionComposite,
				"工程项目的基础信息，可直接在此修改并保存，不包含工程项目的工序信息。").setLayoutData(gd);
		toolkit
				.createLabel(infoSectionComposite, Resource
						.get(Resource.P_NAME)); //$NON-NLS-1$
		name = toolkit.createText(infoSectionComposite, project.getName(),
				SWT.SINGLE | SWT.BORDER);
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(infoSectionComposite, SWT.NONE);

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_PLANPERIOD)); //$NON-NLS-1$
		planPeriod = toolkit.createText(infoSectionComposite, null, SWT.SINGLE
				| SWT.BORDER);
		planPeriod.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		planPeriod.addVerifyListener(new NumberVerifyListener());
		planPeriod.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				try {
					updatePlanEndDate();
				} catch (ParseException pe) {
					throw new RuntimeException("upate plan end date failed.", //$NON-NLS-1$
							pe);
				}
			}
		});
		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_PLANPERIOD_UINT)); //$NON-NLS-1$

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_PLANSTARTDATE)); //$NON-NLS-1$
		planStartDate = toolkit.createText(infoSectionComposite, null,
				SWT.SINGLE | SWT.BORDER);
		planStartDate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button pickButton = toolkit.createButton(infoSectionComposite,
				"   选择日期   ", SWT.PUSH); //$NON-NLS-1$
		GridData pickButtonLData = new GridData(GridData.FILL);
		pickButton.setLayoutData(pickButtonLData);
		pickButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CalendarDialog dialog = new CalendarDialog(
						getSite().getShell(), planStartDate,
						"选择日期", "工程项目计划开工时间：", //$NON-NLS-1$ //$NON-NLS-2$
						planStartDate.getText(), false);
				dialog.open();
				planStartDate.setText(dialog.getDateAsString());
				try {
					updatePlanEndDate();
				} catch (ParseException pe) {
					throw new RuntimeException("upate plan end date failed.", //$NON-NLS-1$
							pe);
				}
			}
		});

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_PLANENDDATE)); //$NON-NLS-1$
		planEndDate = toolkit.createText(infoSectionComposite, null, SWT.SINGLE
				| SWT.BORDER | SWT.READ_ONLY);
		planEndDate.setEditable(false);
		planEndDate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(infoSectionComposite, SWT.NONE);

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_PLANCOST)); //$NON-NLS-1$
		planCost = toolkit.createText(infoSectionComposite, null, SWT.SINGLE
				| SWT.BORDER);
		planCost.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		planCost.addVerifyListener(new DecimalVerifyListener());
		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_PLANCOST_UNIT)); //$NON-NLS-1$

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_BUILDER)); //$NON-NLS-1$
		builder = toolkit.createText(infoSectionComposite, null, SWT.SINGLE
				| SWT.BORDER);
		builder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(infoSectionComposite, SWT.NONE);

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_MANAGER)); //$NON-NLS-1$
		manager = toolkit.createText(infoSectionComposite, null, SWT.SINGLE
				| SWT.BORDER);
		manager.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(infoSectionComposite, SWT.NONE);

		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		gd2.horizontalSpan = 3;
		toolkit.createSeparator(infoSectionComposite,
				SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(gd2);
		GridData gd3 = new GridData();
		gd3.horizontalSpan = 3;
		toolkit.createLabel(infoSectionComposite, "以下项目为可选内容：").setLayoutData( //$NON-NLS-1$
				gd3);

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_DESIGNER)); //$NON-NLS-1$
		designer = toolkit.createText(infoSectionComposite, null, SWT.SINGLE
				| SWT.BORDER);
		designer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(infoSectionComposite, SWT.NONE);

		toolkit.createLabel(infoSectionComposite, Resource
				.get(Resource.P_L_REMARKS)); //$NON-NLS-1$
		remarks = toolkit.createText(infoSectionComposite, null, SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		GridData remarksLDate = new GridData(GridData.FILL_BOTH);
		remarksLDate.heightHint = 200;
		remarks.setLayoutData(remarksLDate);
		section.setClient(infoSectionComposite);
	}

	/**
	 * This method initializes staSectionComposite
	 * 
	 */
	private void createStaSectionComposite(Section section, FormToolkit toolkit) {
		Composite staSectionComposite = toolkit.createComposite(section);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		staSectionComposite.setLayout(gridLayout);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		toolkit.createLabel(staSectionComposite, "关于该工程的工程进度、资金等的统计信息。") //$NON-NLS-1$
				.setLayoutData(gd);
		ActivitiyDAO activitiyDAO = new ActivitiyDAO();
		Integer total = activitiyDAO.countActivity(project);
		Integer started = activitiyDAO.countStarted(project);
		Integer ended = activitiyDAO.countEnded(project);
		Integer notEnded = activitiyDAO.countNotEnded(project);
		Integer notStarted = total - started;
		Double totalCost = project.getPlanCost();
		Double usedCost = activitiyDAO.countUsedCost(project);
		Double remainCost = totalCost - usedCost;
		Integer totalPeriod = project.getPlanPeriod();
		Integer usedPeriod = activitiyDAO.countUsedPeriod(project);
		Integer remainPeriod = totalPeriod - usedPeriod;
		//
		toolkit.createLabel(staSectionComposite, "工程工序总数：");
		Label label16 = toolkit.createLabel(staSectionComposite, total
				.toString());
		label16.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "已开工工序数：");
		Label label17 = toolkit.createLabel(staSectionComposite, started
				.toString());
		label17.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "已完工工序数：");
		Label label18 = toolkit.createLabel(staSectionComposite, ended
				.toString());
		label18.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "正在进行的工序数：");
		Label label19 = toolkit.createLabel(staSectionComposite, notEnded
				.toString());
		label19.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "未开工工序数：");
		Label label20 = toolkit.createLabel(staSectionComposite, notStarted
				.toString());
		label20.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "预算资金总额：");
		Label label22 = toolkit.createLabel(staSectionComposite, totalCost
				.toString());
		label22.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "已用资金：");
		Label label24 = toolkit.createLabel(staSectionComposite, usedCost
				.toString());
		label24.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "可用资金：");
		Label label26 = toolkit.createLabel(staSectionComposite, remainCost
				.toString());
		label26.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "总工期：");
		Label label28 = toolkit.createLabel(staSectionComposite, totalPeriod
				.toString());
		label28.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "已完成工期：");
		Label label30 = toolkit.createLabel(staSectionComposite, usedPeriod
				.toString());
		label30.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		toolkit.createLabel(staSectionComposite, "剩余工期：");
		Label label32 = toolkit.createLabel(staSectionComposite, remainPeriod
				.toString());
		label32.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setClient(staSectionComposite);
	}

	private void bindValues(ScrolledForm form) {
		DataBindingContext context = new DataBindingContext();
		// bind values
		context.bindValue(SWTObservables.observeText(name, SWT.Modify),
				PojoObservables.observeValue(project, "name"), StrategyFactory
						.strLength(true, Resource.get(Resource.P_NAME),
								SWTHelper.FIELD_MAXLEN), null); //$NON-NLS-1$
		context.bindValue(SWTObservables.observeText(remarks, SWT.Modify),
				PojoObservables.observeValue(project, "remarks"),
				StrategyFactory.strLength(false, Resource
						.get(Resource.P_REMARKS), SWTHelper.TEXT_MAXLEN), null);
		context
				.bindValue(SWTObservables.observeText(designer, SWT.Modify),
						PojoObservables.observeValue(project, "designer"),
						StrategyFactory.strLength(false, Resource
								.get(Resource.P_DESIGNER),
								SWTHelper.TEXT_MAXLEN), null);
		context
				.bindValue(SWTObservables.observeText(manager, SWT.Modify),
						PojoObservables.observeValue(project, "manager"),
						StrategyFactory.strLength(true, Resource
								.get(Resource.P_MANAGER),
								SWTHelper.FIELD_MAXLEN), null); //$NON-NLS-1$
		context
				.bindValue(SWTObservables.observeText(builder, SWT.Modify),
						PojoObservables.observeValue(project, "builder"),
						StrategyFactory.strLength(true, Resource
								.get(Resource.P_BUILDER),
								SWTHelper.FIELD_MAXLEN), null); //$NON-NLS-1$
		context.bindValue(SWTObservables.observeText(planCost, SWT.Modify),
				PojoObservables.observeValue(project, "planCost"),
				StrategyFactory.DoubleRange(true, Resource
						.get(Resource.P_PLANCOST), Double.MAX_VALUE),
				new NumberToTextUpdateStrategy()); //$NON-NLS-1$
		context.bindValue(SWTObservables.observeText(planEndDate, SWT.Modify),
				PojoObservables.observeValue(project, "planEndDate"),
				new TextToDateUpdateStrategy(), new DateToTextUpdateStrategy());
		context.bindValue(
				SWTObservables.observeText(planStartDate, SWT.Modify),
				PojoObservables.observeValue(project, "planStartDate"),
				new TextToDateUpdateStrategy(), new DateToTextUpdateStrategy());
		context.bindValue(SWTObservables.observeText(planPeriod, SWT.Modify),
				PojoObservables.observeValue(project, "planPeriod"),
				StrategyFactory.IntRange(true, Resource
						.get(Resource.P_PLANPERIOD), Integer.MAX_VALUE),
				new NumberToTextUpdateStrategy()); //$NON-NLS-1$
		// check and show the validation messages
		updateFormMessage(context, form);
	}

	private void updatePlanEndDate() throws ParseException {
		Date sDate = DateUtils.parseDate(planStartDate.getText(),
				SWTHelper.DATE_PATERNS);
		String pStr = planPeriod.getText();
		int p = 0;
		if (!StringUtils.isBlank(pStr)) {
			p = Integer.parseInt(pStr);
		}
		planEndDate.setText(DateFormatUtils.format(DateUtils.addDays(sDate, p),
				SWTHelper.DATE_PATERN));
	}

	private void updateFormMessage(DataBindingContext context,
			final ScrolledForm form) {
		new AggregateValidationStatus(context.getBindings(),
				AggregateValidationStatus.MAX_SEVERITY)
				.addChangeListener(new IChangeListener() {
					@Override
					public void handleChange(ChangeEvent event) {
						saveAction.setEnabled(false);
						IStatus status = (IStatus) ((IObservableValue) event
								.getSource()).getValue();
						int severity = status.getSeverity();
						if (severity == IStatus.ERROR) {
							form.setMessage(status.getMessage(),
									IMessageProvider.ERROR);
						} else if (severity == IStatus.INFO) {
							form.setMessage(status.getMessage(),
									IMessageProvider.INFORMATION);
						} else if (severity == IStatus.WARNING) {
							form.setMessage(status.getMessage(),
									IMessageProvider.WARNING);
						} else {
							form.setMessage(null, IMessageProvider.NONE);
							saveAction.setEnabled(true);
						}
					}
				});
	}
}