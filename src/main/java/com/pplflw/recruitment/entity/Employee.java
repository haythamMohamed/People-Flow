package com.pplflw.recruitment.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pplflw.recruitment.entity.enums.EmployeeState;

import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	private String name ;
	private String phoneNumber ;
	private Date dateOfBirth ;
	private Double grossSalary ;

	@Enumerated(EnumType.STRING)
	private EmployeeState state ;
}
