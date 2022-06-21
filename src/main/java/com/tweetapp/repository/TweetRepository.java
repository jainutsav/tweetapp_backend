package com.tweetapp.repository;

import com.tweetapp.entity.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface TweetRepository extends MongoRepository<Tweet, String> {

    List<Tweet> findAllByUsername(String username);

    @Query("{'id' : ?0}")
    @Update("{ '$set' : {'message':?1}}")
    long updateTweet(String id, String message);

    Long deleteTweetById(String id);

    @Update("{ '$inc' : { 'likes' : 1 } }")
    Long findAndIncrementLikesById(String id);
}
