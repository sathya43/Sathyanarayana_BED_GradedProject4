package com.employee.test.api.service;

import java.util.List;

import com.employee.test.api.dto.RoleDTO;
import com.employee.test.api.dto.RoleRequestDTO;
import com.employee.test.api.entity.Role;



public interface RoleService {
	RoleDTO saveRole(RoleRequestDTO roleDTO);
	List<RoleDTO> getAllRoles();
	RoleDTO getRoleById(Long id);
	RoleDTO updateRole(Long id, RoleDTO roleDTO);
    RoleDTO mapToDTO(Role role);
    boolean deleteRole(Long id);
}
