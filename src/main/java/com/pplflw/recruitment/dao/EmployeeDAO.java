package com.pplflw.recruitment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pplflw.recruitment.entity.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer>{

}
