package com.tweetapp.resource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.tweetapp.constants.ApplicationConstants;
import com.tweetapp.entity.Tweet;
import com.tweetapp.entity.User;
import com.tweetapp.model.*;
import com.tweetapp.service.api.TweetService;
import com.tweetapp.service.api.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TweetAppResourceTest {

    @InjectMocks
    TweetAppResource tweetAppResource;

    @Mock
    UserService userService;

    @Mock
    TweetService tweetService;

    @Test
    void newUserRegistrationTest(){
        UserDTO userDTO = new UserDTO();
        when(userService.registerUser(any(User.class))).thenReturn(ApplicationConstants.SUCCESS);
        assertEquals(200,tweetAppResource.newUserRegistration(userDTO).getStatusCodeValue());
    }

    @Test
    void newUserRegistrationTest_BadRequest(){
        UserDTO userDTO = new UserDTO();
        when(userService.registerUser(any(User.class))).thenReturn(ApplicationConstants.ALREADY_EXISTS);
        assertEquals(400,tweetAppResource.newUserRegistration(userDTO).getStatusCodeValue());
    }

    @Test
    void loginTest(){
        LoginDto loginDto = new LoginDto();
        when(userService.login(any(LoginDto.class))).thenReturn(new JWTAuthResponse(anyString()));
        assertEquals(200,tweetAppResource.login(loginDto).getStatusCodeValue());
    }

    @Test
    void resetPasswordTest(){
        UserDTO userDTO = new UserDTO();
        when(userService.resetPassword(any(User.class))).thenReturn(anyString());
        assertEquals(200,tweetAppResource.resetPassword(userDTO).getStatusCodeValue());
    }

    @Test
    void getAllTweetsTest(){
        when(tweetService.getAllTweets()).thenReturn(new ArrayList<>());
        assertEquals(200,tweetAppResource.getAllTweets().getStatusCodeValue());
    }

    @Test
    void getAllUsersTest(){
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        assertEquals(200, tweetAppResource.getAllUsers().getStatusCodeValue());
    }

    @Test
    void searchByUsernameTest(){
        when((userService.findByUsernameRegex(anyString()))).thenReturn(new ArrayList<>());
        assertEquals(200, tweetAppResource.searchByUsername("utsav").getStatusCodeValue());
    }

    @Test
    void getUserTweetsTest(){
        when(tweetService.getUserTweets(anyString())).thenReturn(new ArrayList<>());
        assertEquals(200, tweetAppResource.getUserTweets("utsav").getStatusCodeValue());
    }

    @Test
    void newTweetTest(){
        when(tweetService.newTweet(any(Tweet.class))).thenReturn(any(Tweet.class));
        assertEquals(200,tweetAppResource.newTweet("utsav",new TweetDTO()).getStatusCodeValue());
    }

    @Test
    void updateTweetTest(){
        when(tweetService.updateTweet(anyString(), anyString())).thenReturn(new Tweet());
        assertEquals(200,tweetAppResource.updateTweet("utsav","1","hello").getStatusCodeValue());
    }

    @Test
    void deleteTweetTest(){
        when(tweetService.deleteTweet(anyString())).thenReturn(any(Long.class));
        assertEquals(200,tweetAppResource.deleteTweet("utsav","1").getStatusCodeValue());
    }

    @Test
    void likeTweetTest(){
        when(tweetService.likeTweet(anyString(),anyString())).thenReturn(new Tweet());
        assertEquals(200,tweetAppResource.likeTweet("utsav","1").getStatusCodeValue());
    }

    @Test
    void replyTweetTest(){
        when(tweetService.replyTweet(anyString(),any(TweetReply.class))).thenReturn(new Tweet());
        assertEquals(200, tweetAppResource.replyTweet("utsav","1", new TweetReply()).getStatusCodeValue());
    }
}
