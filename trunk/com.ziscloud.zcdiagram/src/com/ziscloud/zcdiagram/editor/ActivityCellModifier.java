package com.ziscloud.zcdiagram.editor;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.ziscloud.zcdiagram.dao.DAOUtil;
import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class ActivityCellModifier implements ICellModifier {
	TableViewer tableViewer;

	public ActivityCellModifier(TableViewer tableViewer) {
		super();
		this.tableViewer = tableViewer;
	}

	@Override
	public boolean canModify(Object element, String property) {
		return true;
	}

	@Override
	public Object getValue(Object element, String property) {
		if (element instanceof Activity) {
			Activity activity = (Activity) element;
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
				activity.setPlanPeriod(Integer.parseInt(valueStr));
				activity.setPlanEndDate(DateUtils.addDays(activity
						.getPlanStartDate(), activity.getPlanPeriod()));
			}
			if (property.equals(Resource.A_P_COST))
				activity.setPlanCost(Double.parseDouble(valueStr));
			if (property.equals(Resource.A_OUTPUT))
				activity.setOutput(Double.parseDouble(valueStr));
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
				if (!StringUtils.isEmpty(valueStr)) {
					activity.setActualStartDate(DateUtils.parseDate(valueStr,
							SWTHelper.DATE_PATERNS));
					if (null != activity.getActualPeriod()) {
						activity.setActualEndDate(DateUtils.addDays(activity
								.getActualStartDate(), activity
								.getActualPeriod()));
					} else {
						activity
								.setActualEndDate(null);
					}
				} else {
					activity.setActualStartDate(null);
					activity.setActualEndDate(null);
					activity.setActualPeriod(null);
				}
			}

			if (property.equals(Resource.A_A_PERIOD)) {
				if (null != activity.getActualStartDate()) {
					activity.setActualPeriod(Integer.parseInt(valueStr));
					activity.setActualEndDate(DateUtils.addDays(activity
							.getActualStartDate(), activity.getActualPeriod()));
				} else {
					activity.setActualPeriod(null);
					activity.setActualEndDate(null);
				}
			}

			if (property.equals(Resource.A_A_COST))
				activity.setActualCost(Double.parseDouble(valueStr));
			if (property.equals(Resource.A_BUILDER))
				activity.setBuilder(valueStr);
			if (property.equals(Resource.A_R_DAYS))
				activity.setRarDays(Integer.parseInt(valueStr));
			if (property.equals(Resource.A_R_COST))
				activity.setRarCost(Double.parseDouble(valueStr));
			if (property.equals(Resource.A_RMARKS))
				activity.setRemarks(valueStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// save it to database
		DAOUtil.updateActivityToDababase(activity);
		//
		tableViewer.update(activity, null);
	}

}
