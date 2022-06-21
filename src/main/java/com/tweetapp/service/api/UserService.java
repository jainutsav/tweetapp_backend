package com.tweetapp.service.api;

import com.tweetapp.entity.User;
import com.tweetapp.model.JWTAuthResponse;
import com.tweetapp.model.LoginDto;
import com.tweetapp.model.SendUser;

import java.util.List;

public interface UserService {

    String registerUser(User user);

    JWTAuthResponse login(LoginDto user);

    User findByEmail(String emailId) ;

    String resetPassword(User user);

    List<SendUser> getAllUsers();

    List<SendUser> findByUsernameRegex(String username);

}
