package com.tweetapp.service.impl;

import com.tweetapp.entity.Tweet;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.service.api.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll().stream().sorted(Comparator.comparing(Tweet::getDate)).collect(Collectors.toList());
    }

    @Override
    public List<Tweet> getUserTweets(String username) {
        return tweetRepository.findAllByUsername(username);
    }

    @Override
    public Tweet newTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public Optional<Tweet> updateTweet(String id, String message) throws TweetNotFoundException {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isPresent()){
            tweet.get().setMessage(message);
            long count = tweetRepository.updateTweet(id, message);
            if(count==1)
            {
                return tweetRepository.findById(id);
            }
        }
        throw new TweetNotFoundException("Tweet not found");
    }

    @Override
    public Long deleteTweet(String id) throws TweetNotFoundException {
        long count  = tweetRepository.deleteTweetById(id);
        if(count ==1 ){
            return count;
        }
        throw new TweetNotFoundException("Tweet not found");
    }

    @Override
    public Long likeTweet(String id) throws TweetNotFoundException {
        Long count = tweetRepository.findAndIncrementLikesById(id);
        if(count==1){
            return count;
        }
        throw new TweetNotFoundException("Tweet not found");
    }
}
