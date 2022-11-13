package com.niit.authenticationservice.AuthenticationService.service;

import com.niit.authenticationservice.AuthenticationService.domain.User;
import com.niit.authenticationservice.AuthenticationService.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User findByUsernameAndPassword(String username,String password) throws UserNotFoundException;

    List<User> getAllUsers();
}
