package com.pplflw.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pplflw.recruitment.controller.validation.AddEmployeeValidation;
import com.pplflw.recruitment.dto.EmployeeDTO;
import com.pplflw.recruitment.dto.UpdateEmployeeStateDTO;
import com.pplflw.recruitment.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	@Autowired AddEmployeeValidation addEmployeeValidation ;
	
	@GetMapping("")
	public List<EmployeeDTO> getAllEmployee (){
		return employeeService.getAll();
	}
	
	@GetMapping("/{id}")
	public EmployeeDTO getById (@PathVariable(name = "id") Integer id){
		return employeeService.getById(id);
	}
	
	@PostMapping("")
	public void addEmployee (@RequestBody EmployeeDTO employeeDTO , BindingResult bindingResult) throws BindException {
		addEmployeeValidation.validate(employeeDTO, bindingResult);
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		employeeService.saveEmployee(employeeDTO);
	}
	
	@PutMapping("/in-check")
	public void inCheckEmployee (@RequestBody UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		employeeService.inCheckEmployee(updateEmployeeStateDTO);
	}
	
	@PutMapping("/approve")
	public void approveEmployee (@RequestBody UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		employeeService.approveEmployee(updateEmployeeStateDTO);
	}
	
	@PutMapping("/activate")
	public void activeEmployee (@RequestBody UpdateEmployeeStateDTO updateEmployeeStateDTO) {
		employeeService.activateEmployee(updateEmployeeStateDTO);
	}
}
