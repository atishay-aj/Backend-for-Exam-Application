package com.exam.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.payloads.ApiResponse;
import com.exam.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<ApiResponse> createUser(@RequestBody User user) throws Exception {

		Set<UserRole> userRoles = new HashSet<>();

		Role role = new Role(45L, "NORMAL");
		userRoles.add(new UserRole(user, role));
		this.userService.createUser(user, userRoles);

		return  ResponseEntity.ok(new ApiResponse("User Created Succesfully", true));

	}
	@GetMapping("/{username}")
	public User getUser(@PathVariable String username) {
		return this.userService.getUser(username);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
	}
}
