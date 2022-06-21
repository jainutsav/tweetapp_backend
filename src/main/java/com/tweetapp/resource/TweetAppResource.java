package com.tweetapp.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.constants.ApplicationConstants;
import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.exception.TweetAlreadyLikedException;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.*;
import com.tweetapp.service.api.TweetService;
import com.tweetapp.service.api.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetAppResource {

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    private static final Logger log = LogManager.getLogger(TweetAppResource.class);

    @PostMapping("/register")
    public ResponseEntity<Object> newUserRegistration(@RequestBody UserDTO userDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        try{
            user= objectMapper.readValue(objectMapper.writeValueAsString(userDTO),User.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(ApplicationConstants.PARSE_OBJECT_ERROR, HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/forgot")
    public  ResponseEntity<Object> resetPassword(@RequestBody UserDTO userDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        try{
            user= objectMapper.readValue(objectMapper.writeValueAsString(userDTO),User.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(ApplicationConstants.PARSE_OBJECT_ERROR, HttpStatus.BAD_REQUEST);
        }
        try{
            return ResponseEntity.ok(userService.resetPassword(user));
        } catch (UserNotFoundException e){
            return new ResponseEntity<>(ApplicationConstants.ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTweets(){
        log.info("Getting all tweets");
        List<Tweet> tweets = tweetService.getAllTweets();
        log.info("Tweets retrieved. Sending back to user.");
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/users/all")
    public ResponseEntity<Object> getAllUsers(){
        log.info("Getting all users.");
        List<SendUser> users = userService.getAllUsers();
        log.info("All users retrieved successfully");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/search/{username}")
    public ResponseEntity<Object> searchByUsername(@PathVariable(ApplicationConstants.USERNAME) String username){
        log.info("Searching user by username : {}",username);
        List<SendUser> user = userService.findByUsernameRegex(username);
        log.info("User list retrieved successfully for username: {}",username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    public  ResponseEntity<Object> getUserTweets(@PathVariable(ApplicationConstants.USERNAME) String username){
        log.info("Getting tweets based on username: {}",username);
        List<Tweet> tweets = tweetService.getUserTweets(username);
        log.info("Tweets retrieved successfully for username: {}",username);
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/{username}/add")
    public ResponseEntity<Object> newTweet(@PathVariable(ApplicationConstants.USERNAME) String username, @RequestBody TweetDTO tweetDTO){
        log.info("Adding new tweet for user: {}",username);
        ObjectMapper objectMapper = new ObjectMapper();
        Tweet tweet;
        try{
            tweet= objectMapper.readValue(objectMapper.writeValueAsString(tweetDTO),Tweet.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(ApplicationConstants.PARSE_OBJECT_ERROR, HttpStatus.BAD_REQUEST);
        }

        Tweet tweet1 = tweetService.newTweet(tweet);
        log.info("Tweet has been added successfully for user : {}",username);
        return ResponseEntity.ok(tweet1);
    }

    @GetMapping("/{username}/update/{id}")
    public ResponseEntity<Object> updateTweet(@PathVariable(ApplicationConstants.USERNAME) String username,
                                         @PathVariable(ApplicationConstants.ID) String id,@RequestBody String message) throws TweetNotFoundException {
        try{
            log.info("Update tweet for username: {}",username);
            Tweet tweet = tweetService.updateTweet(id,message);
            log.info("Tweet has been successfully updated for user: {}",username);
            return ResponseEntity.ok(tweet);
        }catch (TweetNotFoundException e){
            log.error("Error occurred while trying to update the tweet for user: {}",username,e);
            return new ResponseEntity<>(ApplicationConstants.ERROR,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}/delete/{id}")
    public ResponseEntity<Object> deleteTweet(@PathVariable(ApplicationConstants.USERNAME) String username,
                             @PathVariable(ApplicationConstants.ID) String id) throws TweetNotFoundException {
        try{
            log.info("Deleting tweet for user: {}",username);
            long deleted = tweetService.deleteTweet(id);
            log.info("Tweet has been successfully deleted for user: {}",username);
            return ResponseEntity.ok(deleted);
        }catch (TweetNotFoundException e){
            log.error("Tweet not found which user : {} : trying to delete",username,e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{username}/like/{id}")
    public ResponseEntity<Object> likeTweet(@PathVariable(ApplicationConstants.USERNAME) String username,
                             @PathVariable(ApplicationConstants.ID) String id){
        try{
            log.info("Like a tweet by user: {}",username);
            Tweet tweet = tweetService.likeTweet(username,id);
            log.info("Tweet has been successfully liked by user: {}",username);
            return ResponseEntity.ok(tweet);
        }catch (TweetAlreadyLikedException | TweetNotFoundException e){
            log.error("Exception occurred while trying to like the tweet by user : {}", username,e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{username}/reply/{id}")
    public ResponseEntity<Object> replyTweet(@PathVariable(ApplicationConstants.USERNAME) String username,
                                        @PathVariable(ApplicationConstants.ID) String id,
                                        @RequestBody TweetReply reply){
        try{
            log.info("Reply to a tweet by user: {}", username);
            Tweet tweet = tweetService.replyTweet(id,reply);
            log.info("Reply has been successfully by user: {}",username);
            return ResponseEntity.ok(tweet);
        }catch (TweetNotFoundException e){
            log.error("Error occurred while trying to reply to tweet by user: {}",username,e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
