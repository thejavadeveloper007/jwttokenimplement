package com.jwttoken.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwttoken.dto.AuthRequest;
import com.jwttoken.dto.AuthResponse;
import com.jwttoken.dto.StudentDto;
import com.jwttoken.entity.Student;
import com.jwttoken.repository.StudentRepo;
import com.jwttoken.services.UserService;
import com.jwttoken.utility.JwtUtil;

@RestController
@RequestMapping("/cnt")
public class StudentController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private StudentRepo studentRepo;
	
//	@Autowired
//	private AuthenticationManager authManager;
	
	@Autowired
	private AuthenticationProvider authProvider;
	
	@GetMapping("/t")
	public String wlcm() {
		System.out.print("hello");
		return "welcome Student!!";
	}
	
	@PostMapping("/auth")
	public AuthResponse authentication(@RequestBody AuthRequest authRequest) throws Exception {
		
		
		System.out.print("hello");
		try {
			authProvider.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		} catch (Exception e) {
			throw new Exception("invalid username/password!!");
		}
		
		
		
		UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		return new AuthResponse(token);
	}
	
	@PostMapping("/register")
	public Student registerStudent(@RequestBody StudentDto studentDto) {
		Student student = new Student();
		student.setUsername(studentDto.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(studentDto.getPassword());
		student.setPassword(encodePassword);
		student.setEmail(studentDto.getEmail());
		
		return studentRepo.save(student);
	}
}
