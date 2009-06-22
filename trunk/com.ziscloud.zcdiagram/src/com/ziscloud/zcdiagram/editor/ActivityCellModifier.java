package com.ziscloud.zcdiagram.editor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.ziscloud.zcdiagram.core.IModelChangeProvider;
import com.ziscloud.zcdiagram.core.IModelChangedEvent;
import com.ziscloud.zcdiagram.core.IModelChangedListener;
import com.ziscloud.zcdiagram.core.ModelChangedEvent;
import com.ziscloud.zcdiagram.dao.ActivitiyDAO;
import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class ActivityCellModifier implements ICellModifier,
		IModelChangeProvider {
	private TableViewer tableViewer;
	private List<IModelChangedListener> listeners = new ArrayList<IModelChangedListener>();

	public ActivityCellModifier(TableViewer tableViewer) {
		super();
		this.tableViewer = tableViewer;
	}

	@Override
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * get value for every column before use edit the cell
	 */
	@Override
	public Object getValue(Object element, String property) {
		if (element instanceof Activity) {
			Activity activity = new ActivitiyDAO()
					.findById(((Activity) element).getId());
			if (property.equals(Resource.A_NAME))
				return SWTHelper.nullToString(activity.getName());
			if (property.equals(Resource.A_PRE))
				return SWTHelper.nullToString(activity.getPreActivity());
			if (property.equals(Resource.A_P_PERIOD))
				return SWTHelper.nullToString(activity.getPlanPeriod());
			if (property.equals(Resource.A_P_COST))
				return SWTHelper.nullToString(activity.getPlanCost());
			if (property.equals(Resource.A_OUTPUT))
				return SWTHelper.nullToString(activity.getOutput());
			if (property.equals(Resource.A_P_START))
				return SWTHelper.dateColumnText(activity.getPlanStartDate());
			if (property.equals(Resource.A_M_START))
				return SWTHelper.dateColumnText(activity.getMustStartDate());
			if (property.equals(Resource.A_M_END))
				return SWTHelper.dateColumnText(activity.getMustEndDate());
			if (property.equals(Resource.A_L_START))
				return SWTHelper.dateColumnText(activity.getLaterStartDate());
			if (property.equals(Resource.A_L_END))
				return SWTHelper.dateColumnText(activity.getLaterEndDate());
			if (property.equals(Resource.A_E_START))
				return SWTHelper.dateColumnText(activity.getEarlyStartDate());
			if (property.equals(Resource.A_E_END))
				return SWTHelper.dateColumnText(activity.getEarlyEndDate());
			if (property.equals(Resource.A_A_START))
				return SWTHelper.dateColumnText(activity.getActualStartDate());
			if (property.equals(Resource.A_A_PERIOD))
				return SWTHelper.nullToString(activity.getActualPeriod());
			if (property.equals(Resource.A_A_COST))
				return SWTHelper.nullToString(activity.getActualCost());
			if (property.equals(Resource.A_BUILDER))
				return SWTHelper.nullToString(activity.getBuilder());
			if (property.equals(Resource.A_R_DAYS))
				return SWTHelper.nullToString(activity.getRarDays());
			if (property.equals(Resource.A_R_COST))
				return SWTHelper.nullToString(activity.getRarCost());
			if (property.equals(Resource.A_RMARKS))
				return SWTHelper.nullToString(activity.getRemarks());
			return null;
		} else {
			return null;
		}
	}

	/**
	 * set the value to the object displayed on the row
	 */
	@Override
	public void modify(Object element, String property, Object value) {
		Activity activity = (Activity) ((TableItem) element).getData();
		String valueStr = value.toString();
		try {
			if (property.equals(Resource.A_NAME))
				activity.setName(valueStr);
			if (property.equals(Resource.A_PRE))
				activity.setPreActivity(valueStr);
			if (property.equals(Resource.A_P_PERIOD)) {
				try {
					activity.setPlanPeriod(Integer.parseInt(valueStr));
				} catch (Exception e) {
					activity.setPlanPeriod(activity.getPlanPeriod());
				}
				activity.setPlanEndDate(DateUtils.addDays(activity
						.getPlanStartDate(), activity.getPlanPeriod()));
			}
			if (property.equals(Resource.A_P_COST))
				try {
					activity.setPlanCost(Double.parseDouble(valueStr));
				} catch (Exception e) {
					activity.setPlanCost(activity.getPlanCost());
				}
			if (property.equals(Resource.A_OUTPUT))
				try {
					activity.setOutput(Double.parseDouble(valueStr));
				} catch (Exception e) {
					activity.setOutput(activity.getOutput());
				}
			if (property.equals(Resource.A_P_START)) {
				activity.setPlanStartDate(DateUtils.parseDate(valueStr,
						SWTHelper.DATE_PATERNS));
				activity.setPlanEndDate(DateUtils.addDays(activity
						.getPlanStartDate(), activity.getPlanPeriod()));
			}

			if (property.equals(Resource.A_M_START)) {
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setMustStartDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
				} else {
					activity.setMustStartDate(null);
				}

			}
			if (property.equals(Resource.A_M_END)) {
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setMustEndDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
				} else {
					activity.setMustEndDate(null);
				}
			}
			if (property.equals(Resource.A_L_START)) {
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setLaterStartDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
				} else {
					activity.setLaterStartDate(null);
				}
			}
			if (property.equals(Resource.A_L_END)) {
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setLaterEndDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
				} else {
					activity.setLaterEndDate(null);
				}
			}
			if (property.equals(Resource.A_E_START)) {
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setEarlyStartDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
				} else {
					activity.setEarlyStartDate(null);
				}
			}
			if (property.equals(Resource.A_E_END)) {
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setEarlyEndDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
				} else {
					activity.setEarlyEndDate(null);
				}
			}
			if (property.equals(Resource.A_A_START)) {
				if (!StringUtils.isBlank(valueStr)) {
					activity.setActualStartDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
					// if the period is not null then calculate the end date,
					// else set the end date to be null
					if (null != activity.getActualPeriod()
							&& 0 != activity.getActualPeriod()) {
						activity.setActualEndDate(DateUtils.addDays(activity
								.getActualStartDate(), activity
								.getActualPeriod()));
					} else {
						activity.setActualEndDate(null);
					}
				} else {
					// if the actual start date is blank then set the end date
					// to be null and set the period to be zero
					activity.setActualStartDate(null);
					activity.setActualEndDate(null);
					activity.setActualPeriod(null);
				}
			}

			if (property.equals(Resource.A_A_PERIOD)) {
				// if the start date is not null, then calculate the end date,
				if (null != activity.getActualStartDate()) {
					if (StringUtils.isBlank(valueStr)) {
						activity.setActualPeriod(null);
						activity.setActualEndDate(null);
					} else {
						try {
							activity
									.setActualPeriod(Integer.parseInt(valueStr));
						} catch (Exception e) {
							activity
									.setActualPeriod(activity.getActualPeriod());
						}
						activity.setActualEndDate(DateUtils.addDays(activity
								.getActualStartDate(), activity
								.getActualPeriod()));
					}
				} else {
					activity.setActualEndDate(null);
				}
			}

			if (property.equals(Resource.A_A_COST))
				try {
					activity.setActualCost(Double.parseDouble(valueStr));
				} catch (Exception e) {
					activity.setActualCost(activity.getActualCost());
				}
			if (property.equals(Resource.A_BUILDER))
				activity.setBuilder(valueStr);
			if (property.equals(Resource.A_R_DAYS))
				try {
					activity.setRarDays(Integer.parseInt(valueStr));
				} catch (Exception e) {
					activity.setRarDays(activity.getRarDays());
				}
			if (property.equals(Resource.A_R_COST))
				try {
					activity.setRarCost(Double.parseDouble(valueStr));
				} catch (Exception e) {
					activity.setRarCost(activity.getRarCost());
				}
			if (property.equals(Resource.A_RMARKS))
				activity.setRemarks(valueStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// save it to database
		DAOUtil.updateActivityToDababase(activity);
		//
		fireModelObjectChanged(null, activity, activity);
		tableViewer.update(activity, null);
		tableViewer.refresh(activity, true);
	}

	@Override
	public void addModelChangedListener(IModelChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void fireModelChanged(IModelChangedEvent event) {

	}

	@Override
	public void fireModelObjectChanged(Object object, Object oldValue,
			Object newValue) {
		for (IModelChangedListener listener : listeners) {
			listener.toString();
			listener.modelChanged(new ModelChangedEvent(this,
					IModelChangedEvent.CHANGE, oldValue, newValue));
		}
	}

	@Override
	public void removeModelChangedListener(IModelChangedListener listener) {
		listeners.remove(listener);
	}
}
