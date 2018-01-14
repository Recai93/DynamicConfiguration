package com.trendyol.form.validator;

import com.trendyol.form.service.ConfRecordService;
import model.ConfRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ConfRecordFormValidator implements Validator {
	
	@Autowired
	private ConfRecordService confRecordService;

	public boolean supports(Class<?> clazz) {
		return ConfRecord.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		ConfRecord confRecord = (ConfRecord) target;
		ValidationUtils.rejectIfEmpty(errors, "isActive", "Valid.confRecordForm.isActive");
		ValidationUtils.rejectIfEmpty(errors, "type", "Valid.confRecordForm.type");
		if(confRecord.getId() == null && confRecordService.isExist(confRecord.getName())){
			errors.rejectValue("name", "Valid.confRecordForm.name");
		}
	}
}