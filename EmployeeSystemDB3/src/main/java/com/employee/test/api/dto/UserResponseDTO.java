package com.employee.test.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
	
	private Long id;
	
	private String username;
	
	private List<RoleDTO> roles;
	
	
	

}
