package com.employee.test.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.test.api.dto.EmployeeDTO;
import com.employee.test.api.dto.EmployeeRequestDTO;
import com.employee.test.api.entity.Employee;
import com.employee.test.api.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	

	@GetMapping
	@ApiOperation(value = "Lists all the Employees", notes = "This API is used to fetch all the employees and display as list")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get employee details by ID", notes = "This API is used to fetch the details of the employee with ID")
	public EmployeeDTO getEmployee(@PathVariable(value = "id") Long id) {
		EmployeeDTO employeeDTO = employeeService.getEmployee(id);
		return employeeDTO;
	}
	
	@PostMapping
	@ApiOperation(value = "Save a new employee", notes = "This API is used to add a new employee and store the details")
	public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeRequestDTO employeeDTO) {
	     EmployeeDTO employeeResponseDTO =	employeeService.save(employeeDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDTO);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Update employee details", notes = "This API is used to update the employee details with the help of employee id")
	public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(value="id") Long id, @RequestBody EmployeeDTO employeeDTO) {
		EmployeeDTO employeeResponseDTO = employeeService.updateEmployee(id, employeeDTO);
		return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDTO);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete employee details", notes = "This API is used to delete the employee details with the help of employee id")
	public ResponseEntity<String> deleteEmployee(@PathVariable(value="id") Long id) {
		Boolean isDeletedBoolean = employeeService.deleteEmployee(id);
		if(isDeletedBoolean) {
			return new ResponseEntity<>("Deleted employee with id - " + id,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/search/{firstName}")
	@ApiOperation(value = "Search employee by first name", notes = "This API is used to search the employees by their first name")
	public List<Employee> searchByFirstName(@PathVariable(value="firstName") String firstName){
		return employeeService.getEmployeesByFirstName(firstName);
	}
	
	@GetMapping("/sort")
	@ApiOperation(value = "Sort the employee", notes = "This API is used to sort the employee details in ascending/ descending order")
	public List<Employee> sortByFirstName(@RequestParam Direction direction){
		return employeeService.getSortByFirstName(direction);
	}

	
}
