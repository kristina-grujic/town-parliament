package com.xml.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xml.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

	public User findByEmail(String email);
}
