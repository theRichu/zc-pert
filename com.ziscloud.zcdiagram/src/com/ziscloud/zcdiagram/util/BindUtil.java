package com.ziscloud.zcdiagram.util;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.ziscloud.zcdiagram.strategy.DateToTextUpdateStrategy;
import com.ziscloud.zcdiagram.strategy.TextToDateUpdateStrategy;
import com.ziscloud.zcdiagram.validator.StrategyFactory;

public class BindUtil {
	public static void bindInteger(DataBindingContext context,
			boolean isRequird, Object pojo, Text text, String field, String key) {
		context.bindValue(SWTObservables.observeText(text, SWT.Modify),
				PojoObservables.observeValue(pojo, field), StrategyFactory
						.IntRange(isRequird, Resource.get(key),
								Integer.MAX_VALUE), null);
	}

	public static void bindDouble(DataBindingContext context,
			boolean isRequird, Object pojo, Text text, String field, String key) {
		context.bindValue(SWTObservables.observeText(text, SWT.Modify),
				PojoObservables.observeValue(pojo, field), StrategyFactory
						.DoubleRange(isRequird, Resource.get(key),
								Double.MAX_VALUE), null);
	}

	public static void bindDate(DataBindingContext context, Object pojo,
			Text text, String field, String key) {
		context.bindValue(SWTObservables.observeText(text, SWT.Modify),
				PojoObservables.observeValue(pojo, field),
				new TextToDateUpdateStrategy(), new DateToTextUpdateStrategy());
	}

	public static void bindString(DataBindingContext context,
			boolean isRequird, Object pojo, Text text, String field,
			String key, int maxLen) {
		context.bindValue(SWTObservables.observeText(text, SWT.Modify),
				PojoObservables.observeValue(pojo, field), StrategyFactory
						.strLength(isRequird, Resource.get(key), maxLen), null);
	}
}
