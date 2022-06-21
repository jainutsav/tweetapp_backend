package com.tweetapp.service.api;

import com.tweetapp.entity.Tweet;
import com.tweetapp.model.TweetReply;

import java.util.List;

public interface TweetService {

    List<Tweet> getAllTweets();

    List<Tweet> getUserTweets(String username);

    Tweet newTweet(Tweet tweet);

    Tweet updateTweet(String id, String message);

    Long deleteTweet(String id);

    Tweet likeTweet(String username, String id);

    Tweet replyTweet(String id, TweetReply tweetReply);
}
