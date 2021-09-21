package com.pplflw.recruitment.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.pplflw.recruitment.dto.EmployeeDTO;
import com.pplflw.recruitment.entity.Employee;

@Mapper
public interface EmployeeToDTOMapper {

	EmployeeToDTOMapper INSTANCE = Mappers.getMapper(EmployeeToDTOMapper.class);

	
	EmployeeDTO entityToDTO (Employee employee);
	Employee dtoToEntity (EmployeeDTO employeeDTO);
	
	List<EmployeeDTO> entitiesToDTOs (List<Employee> leagues); 
}
