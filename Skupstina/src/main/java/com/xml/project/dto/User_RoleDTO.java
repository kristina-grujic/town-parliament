package com.xml.project.dto;

import com.xml.project.model.User_Role;

public class User_RoleDTO {

	private Long id;
	private UserDTO userDTO;
	private RoleDTO roleDTO;

	public User_RoleDTO() {
	}

	public User_RoleDTO(Long id, UserDTO userDTO, RoleDTO roleDTO) {
		super();
		this.id = id;
		this.userDTO = userDTO;
		this.roleDTO = roleDTO;
	}

	public User_RoleDTO(User_Role userRole) {
		this.id = userRole.getId();
		if (userRole.getRole() != null)
			this.roleDTO = new RoleDTO(userRole.getRole());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public RoleDTO getRoleDTO() {
		return roleDTO;
	}

	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
	}

}
