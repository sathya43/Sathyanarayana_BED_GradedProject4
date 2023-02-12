package com.employee.test.api.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.employee.test.api.dto.EmployeeDTO;
import com.employee.test.api.dto.EmployeeRequestDTO;
import com.employee.test.api.entity.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();
	EmployeeDTO getEmployee(Long id);
	EmployeeDTO save(EmployeeRequestDTO employeeDTO);
	EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
	EmployeeDTO mapToDTO(Employee employee);
	boolean deleteEmployee(Long id);
	List<Employee> getEmployeesByFirstName(String firstName);
	List<Employee> getSortByFirstName(Direction direction);
}
