package com.pplflw.recruitment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.pplflw.recruitment.dto.EmployeeDTO;
import com.pplflw.recruitment.dto.UpdateEmployeeStateDTO;
import com.pplflw.recruitment.entity.enums.EmployeeState;
import com.pplflw.recruitment.service.EmployeeService;

@SpringBootTest
@Transactional
@Commit
@TestMethodOrder(OrderAnnotation.class)
class RecruitmentApplicationTests {

	@Autowired EmployeeService employeeService ;
	
	private EmployeeDTO getById(Integer id ) {
		return employeeService.getById(id);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1,2,3,4,5})
	@Order(1)
	public void addEmployee (int id) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setDateOfBirth(new Date());
		dto.setGrossSalary(3000.0);
		dto.setName("testEmp " + id);
		dto.setPhoneNumber("01022863696");
		
		employeeService.saveEmployee(dto);
		
		EmployeeDTO returnSavedEmp = getById(1);
		
		assertNotNull(returnSavedEmp);
		assertEquals(returnSavedEmp.getState(), EmployeeState.ADDED);
	}
	
	@Test
	@Order(2)
	public void testFetchAllEmployees () {
		List<EmployeeDTO> employees = employeeService.getAll();
		
		assertNotNull(employeeService);
		assertEquals(employees.size(), 5);
		
		employees.stream().forEach(emp -> {
			assertEquals(emp.getState(), EmployeeState.ADDED);
		});
	}
	
	@Test
	@Order(3)
	public void testInCheckEmployee () {
		UpdateEmployeeStateDTO updateEmployeeStateDTO = new UpdateEmployeeStateDTO();
		updateEmployeeStateDTO.setEmployeeId(1);
		
		employeeService.inCheckEmployee(updateEmployeeStateDTO);
		
		EmployeeDTO returnSavedEmp = getById(1);
		assertNotNull(returnSavedEmp);
		assertEquals(returnSavedEmp.getState(), EmployeeState.IN_CHECK);
		
	}
	
	@Test
	@Order(4)
	public void testApproveEmployee () {
		UpdateEmployeeStateDTO updateEmployeeStateDTO = new UpdateEmployeeStateDTO();
		updateEmployeeStateDTO.setEmployeeId(1);
		
		employeeService.approveEmployee(updateEmployeeStateDTO);
		
		EmployeeDTO returnSavedEmp = getById(1);
		assertNotNull(returnSavedEmp);
		assertEquals(returnSavedEmp.getState(), EmployeeState.APPROVED);
		
	}
	
	@Test
	@Order(5)
	public void testActivateEmployee () {
		UpdateEmployeeStateDTO updateEmployeeStateDTO = new UpdateEmployeeStateDTO();
		updateEmployeeStateDTO.setEmployeeId(1);
		
		employeeService.activateEmployee(updateEmployeeStateDTO);
		
		EmployeeDTO returnSavedEmp = getById(1);
		assertNotNull(returnSavedEmp);
		assertEquals(returnSavedEmp.getState(), EmployeeState.ACTIVE);
		
	}
}
