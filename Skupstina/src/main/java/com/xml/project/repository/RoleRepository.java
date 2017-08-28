package com.xml.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xml.project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
