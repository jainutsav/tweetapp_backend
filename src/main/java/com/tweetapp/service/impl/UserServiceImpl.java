package com.tweetapp.service.impl;

import com.tweetapp.constants.ApplicationConstants;
import com.tweetapp.entity.User;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.JWTAuthResponse;
import com.tweetapp.model.LoginDto;
import com.tweetapp.model.SendUser;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.security.JwtTokenProvider;
import com.tweetapp.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
        log.info("Registering new user to the app with username: {}", user.getUsername());
        boolean usernameExists = userRepository.existsByUsername(user.getUsername());
        boolean emailIdExists = userRepository.existsByEmailId(user.getEmailId());
        if(!usernameExists && !emailIdExists){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("User has been successfully registered to the app.");
            return ApplicationConstants.SUCCESS;
        } else {
            if(usernameExists && emailIdExists){
                log.error("Username and emailId"+ApplicationConstants.ALREADY_EXISTS);
                return ApplicationConstants.USERNAME+" and "+ApplicationConstants.EMAILID +ApplicationConstants.ALREADY_EXISTS;
            }
            if(usernameExists){
                log.error("Username"+ApplicationConstants.ALREADY_EXISTS);
                return ApplicationConstants.USERNAME+ApplicationConstants.ALREADY_EXISTS;
            } else{
                log.error("EmailId already exists.");
                return ApplicationConstants.EMAILID+ApplicationConstants.ALREADY_EXISTS;
            }
        }
    }

    @Override
    public JWTAuthResponse login(LoginDto user) {
        log.info("Trying to login the user");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsernameOrEmail(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        log.info("User has been successfully logged in.");
        return new JWTAuthResponse(token);
    }

    @Override
    public User findByEmail(String emailId) {
        log.info("Trying to find the user by emailId : {}",emailId);
        User user = userRepository.findByEmailId(emailId);
        if(user == null) {
            log.error("User does not exists.");
            throw new UserNotFoundException("User Not Found");
        }
        log.info("User has been successfully found.");
        return user;

    }

    @Override
    public String resetPassword(User user) {
        log.info("Reset password for user: {}",user.getUsername());
        User userDB = userRepository.findByEmailIdOrUsername(user.getEmailId(), user.getUsername());
        if(!ObjectUtils.isEmpty(userDB)
            && user.getEmailId().equals(userDB.getEmailId())
            && user.getUsername().equals(userDB.getUsername())
            && user.getContactNumber().equals(userDB.getContactNumber())){
                userDB.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(userDB);
                log.info("Pass word has been successfully changed for user: {}",user.getUsername());
                return ApplicationConstants.SUCCESS;
        }
        log.error("User not exists or details mismatch. Can not reset password.");
        throw new UserNotFoundException("Unable to reset password.");
    }

    @Override
    public List<SendUser> getAllUsers() {
        log.info("Get list of all users.");
        List<User> userList = userRepository.findAll();
        List<SendUser> sendUserList = new ArrayList<>();
        userList.forEach(user -> {
            SendUser sendUser = new SendUser();
            sendUser.setFirstName(user.getFirstName());
            sendUser.setUsername(user.getUsername());
            sendUserList.add(sendUser);
        });
        log.info("User list successfully retrieved.");
        return sendUserList;
    }

    @Override
    public List<SendUser> findByUsernameRegex(String username) {
        log.info("Find all users which contains : {}",username);
        List<User> userList = userRepository.findByUsernameRegex(username);
        List<SendUser> sendUserList = new ArrayList<>();
        userList.forEach(user -> {
            SendUser sendUser = new SendUser();
            sendUser.setFirstName(user.getFirstName());
            sendUser.setUsername(user.getUsername());
            sendUserList.add(sendUser);
        });
        log.info("All users has been successfully retrieved which contains: {}",username);
        return sendUserList;
    }

}
