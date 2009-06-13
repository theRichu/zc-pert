package com.ziscloud.zcdiagram.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.ziscloud.zcdiagram.pojo.Activity;
import com.ziscloud.zcdiagram.util.ImageUtil;
import com.ziscloud.zcdiagram.util.SWTHelper;

public class ActivityTableLabelProvider extends LabelProvider implements
		ITableLabelProvider {

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
			switch (columnIndex) {
			case 0:
				return SWTHelper.nullToString(activity.getName());
			case 1:
				return SWTHelper.nullToString(activity.getSymbol());
			case 2:
				return activity.getPreActivity();
			case 3:
				return SWTHelper.nullToString(activity.getPlanPeriod());
			case 4:
				return SWTHelper.nullToString(activity.getPlanCost());
			case 5:
				return SWTHelper.nullToString(activity.getOutput());
			case 6:
				return SWTHelper.dateColumnText(activity.getPlanStartDate());
			case 7:
				return SWTHelper.dateColumnText(activity.getPlanEndDate());
			case 8:
				return SWTHelper.dateColumnText(activity.getMustStartDate());
			case 9:
				return SWTHelper.dateColumnText(activity.getMustEndDate());
			case 10:
				return SWTHelper.dateColumnText(activity.getLaterStartDate());
			case 11:
				return SWTHelper.dateColumnText(activity.getLaterEndDate());
			case 12:
				return SWTHelper.dateColumnText(activity.getEarlyStartDate());
			case 13:
				return SWTHelper.dateColumnText(activity.getEarlyEndDate());
			case 14:
				return SWTHelper.dateColumnText(activity.getActualStartDate());
			case 15:
				return SWTHelper.dateColumnText(activity.getActualEndDate());
			case 16:
				return SWTHelper.nullToString(activity.getActualPeriod());
			case 17:
				return SWTHelper.nullToString(activity.getActualCost());
			case 18:
				return SWTHelper.nullToString(activity.getBuilder());
			case 19:
				return SWTHelper.nullToString(activity.getRarDays());
			case 20:
				return SWTHelper.nullToString(activity.getRarCost());
			case 21:
				return SWTHelper.nullToString(activity.getRemarks());
			default:
				return SWTHelper.UNKNOWN;
			}
		} else {
			return SWTHelper.UNKNOWN;
		}
	}

}
