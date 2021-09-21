package com.pplflw.recruitment.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplflw.recruitment.dao.EmployeeStatesLogDAO;
import com.pplflw.recruitment.entity.EmployeeStatesLog;
import com.pplflw.recruitment.service.EmployeeStatesLogService;

@Service
@Transactional
public class EmployeeStatesLogServiceImpl implements EmployeeStatesLogService{

	@Autowired EmployeeStatesLogDAO employeeStatusLogDAO ;
	
	@Override
	public void save(EmployeeStatesLog employeeStatesLog) {
		employeeStatusLogDAO.save(employeeStatesLog);		
	}

}
