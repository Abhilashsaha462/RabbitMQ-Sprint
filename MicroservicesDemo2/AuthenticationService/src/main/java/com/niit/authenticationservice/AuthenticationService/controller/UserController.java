package com.niit.authenticationservice.AuthenticationService.controller;

import com.niit.authenticationservice.AuthenticationService.domain.User;
import com.niit.authenticationservice.AuthenticationService.exception.UserNotFoundException;
import com.niit.authenticationservice.AuthenticationService.service.SecurityTokenGenerator;
import com.niit.authenticationservice.AuthenticationService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/")
public class UserController {

    private ResponseEntity responseEntity;
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.userService = userService;
                this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody User user) throws UserNotFoundException {
        System.out.println("");
        Map<String, String> map = null;
        try {
            User userObj = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (userObj.getUsername().equals(user.getUsername())) {
                map = securityTokenGenerator.generateToken(user);
            }
            responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Exception e) {
            responseEntity = new ResponseEntity("Try after sometime !!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/register")
    public ResponseEntity saveUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return responseEntity = new ResponseEntity("User Created", HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/userservice/users")
    public ResponseEntity getAllUsers(HttpServletRequest request) {
        List<User> list = userService.getAllUsers();
        responseEntity = new ResponseEntity(list, HttpStatus.OK);
        return responseEntity;
    }

}
