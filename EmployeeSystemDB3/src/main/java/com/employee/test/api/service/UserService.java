package com.employee.test.api.service;


import java.util.List;
import java.util.Set;

import com.employee.test.api.dto.UserDTO;
import com.employee.test.api.dto.UserRequestDTO;
import com.employee.test.api.dto.UserResponseDTO;
import com.employee.test.api.entity.Role;
import com.employee.test.api.entity.User;

public interface UserService {

	UserResponseDTO registerUser(UserRequestDTO userDTO);
	
	List<UserResponseDTO> getUsers();
	
	UserResponseDTO getUserByID(Long id);
	
	Set<Role> mapRoles(UserDTO userDTO);
	
	UserResponseDTO mapToDto(User user);
	
	UserResponseDTO updateUser(Long id, UserDTO userDTO);
	
	boolean deleteUser(Long id);

	Set<Role> mapRoles(UserRequestDTO userDTO);
}
