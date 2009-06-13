package com.ziscloud.zcdiagram.validator;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class DoubleValueValidate implements IValidator {
	private String fieldName;
	private boolean isRequired;
	private double minValue;
	private double maxValue;

	/**
	 * @param fieldName
	 * @param minValue
	 * @param maxValue
	 */
	public DoubleValueValidate(String fieldName, boolean isRequired,
			double minValue, double maxValue) {
		this.fieldName = fieldName;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * @param fieldName
	 * @param maxValue
	 */
	public DoubleValueValidate(String fieldName, boolean isRequired,
			double maxValue) {
		this(fieldName, isRequired, 0, maxValue);
	}

	@Override
	public IStatus validate(Object value) {
		if (!isRequired && null == value) {
			return Status.OK_STATUS;
		}
		double val = 0;
		try {
			val = Double.parseDouble(value.toString());
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
