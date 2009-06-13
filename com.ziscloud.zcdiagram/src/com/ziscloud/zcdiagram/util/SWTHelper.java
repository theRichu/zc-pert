package com.ziscloud.zcdiagram.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SWTHelper {
	public static final String DATE_PATERN = "yyyy-MM-dd";
	public static final String[] DATE_PATERNS = new String[] { DATE_PATERN };
	public static final int TEXT_MAXLEN = 65535;
	public static final int FIELD_MAXLEN = 255;
	public static final String UNKNOWN = "";
	public static final String EXPORTIMGACTION_ID = "com.ziscloud.zcdiagram.action.exportimg";

	public static Label placeHolder(Composite container) {
		return new Label(container, SWT.NONE);
	}

	public static boolean isDecimal(String number) {
		int index = number.indexOf(".");
		if (index < 0) {
			return isNumeric(number);
		} else {
			String partA = number.substring(0, index);
			String partB = number.substring(index + 1);
			return isNumeric(partA) && StringUtils.isNumeric(partB);
		}
	}

	public static boolean isNumeric(String number) {
		return ((!isStartWithZero(number)) && StringUtils.isNumeric(number));
	}

	public static boolean isStartWithZero(String number) {
		return (number.startsWith("0") && (number.length() > 1));
	}

	public static String randomSymbol() {
		return String.valueOf((char) ((int) (Math.random() * 26 + 65)));
	}

	public static String dateColumnText(Date date) {
		if (null == date) {
			return UNKNOWN;
		} else {
			return DateFormatUtils.format(date, SWTHelper.DATE_PATERN);
		}
	}

	public static String nullToString(Object object) {
		if (null == object) {
			return UNKNOWN;
		} else {
			return object.toString();
		}
	}

	public static Label createSeparator(Composite container, int hSpan) {
		Label horizontal = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData horizontalLData = new GridData(GridData.FILL_HORIZONTAL);
		horizontalLData.heightHint = 20;
		horizontalLData.horizontalSpan = hSpan;
		horizontal.setLayoutData(horizontalLData);
		return horizontal;
	}

	public static int intervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}

}
