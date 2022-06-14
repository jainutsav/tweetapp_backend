package com.tweetapp.service.api;

import com.tweetapp.entity.User;
import com.tweetapp.model.JWTAuthResponse;
import com.tweetapp.model.LoginDto;

import java.util.List;

public interface UserService {

    String registerUser(User user);

    JWTAuthResponse login(LoginDto user);

    User findByEmail(String emailId) ;

    List<User> getAllUsers();

}
