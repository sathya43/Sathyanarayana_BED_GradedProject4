package com.employee.test.api.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.employee.test.api.dto.RoleDTO;
import com.employee.test.api.dto.RoleRequestDTO;
import com.employee.test.api.entity.Role;
import com.employee.test.api.exceptions.EmployeeAPIException;
import com.employee.test.api.repository.RoleJpaRepository;
import com.employee.test.api.service.RoleService;

import org.modelmapper.ModelMapper;



@Service
public class RoleServiceImpl implements RoleService {
	

    private RoleJpaRepository roleJpaRepository;
	
	private ModelMapper mapper;
	
	

	public RoleServiceImpl(RoleJpaRepository roleJpaRepository, ModelMapper mapper) {
		this.roleJpaRepository = roleJpaRepository;
		this.mapper = mapper;
	}

	@Override
	public RoleDTO saveRole(RoleRequestDTO roleDTO) {
	
		Role role = mapper.map(roleDTO, Role.class);
	    role = roleJpaRepository.save(role);
	    return mapToDTO(role);
	
	}
	
	@Override
	public RoleDTO mapToDTO(Role role) {
		
		RoleDTO roleDTO = mapper.map(role,RoleDTO.class);
		return roleDTO;
		
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		List<Role> roles = roleJpaRepository.findAll();
		List<RoleDTO> roleDTOs = new ArrayList<>();
		for(Role role: roles) {
			RoleDTO roleDTO = mapper.map(role, RoleDTO.class);
			roleDTOs.add(roleDTO);
		}
		return roleDTOs;
	}

	@Override
	public RoleDTO getRoleById(Long id) {
		Optional<Role> optionalRole = roleJpaRepository.findById(id);
		Role role = null;
		if(optionalRole.isPresent()) {
			role = optionalRole.get();
		}else {
			throw new EmployeeAPIException(HttpStatus.NOT_FOUND, "role not found for id: " + id);	
		}
		return mapToDTO(role);
		
	}
	
	@Override
	public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
		Optional<Role> optionalRole = roleJpaRepository.findById(id);
		Role role = null;
		if(optionalRole.isPresent()) {
			role = mapper.map(roleDTO, Role.class);
			role.setId(id);
			roleJpaRepository.save(role);
		}else {
			throw new EmployeeAPIException(HttpStatus.NOT_FOUND, "role not found for id: " + id);	
		}
		
		return mapToDTO(role);
		
	}

	@Override
	public boolean deleteRole(Long id) {
		if(roleJpaRepository.existsById(id)) {
			roleJpaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}
	
	

}
