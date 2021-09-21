package com.pplflw.recruitment.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pplflw.recruitment.dto.EmployeeDTO;

@Component
public class AddEmployeeValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return EmployeeDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name can't be null or empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "phoneNumber can't be null or empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "dateOfBirth can't be null or empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "grossSalary", "grossSalary can't be null or empty");
	}

}
