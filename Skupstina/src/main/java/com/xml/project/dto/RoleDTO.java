package com.xml.project.dto;

import java.util.HashSet;
import java.util.Set;

import com.xml.project.model.Role;

public class RoleDTO {

	private Long id;
	private String name;
	private Set<User_RoleDTO> user_roleDTO = new HashSet<User_RoleDTO>();

	public RoleDTO() {
	}

	public RoleDTO(Long id, String name, Set<User_RoleDTO> user_roleDTO) {
		super();
		this.id = id;
		this.name = name;
		this.user_roleDTO = user_roleDTO;
	}
	
	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User_RoleDTO> getUser_roleDTO() {
		return user_roleDTO;
	}

	public void setUser_roleDTO(Set<User_RoleDTO> user_roleDTO) {
		this.user_roleDTO = user_roleDTO;
	}

}
