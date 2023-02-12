package com.employee.test.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.test.api.dto.RoleDTO;
import com.employee.test.api.dto.RoleRequestDTO;
import com.employee.test.api.entity.Role;
import com.employee.test.api.service.RoleService;




@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@GetMapping
	public List<RoleDTO> getAllRoles() {
		return roleService.getAllRoles();
	}
	
	@GetMapping("/{id}")
	public RoleDTO getRoleById(@PathVariable(value = "id") Long id) {
		return roleService.getRoleById(id);
	}
	
	@PostMapping
	public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleRequestDTO roleDTO) {
		
		RoleDTO roleResponseDto =  roleService.saveRole(roleDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(roleResponseDto);
	
	}
	
	@PutMapping("/{id}")
	public RoleDTO updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
		return roleService.updateRole(id, roleDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteRole(@PathVariable(value = "id") Long id)
	{
		boolean isRemoved = roleService.deleteRole(id);
		if(isRemoved) {
			return new ResponseEntity<>(id,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}
