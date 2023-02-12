package com.employee.test.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.test.api.entity.Role;

@Repository
public interface RoleJpaRepository extends JpaRepository<Role, Long  >{


	Optional<Role> findByName(String name);
}
