package com.niit.UserMovieService.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
    @Id
    private String email;
    private String username;
    private String phoneNumber;
    private int userId;
    private String password;
    private List<Movie> movieList;

}

