package com.tweetapp.service.api;

import com.tweetapp.entity.Tweet;
import com.tweetapp.exception.TweetNotFoundException;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface TweetService {

    List<Tweet> getAllTweets();

    List<Tweet> getUserTweets(String username);

    Tweet newTweet(Tweet tweet);

    Optional<Tweet> updateTweet(String id, String message) throws TweetNotFoundException;

    Long deleteTweet(String id) throws TweetNotFoundException;

    Long likeTweet(String id) throws TweetNotFoundException;
}
