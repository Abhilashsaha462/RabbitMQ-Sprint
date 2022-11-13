package com.niit.UserMovieService.service;


import com.niit.UserMovieService.config.Producer;
import com.niit.UserMovieService.domain.Movie;
import com.niit.UserMovieService.domain.User;
import com.niit.UserMovieService.exception.UserAlreadyExistsException;
import com.niit.UserMovieService.exception.UserNotFoundException;
import com.niit.UserMovieService.rabbitmq.domain.UserDto;
import com.niit.UserMovieService.repository.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserMovieServiceImpl implements UserMovieService{

    private UserMovieRepository userMovieRepository;

    private Producer producer;

    @Autowired
    public UserMovieServiceImpl(UserMovieRepository userMovieRepository, Producer producer)
    {
       this.userMovieRepository = userMovieRepository;
       this.producer = producer;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        if (userMovieRepository.findById(user.getEmail()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }
        else {
            userMovieRepository.save(user);
            System.out.println("Trying to save the data into mongo " +userDto.getUserId()+" "+userDto.getUsername()+" "+userDto.getPassword());
            System.out.println("Data is going to save into rabbitmq server");
            producer.sendMessageToRabbitMqServer(userDto);
        }
        return user;
    }

    @Override
    public User saveUserMovieToList(Movie movie, String email) throws UserNotFoundException {
        if (userMovieRepository.findById(email).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userMovieRepository.findByEmail(email);
        if (user.getMovieList() == null)
        {
            user.setMovieList(Arrays.asList(movie));
        }
        else
        {
            List<Movie> movies = user.getMovieList();
            movies.add(movie);
            user.setMovieList(movies);
        }
        return userMovieRepository.save(user);
    }
}
