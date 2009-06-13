package com.ziscloud.zcdiagram.strategy;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;

import com.ziscloud.zcdiagram.util.SWTHelper;

public class DateToTextUpdateStrategy extends UpdateValueStrategy {

	@Override
	public Object convert(Object value) {
		if (value instanceof Date) {
			return DateFormatUtils.format((Date) value, SWTHelper.DATE_PATERN);
		}
		return super.convert(value);
	}
	
}
