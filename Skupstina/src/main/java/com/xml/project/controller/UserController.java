package com.xml.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xml.project.dto.LoginDTO;
import com.xml.project.dto.MesagesDTO;
import com.xml.project.dto.UserDTO;
import com.xml.project.model.Role;
import com.xml.project.model.User;
import com.xml.project.model.User_Role;
import com.xml.project.repository.RoleRepository;
import com.xml.project.repository.UserRepository;
import com.xml.project.repository.UserRoleRepository;
import com.xml.project.security.TokenUtils;
import com.xml.project.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;


	/**
	 * Ispis svih registrovanih usersa koji nisu PRESIDENT
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getAdopt() {


		List<User> user = userService.findAll();

		List<UserDTO> userDTO = new ArrayList<>();
		for(User u : user) {
			if(!u.getUser_role().getRole().getName().equals("PRESIDENT"))
				userDTO.add(new UserDTO(u));
		}

		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<MesagesDTO> login(@RequestBody LoginDTO loginDTO) {
		MesagesDTO messagesDTO = new MesagesDTO();
		try {

			User users = userService.findByUsername(loginDTO.getUsername());
			// Perform the authentication
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Reload user details so we can generate token
			UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());

			messagesDTO.setJwt(tokenUtils.generateToken(details));
			messagesDTO.setRola(users.getUser_role().getRole().getName());
			return new ResponseEntity<MesagesDTO>(messagesDTO, HttpStatus.OK);
		} catch (Exception ex) {
			messagesDTO.setError("invalid");
			return new ResponseEntity<MesagesDTO>(messagesDTO, HttpStatus.OK);
		}
	}


	@RequestMapping(value = "/populate", method = RequestMethod.GET)
	public ResponseEntity<String> populate() {

		String[] roles = { "ADMIN", "PRESIDENT", "ALDERMAN" };
		Role role;
		// save roles
		for (int i = 0; i < roles.length; i++) {
			if (roleRepository.findByName(roles[i]) == null) {
				role = new Role();
				role.setName(roles[i]);
				roleRepository.save(role);
			}
		}
		// save user to database

		if (userService.findByUsername("admin") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User();
			user.setEmail("xml_admin@mailinator.com");
			user.setfName("Stefan");
			user.setlName("Savic");
			user.setUsername("admin");
			user.setPass(encoder.encode("password"));
			userService.save(user);
			Role userRole = roleRepository.findByName(roles[0]);
			User_Role user_Role = new User_Role();
			user_Role.setRole(userRole);
			user_Role.setUser(user);
			userRoleRepository.save(user_Role);
		}

		if (userService.findByUsername("kristina") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User();
			user.setEmail("pinksugarpill@gmail.com");
			user.setfName("Kristina");
			user.setlName("Grujic");
			user.setUsername("kristina");
			user.setPass(encoder.encode("password"));
			userService.save(user);
			Role userRole = roleRepository.findByName(roles[1]);
			User_Role user_Role = new User_Role();
			user_Role.setRole(userRole);
			user_Role.setUser(user);
			userRoleRepository.save(user_Role);
		}

		if (userService.findByUsername("aldreman") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User();
			user.setEmail("xml_alderman@mailinator.com");
			user.setfName("Jovan");
			user.setlName("Dujic");
			user.setUsername("alderman");
			user.setPass(encoder.encode("password"));
			userService.save(user);
			Role userRole = roleRepository.findByName(roles[2]);
			User_Role user_Role = new User_Role();
			user_Role.setRole(userRole);
			user_Role.setUser(user);
			userRoleRepository.save(user_Role);
		}

		if (userService.findByUsername("alderuser") == null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User();
			user.setEmail("xml_alderuser@gmail.com");
			user.setfName("Maja");
			user.setlName("Janjic");
			user.setUsername("alderuser");
			user.setPass(encoder.encode("password"));
			userService.save(user);
			Role userRole = roleRepository.findByName(roles[2]);
			User_Role user_Role = new User_Role();
			user_Role.setRole(userRole);
			user_Role.setUser(user);
			userRoleRepository.save(user_Role);
		}

		return new ResponseEntity<String>("Succesfully created user roles", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


		User user = new User();
		User_Role user_role = new User_Role();

		if (userService.findByUsername(userDTO.getUsername()) != null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);


		if (userService.findByEmail(userDTO.getEmail()) != null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);


		// set user credentials
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPass(encoder.encode(userDTO.getPass()));
		user.setfName(userDTO.getfName());
		user.setlName(userDTO.getlName());

		userRepository.save(user);

		Long id = Long.parseLong("2");
		Role role = roleRepository.findByName("ALDERMAN");

		user_role.setRole(role);
		user_role.setUser(user);

		userRoleRepository.save(user_role);

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getProfile(Principal principal) {

		User user = userService.findByUsername(principal.getName());
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.FOUND);
	}

}
