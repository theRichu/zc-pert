package com.ziscloud.zcdiagram.validator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class RequiredValidator implements IValidator {

	private String fieldName;

	public RequiredValidator(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public IStatus validate(Object value) {
		if (StringUtils.isBlank(value.toString())) {
			return ValidationStatus.error(fieldName + "为必填项。");
		} else {
			return Status.OK_STATUS;
		}
	}

}
