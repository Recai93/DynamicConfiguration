package com.trendyol.form.validator;

import com.trendyol.form.service.ConfRecordService;
import model.ConfRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import utils.Util;

@Component
public class ConfRecordFormValidator implements Validator {
	
	@Autowired
	private ConfRecordService confRecordService;

	public boolean supports(Class<?> clazz) {
		return ConfRecord.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		ConfRecord confRecord = (ConfRecord) target;
		ValidationUtils.rejectIfEmpty(errors, "isActive", "Required.confRecordForm.isActive");
		ValidationUtils.rejectIfEmpty(errors, "type", "Required.confRecordForm.type");
		ValidationUtils.rejectIfEmpty(errors, "value", "Required.confRecordForm.value");
		ValidationUtils.rejectIfEmpty(errors, "name", "Required.confRecordForm.name");
		ValidationUtils.rejectIfEmpty(errors, "applicationName", "Required.confRecordForm.applicationName");
		if(confRecord.getId() == null && confRecordService.isExist(confRecord.getName())){
			errors.rejectValue("name", "Valid.confRecordForm.name");
		}
		if(confRecord.getType() != null && confRecord.getValue() != null && !confRecord.getValue().isEmpty()){
			Object obj = Util.parseValue(confRecord.getType(), confRecord.getValue());
			if(obj == null){
				errors.rejectValue("value", "Valid.confRecordForm.value");
			}
		}
	}
}