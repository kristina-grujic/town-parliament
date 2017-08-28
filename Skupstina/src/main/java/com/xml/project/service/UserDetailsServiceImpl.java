package com.xml.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xml.project.model.User;
import com.xml.project.model.User_Role;
import com.xml.project.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {

			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			User_Role ua = user.getUser_role();	
			grantedAuthorities.add(new SimpleGrantedAuthority(ua.getRole().getName()));

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPass(),
					grantedAuthorities);
		}
	}

}
