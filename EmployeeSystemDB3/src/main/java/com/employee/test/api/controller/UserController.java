package com.employee.test.api.controller;

import java.util.List;

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

import com.employee.test.api.dto.UserDTO;
import com.employee.test.api.dto.UserRequestDTO;
import com.employee.test.api.dto.UserResponseDTO;
import com.employee.test.api.entity.Role;
import com.employee.test.api.entity.User;
import com.employee.test.api.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	

	

	@GetMapping
	public List<UserResponseDTO> getRoles(){
		return userService.getUsers();
	}
	
	@GetMapping("/{id}")
	public UserResponseDTO getRoleById(@PathVariable(value = "id") Long id){
		return userService.getUserByID(id);
		
	}
	
	@PostMapping
	public ResponseEntity<UserResponseDTO> saveRole(@RequestBody UserRequestDTO userDTO) {
		UserResponseDTO userResponseDTO = userService.registerUser(userDTO);
		System.out.println(userResponseDTO.toString());
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
	}
	
	//Working on it
	//@PutMapping("/{id}")
	public UserResponseDTO updateUser(@PathVariable(value = "id") Long id,UserDTO userDTO) {
		return userService.updateUser(id, userDTO);
	}
  
	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteUser(@PathVariable(value = "id") Long id){
		boolean isDeleted = userService.deleteUser(id);
		if(isDeleted) {
			return new ResponseEntity<>(id,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
