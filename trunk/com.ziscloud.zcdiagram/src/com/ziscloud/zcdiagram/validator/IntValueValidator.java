package com.ziscloud.zcdiagram.validator;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class IntValueValidator implements IValidator {
	private String fieldName;
	private boolean isRequired;
	private int minValue;
	private int maxValue;

	/**
	 * @param fieldName
	 * @param minValue
	 * @param maxValue
	 */
	public IntValueValidator(String fieldName, boolean isRequired,
			int minValue, int maxValue) {
		this.fieldName = fieldName;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * @param fieldName
	 * @param maxValue
	 */
	public IntValueValidator(String fieldName, boolean isRequired, int maxValue) {
		this(fieldName, isRequired, 0, maxValue);
	}

	@Override
	public IStatus validate(Object value) {
		if (!isRequired && null == value) {
			return Status.OK_STATUS;
		}
		int val = 0;
		try {
			val = Integer.parseInt(value.toString());
		} catch (Exception e) {
			return ValidationStatus.error(fieldName + "不是数字。");
		}
		if (val < minValue || val > maxValue) {
			return ValidationStatus.error(fieldName + "的取值范围：" + minValue
					+ " - " + maxValue);
		}
		return Status.OK_STATUS;
	}
}
