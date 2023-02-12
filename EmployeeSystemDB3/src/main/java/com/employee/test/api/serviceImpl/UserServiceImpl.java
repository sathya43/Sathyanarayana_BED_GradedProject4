package com.employee.test.api.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.employee.test.api.dto.RoleDTO;
import com.employee.test.api.dto.UserDTO;
import com.employee.test.api.dto.UserRequestDTO;
import com.employee.test.api.dto.UserResponseDTO;
import com.employee.test.api.entity.Role;
import com.employee.test.api.entity.User;
import com.employee.test.api.exceptions.EmployeeAPIException;
import com.employee.test.api.repository.RoleJpaRepository;
import com.employee.test.api.repository.UserJpaRepository;
import com.employee.test.api.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	
	private final UserJpaRepository userJpaRepository;
	
	private final RoleJpaRepository roleJpaRepository;

	private  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	private final ModelMapper modelMapper;


    UserServiceImpl(UserJpaRepository userJpaRepository, ModelMapper modelMapper,RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
		this.modelMapper = modelMapper;
		this.roleJpaRepository = roleJpaRepository;
    }
	
	

	@Override
	public UserResponseDTO registerUser(@RequestBody UserRequestDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		user.setRoles(mapRoles(userDTO));
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user = userJpaRepository.save(user);
	    return mapToDto(user);
    }

	@Override
	public List<UserResponseDTO> getUsers() {
		List<User> users = userJpaRepository.findAll();
		List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
		for(User user: users) {
			UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
			userResponseDTOs.add(userResponseDTO);
		}
		return userResponseDTOs;
	}



	@Override
	public UserResponseDTO getUserByID(Long id) {
		Optional<User> optional = userJpaRepository.findById(id);
		User user = null;
		if(optional.isPresent()) {
			user = optional.get();
		}
		else {
			throw new EmployeeAPIException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
		return mapToDto(user);
	}
	
	

	@Override
	public UserResponseDTO updateUser(Long id, UserDTO userDTO) {
		Optional<User> optionalUser = userJpaRepository.findById(id);
		User user;
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
			user.setUsername(userDTO.getUsername());
			removeAllRoles(user);
			user.setRoles(mapRoles(userDTO));
			userJpaRepository.save(user);
		}
		else {
			throw new EmployeeAPIException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
		return mapToDto(user);
	}



	@Override
	public boolean deleteUser(Long id) {
		Optional<User> userOptional =  userJpaRepository.findById(id);
		if(userOptional.isPresent()) {
			userJpaRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
		
	}

	
	
	
	@Override
	public UserResponseDTO mapToDto(User user) {
		UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
		return userResponseDTO;
	}
	
	public void removeAllRoles(User user) {
		for(Role role: user.getRoles()) {
			user.removeRole(role);
		}
	}





	@Override
	public Set<Role> mapRoles(UserDTO userDTO){
		Set<Role> roles = new HashSet<>();
	    for(RoleDTO role: userDTO.getRoles()) {
	    Role userRole = roleJpaRepository.findByName(role.getName()).get();
	    roles.add(userRole);
	    }
	    return roles;
	}
    
	@Override
	public Set<Role> mapRoles(UserRequestDTO userDTO){
		Set<Role> roles = new HashSet<>();
	    for(RoleDTO role: userDTO.getRoles()) {
	    Role userRole = roleJpaRepository.findByName(role.getName()).get();
	    roles.add(userRole);
	    }
	    return roles;
	}
  

}
