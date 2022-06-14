package com.tweetapp.service.impl;

import com.tweetapp.constants.ApplicationConstants;
import com.tweetapp.entity.User;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.JWTAuthResponse;
import com.tweetapp.model.LoginDto;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.security.JwtTokenProvider;
import com.tweetapp.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public String registerUser(User user) {
        boolean usernameExists = userRepository.existsByUsername(user.getUsername());
        boolean emailIdExists = userRepository.existsByEmailId(user.getEmailId());
        if(!usernameExists && !emailIdExists){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ApplicationConstants.SUCCESS;
        } else {
            if(usernameExists && emailIdExists){
                return ApplicationConstants.USERNAME+" and "+ApplicationConstants.EMAILID + " already exists.";
            }
            if(usernameExists){
                return ApplicationConstants.USERNAME+ " already exists.";
            } else{
                return ApplicationConstants.EMAILID+ " already exists.";
            }
        }
    }

    @Override
    public JWTAuthResponse login(LoginDto user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsernameOrEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return new JWTAuthResponse(token);
    }

    @Override
    public User findByEmail(String emailId) {
        User user = userRepository.findByEmailId(emailId);
        if(user == null)
            throw new UserNotFoundException("Resource Not Found");
        return user;

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
