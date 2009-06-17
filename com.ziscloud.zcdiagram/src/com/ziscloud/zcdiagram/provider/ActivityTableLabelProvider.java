package com.ziscloud.zcdiagram.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.Resource;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class ActivityTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	private String[] columnProperties;

	public ActivityTableLabelProvider(String[] columnProperties) {
		super();
		this.columnProperties = columnProperties;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (1 == columnIndex)
			return ImageUtil.BLANK.createImage();
		else
			return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Activity) {
			Activity activity = (Activity) element;
			String columnName = columnProperties[columnIndex];
			if (columnName.equals(Resource.A_NAME)) {
				return SWTHelper.nullToString(activity.getName());
			}
			if (columnName.equals(Resource.A_SYBOL)) {
				return SWTHelper.nullToString(activity.getSymbol());
			}
			if (columnName.equals(Resource.A_PRE)) {
				return activity.getPreActivity();
			}
			if (columnName.equals(Resource.A_P_PERIOD)) {
				return SWTHelper.nullToString(activity.getPlanPeriod());
			}
			if (columnName.equals(Resource.A_P_COST)) {
				return SWTHelper.nullToString(activity.getPlanCost());
			}
			if (columnName.equals(Resource.A_OUTPUT)) {
				return SWTHelper.nullToString(activity.getOutput());
			}
			if (columnName.equals(Resource.A_P_START)) {
				return SWTHelper.dateColumnText(activity.getPlanStartDate());
			}
			if (columnName.equals(Resource.A_P_END)) {
				return SWTHelper.dateColumnText(activity.getPlanEndDate());
			}
			if (columnName.equals(Resource.A_M_START)) {
				return SWTHelper.dateColumnText(activity.getMustStartDate());
			}
			if (columnName.equals(Resource.A_M_END)) {
				return SWTHelper.dateColumnText(activity.getMustEndDate());
			}
			if (columnName.equals(Resource.A_L_START)) {
				return SWTHelper.dateColumnText(activity.getLaterStartDate());
			}
			if (columnName.equals(Resource.A_L_END)) {
				return SWTHelper.dateColumnText(activity.getLaterEndDate());
			}
			if (columnName.equals(Resource.A_E_START)) {
				return SWTHelper.dateColumnText(activity.getEarlyStartDate());
			}
			if (columnName.equals(Resource.A_E_END)) {
				return SWTHelper.dateColumnText(activity.getEarlyEndDate());
			}
			if (columnName.equals(Resource.A_A_START)) {
				return SWTHelper.dateColumnText(activity.getActualStartDate());
			}
			if (columnName.equals(Resource.A_A_END)) {
				return SWTHelper.dateColumnText(activity.getActualEndDate());
			}
			if (columnName.equals(Resource.A_A_PERIOD)) {
				return SWTHelper.nullToString(activity.getActualPeriod());
			}
			if (columnName.equals(Resource.A_A_COST)) {
				return SWTHelper.nullToString(activity.getActualCost());
			}
			if (columnName.equals(Resource.A_BUILDER)) {
				return SWTHelper.nullToString(activity.getBuilder());
			}
			if (columnName.equals(Resource.A_R_DAYS)) {
				return SWTHelper.nullToString(activity.getRarDays());
			}
			if (columnName.equals(Resource.A_R_COST)) {
				return SWTHelper.nullToString(activity.getRarCost());
			}
			if (columnName.equals(Resource.A_RMARKS)) {
				return SWTHelper.nullToString(activity.getRemarks());
			}
			if (columnName.equals(Resource.A_OP_ONE_START)) {
				return SWTHelper.dateColumnText(activity.getPopStartDate());
			}
			if (columnName.equals(Resource.A_OP_ONE_END)) {
				return SWTHelper.dateColumnText(activity.getPopEndDate());
			}
			if (columnName.equals(Resource.A_OP_TWO_START)) {
				return SWTHelper.dateColumnText(activity.getOptopStartDate());
			}
			if (columnName.equals(Resource.A_OP_TWO_END)) {
				return SWTHelper.dateColumnText(activity.getOptopEndDate());
			}
			return SWTHelper.UNKNOWN;
		} else {
			return SWTHelper.UNKNOWN;
		}
	}

}
