package com.xml.project.dto;


import java.util.HashSet;
import java.util.Set;

import com.xml.project.model.User;

public class UserDTO {

	private Long id;
	private String lName;
	private String fName;
	private String username;
	private String email;
	private String pass;
	private Set<User_RoleDTO> user_roleDTO = new HashSet<>();

	public UserDTO() {
	}

	public UserDTO(Long id, String lName, String fName, String username,
			String email, String pass, Set<User_RoleDTO> user_roleDTO) {
		super();
		this.id = id;
		this.lName = lName;
		this.fName = fName;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.user_roleDTO = user_roleDTO;
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.lName = user.getlName();
		this.fName = user.getfName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.pass = user.getPass();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	

}
