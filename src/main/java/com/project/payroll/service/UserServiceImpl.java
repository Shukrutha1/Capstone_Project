package com.project.payroll.service;

import com.project.payroll.dto.CreateUserRequest;
import com.project.payroll.dto.UserDTO;
import com.project.payroll.entity.User;
import com.project.payroll.exception.UserNotFoundException;
import com.project.payroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(CreateUserRequest userRequest) {
    	User user = new User();
    	user.setUsername(userRequest.getUsername());
    	user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(String name) {
        // Implement logic to fetch current user
        return userRepository.findByUsername(name)
        		.orElseThrow(() -> new UserNotFoundException("User not found for the given name : "+ name));
        
        
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUserStatus(Long id, boolean active) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Add an 'active' field to User entity if not present
            // user.setActive(active);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

	@Override
	public List<User> getUsers(String name) {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
}
