package com.employee.test.api.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.employee.test.api.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	
    private Long id;
    
    private String username;
    
    private String email;
   
    private String password;

    private List<RoleDTO> roles;

}
