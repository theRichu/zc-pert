package com.ziscloud.zcdiagram.strategy;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;

import com.ziscloud.zcdiagram.util.SWTHelper;

public class TextToDateUpdateStrategy extends UpdateValueStrategy {

	@Override
	public Object convert(Object value) {
		try {
			return DateUtils.parseDate(value.toString(), SWTHelper.DATE_PATERNS);
		} catch (Exception e) {
			return super.convert(value);
		}

	}

}
