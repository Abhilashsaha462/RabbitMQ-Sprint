package com.niit.UserMovieService.rabbitmq.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String username;
    private String password;

}
