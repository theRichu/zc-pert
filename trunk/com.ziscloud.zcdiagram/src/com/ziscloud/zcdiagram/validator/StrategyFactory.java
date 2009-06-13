package com.ziscloud.zcdiagram.validator;

import org.eclipse.core.databinding.UpdateValueStrategy;

public class StrategyFactory {

	public static UpdateValueStrategy strLength(boolean isReqired,
			String fieldName, int maxLength) {
		UpdateValueStrategy updateStrategy = new UpdateValueStrategy();
		if (isReqired) {
			updateStrategy
					.setAfterGetValidator(new RequiredValidator(fieldName));
		}
		updateStrategy.setAfterConvertValidator(new StringLengthValidator(
				fieldName, maxLength));
		return updateStrategy;
	}

	public static UpdateValueStrategy IntRange(boolean isReqired,
			String fieldName, int maxValue) {
		UpdateValueStrategy updateStrategy = new UpdateValueStrategy();
		if (isReqired) {
			updateStrategy
					.setAfterGetValidator(new RequiredValidator(fieldName));
		}
		updateStrategy.setAfterConvertValidator(new IntValueValidator(
				fieldName, isReqired, maxValue));
		return updateStrategy;
	}

	public static UpdateValueStrategy DoubleRange(boolean isReqired,
			String fieldName, double maxValue) {
		UpdateValueStrategy updateStrategy = new UpdateValueStrategy();
		if (isReqired) {
			updateStrategy
					.setAfterGetValidator(new RequiredValidator(fieldName));
		}
		updateStrategy.setAfterConvertValidator(new DoubleValueValidate(
				fieldName, isReqired, maxValue));
		return updateStrategy;
	}
}
