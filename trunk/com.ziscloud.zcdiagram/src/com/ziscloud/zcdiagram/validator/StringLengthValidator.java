package com.ziscloud.zcdiagram.validator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class StringLengthValidator implements IValidator {
	private String fieldName;
	private int minLength;
	private int maxLength;

	/**
	 * @param fieldName
	 * @param minLength
	 * @param maxLength
	 */
	public StringLengthValidator(String fieldName, int minLength, int maxLength) {
		this.fieldName = fieldName;
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	/**
	 * @param fieldName
	 * @param maxLength
	 */
	public StringLengthValidator(String fieldName, int maxLength) {
		this(fieldName, 0, maxLength);
	}

	@Override
	public IStatus validate(Object value) {
		String val = StringUtils.trim(value.toString());
		if (0 == minLength && val.length() > maxLength) {
			return ValidationStatus.error(fieldName + "的最大长度为：" + maxLength
					+ "个字符。");
		}
		if (0 != minLength
				&& (val.length() < minLength || val.length() > maxLength)) {
			return ValidationStatus.error(fieldName + "的长度为：" + minLength
					+ " - " + maxLength + "个字符。");
		}
		return Status.OK_STATUS;
	}
}
