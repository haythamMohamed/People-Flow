package com.pplflw.recruitment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pplflw.recruitment.entity.enums.EmployeeState;

import lombok.Data;

@Entity
@Table(name = "employee_states_log")
@Data
public class EmployeeStatesLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	@ManyToOne
	@JoinColumn
	private Employee employee ;
	
	@Enumerated(EnumType.STRING)
	private EmployeeState state ;
	
	@Column(name = "action_date")
	private Date actionDate ;
}
