package com.niit.authenticationservice.AuthenticationService.config;

import com.niit.authenticationservice.AuthenticationService.domain.User;
import com.niit.authenticationservice.AuthenticationService.rabbitmq.domain.UserDto;
import com.niit.authenticationservice.AuthenticationService.service.UserService;
import com.niit.authenticationservice.AuthenticationService.service.UserServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private UserServiceImpl userService;

    @RabbitListener(queues = "niit_queue1")
    public void getDataFromRabbitMq(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        System.out.println("Trying to save the data into mysql " +user.getUserId()+" "+user.getUsername()+" "+user.getPassword());
        userService.saveUser(user);
    }

}
