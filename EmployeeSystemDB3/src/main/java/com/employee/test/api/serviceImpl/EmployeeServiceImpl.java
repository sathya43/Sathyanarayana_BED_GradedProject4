package com.employee.test.api.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.employee.test.api.dto.EmployeeDTO;
import com.employee.test.api.dto.EmployeeRequestDTO;
import com.employee.test.api.dto.EmployeeDTO;
import com.employee.test.api.entity.Employee;
import com.employee.test.api.entity.Role;
import com.employee.test.api.exceptions.EmployeeAPIException;
import com.employee.test.api.repository.EmployeeRepository;
import com.employee.test.api.service.EmployeeService;



@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		super();
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public EmployeeDTO getEmployee(Long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee employee = null;
		EmployeeDTO employeeDTO;
		if(optional.isPresent()) {
			employee = optional.get();
			employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
		}else {
			throw new EmployeeAPIException(HttpStatus.NOT_FOUND, "Employee Not found");
		}
		return employeeDTO;
	}

	@Override
	public EmployeeDTO save(EmployeeRequestDTO employeeDTO) {
		Employee employee = modelMapper.map(employeeDTO, Employee.class);
		employeeRepository.save(employee);
		return mapToDTO(employee);
	}
	
	@Override
	public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		Employee employee = null;
		if(optionalEmployee.isPresent()) {
			employee = modelMapper.map(employeeDTO, Employee.class);
			employee.setId(id);
			employeeRepository.save(employee);
		}else {
			throw new EmployeeAPIException(HttpStatus.NOT_FOUND, "Employee Not found");
		}
		
		return mapToDTO(employee);
		
	}
	
	@Override
	public boolean deleteEmployee(Long id) {
		if(employeeRepository.existsById(id)) {
			employeeRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public EmployeeDTO mapToDTO(Employee employee) {
		EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
		return employeeDTO;
	}
	
	@Override
	public List<Employee> getEmployeesByFirstName(String firstName){
		Employee employeeWithFirstName = new Employee();
		employeeWithFirstName.setFirstName(firstName);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				                         .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.exact())
				                         .withIgnoreCase("id","lastName","email");
		Example<Employee> example = Example.of(employeeWithFirstName,exampleMatcher);
		return employeeRepository.findAll(example);
		
	}
	
	@Override
	public List<Employee> getSortByFirstName(Direction direction){
		return employeeRepository.findAll(Sort.by(direction, "firstName"));
	}
	

	


}
