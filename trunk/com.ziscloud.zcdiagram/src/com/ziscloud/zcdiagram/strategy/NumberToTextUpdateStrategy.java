package com.ziscloud.zcdiagram.strategy;

import org.eclipse.core.databinding.UpdateValueStrategy;

public class NumberToTextUpdateStrategy extends UpdateValueStrategy {
	@Override
	public Object convert(Object value) {
		return value.toString();
	}
}
