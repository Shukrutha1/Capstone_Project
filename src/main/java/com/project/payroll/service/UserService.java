package com.project.payroll.service;

import com.project.payroll.dto.CreateUserRequest;
import com.project.payroll.dto.UserDTO;
import com.project.payroll.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(CreateUserRequest userRequest);
    User getCurrentUser(String string);
    Optional<User> getUserById(Long id);
    User updateUserStatus(Long id, boolean active);
	List<User> getUsers(String name);
}
