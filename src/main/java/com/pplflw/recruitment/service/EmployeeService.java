package com.pplflw.recruitment.service;

import java.util.List;

import com.pplflw.recruitment.dto.EmployeeDTO;
import com.pplflw.recruitment.dto.UpdateEmployeeStateDTO;

public interface EmployeeService {

	void saveEmployee (EmployeeDTO employeeDTO);
	
	void inCheckEmployee (UpdateEmployeeStateDTO updateEmployeeStateDTO);
	void approveEmployee (UpdateEmployeeStateDTO updateEmployeeStateDTO);
	void activateEmployee (UpdateEmployeeStateDTO updateEmployeeStateDTO);
	
	List<EmployeeDTO> getAll ();
	EmployeeDTO getById (Integer id);
}
