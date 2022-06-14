package com.tweetapp.resource;

import com.tweetapp.constants.ApplicationConstants;
import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.model.JWTAuthResponse;
import com.tweetapp.model.LoginDto;
import com.tweetapp.service.api.TweetService;
import com.tweetapp.service.api.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.inject.Singleton;
import javax.ws.rs.*;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetAppResource {

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @ApiOperation(value = "Register new user in the tweet app", notes = "This methods tries to register a new user in the app. " +
            "If user emailId/username already exists, user is notified to try with new credentials.")
    @PostMapping("/register")
    public ResponseEntity<?> newUserRegistration(@RequestBody User user){
        String response = userService.registerUser(user);
        if(response.equals(ApplicationConstants.SUCCESS)) {
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.login(loginDto));
    }

    /*@GET
    @Path("/all")
    public ResponseEntity<?> getAllTweets(){
        return ResponseEntity.ok(tweetService.getAllTweets());
    }*/

    @GetMapping("/all")
    public String getAllTweets(){
        return "success";
    }

    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

/*
    @GET
    @Path("user/search/{username}")
    public ResponseEntity<?> searchByUsername(@PathParam(ApplicationConstants.USERNAME) String username){

    }
*/

    @GetMapping("/{username}")
    public  ResponseEntity<?> getUserTweets(@PathParam(ApplicationConstants.USERNAME) String username){
        return ResponseEntity.ok(tweetService.getUserTweets(username));
    }

    @GetMapping("/{username}/add")
    public ResponseEntity<?> newTweet(@PathParam(ApplicationConstants.USERNAME) String username, @RequestBody Tweet tweet){
        return ResponseEntity.ok(tweetService.newTweet(tweet));
    }

    @GetMapping("/{username}/update/{id}")
    public Tweet updateTweet(@PathParam(ApplicationConstants.USERNAME) String username,
                                         @PathParam(ApplicationConstants.ID) String id,@RequestBody String message) throws TweetNotFoundException {
        return tweetService.updateTweet(id,message).get();
    }

    @GetMapping("/{username}/delete/{id}")
    public Long deleteTweet(@PathParam(ApplicationConstants.USERNAME) String username,
                             @PathParam(ApplicationConstants.ID) String id) throws TweetNotFoundException {
        return tweetService.deleteTweet(id);
    }

    @GetMapping("/{username}/like/{id}")
    public Long likeTweet(@PathParam(ApplicationConstants.USERNAME) String username,
                             @PathParam(ApplicationConstants.ID) String id) throws TweetNotFoundException {
        return tweetService.likeTweet(id);
    }

}
