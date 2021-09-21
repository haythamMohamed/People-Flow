package com.pplflw.recruitment.dto;

import java.util.Date;

import com.pplflw.recruitment.entity.enums.EmployeeState;

import lombok.Data;

@Data
public class EmployeeDTO {

	private String name ;
	private String phoneNumber ;
	private Date dateOfBirth ;
	private Double grossSalary ;
	private EmployeeState state ;
}
