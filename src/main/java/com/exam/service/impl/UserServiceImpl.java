package com.exam.service.impl;

import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception{
		
		
		User localUser=this.userRepository.findByUsername(user.getUsername());
		if(localUser!=null) {
			System.out.println("User is already there");
			throw new EntityExistsException("User is already present");
			
		}else {
			for(UserRole ur:userRoles) {
				this.roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			localUser = this.userRepository.save(user);
		}
	
		return localUser;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findByUsername(username);
		return user;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		 this.userRepository.deleteById(id);
		
	}

}
