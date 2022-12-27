package com.jwttoken.services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.jwttoken.entity.Student;
import com.jwttoken.repository.StudentRepo;
@Component
public class UserService implements UserDetailsService{

	@Autowired
	private StudentRepo studentRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student user = studentRepo.findStudentByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("user not found!");
		}
		return new UserPrincipal(user);
	}

}
