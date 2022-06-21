package com.tweetapp.service.impl;

import com.tweetapp.constants.ApplicationConstants;
import com.tweetapp.entity.Tweet;
import com.tweetapp.exception.TweetAlreadyLikedException;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.model.TweetReply;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.service.api.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public List<Tweet> getAllTweets() {
        log.info("Getting all tweets from Database");
        List<Tweet> tweets =tweetRepository.findAll().stream().sorted(Comparator.comparing(Tweet::getCreatedDate)).collect(Collectors.toList());
        log.info("All tweets has been successfully retrieved");
        return tweets;
    }

    @Override
    public List<Tweet> getUserTweets(String username) {
        log.info("Finding all tweets of user: {}", username);
        List<Tweet> tweets = tweetRepository.findAllByUsername(username);
        log.info("Tweets retrieved for the user: {}", username);
        return tweets;
    }

    @Override
    public Tweet newTweet(Tweet tweet) {
        log.info(" Adding new tweet: {}",tweet);
        Tweet tweet1 = tweetRepository.save(tweet);
        log.info("Tweet has been successfully added.");
        return tweet1;
    }

    @Override
    public Tweet updateTweet(String id, String message)  {
        log.info("Updating the message in the tweet. Message: {}",message);
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isPresent()){
            log.info("Tweet found. Updating message now.");
            tweet.get().setMessage(message);
            long count = tweetRepository.updateTweet(id, message);
            if(count==1)
            {
                log.info("Tweet has been successfully updated.");
                return tweet.get();
            }
        }
        log.error("Error occurred while trying to update the tweet.");
        throw new TweetNotFoundException(ApplicationConstants.TWEET_NOT_FOUND);
    }

    @Override
    public Long deleteTweet(String id) {
        log.info("Deleting tweet with id : {}",id);
        long count  = tweetRepository.deleteTweetById(id);
        if(count ==1 ){
            log.info("Tweet has been successfully deleted.");
            return count;
        }
        log.error("Error occurred while trying to delete the tweet.");
        throw new TweetNotFoundException(ApplicationConstants.TWEET_NOT_FOUND);
    }

    @Override
    public Tweet likeTweet(String username, String id) {
        log.info("Liking a tweet by user: {}",username);
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if(optionalTweet.isPresent()){
            Tweet tweet = optionalTweet.get();
            boolean b = false;
            if(!ObjectUtils.isEmpty(tweet.getLikedByUsernames())) {
                b = tweet.getLikedByUsernames().parallelStream()
                        .anyMatch(usernameDb -> usernameDb.equals(username));
            }
            if(b){
                log.error("Tweet hs been already liked by the user.");
                throw new TweetAlreadyLikedException("You have already liked this tweet");
            }
            log.info("Tweet found and has not been already liked by user. Liking tweet now.");
            tweet.setLikes(tweet.getLikes()+1);
            if(ObjectUtils.isEmpty(tweet.getLikedByUsernames())){
                List<String> userList = new ArrayList<>();
                userList.add(username);
                tweet.setLikedByUsernames(userList);
            }else{
                tweet.getLikedByUsernames().add(username);
            }
            log.info("Tweet has been successfully liked.");
            tweetRepository.save(tweet);
            return tweet;
        }
        log.error("Tweet not exists anymore.");
        throw new TweetNotFoundException(ApplicationConstants.TWEET_NOT_FOUND);
    }

    @Override
    public Tweet replyTweet(String id, TweetReply tweetReply) {
        log.info("Replying to a tweet by user: {}",tweetReply.getUsername());
        Optional<Tweet> tweetDB = tweetRepository.findById(id);
        if(tweetDB.isPresent()){
            log.info("Tweet found. Replying to tweet now.");
            if(ObjectUtils.isEmpty(tweetDB.get().getTweetReplyList())){
                List<TweetReply> tweetReplyList = new ArrayList<>();
                tweetReplyList.add(tweetReply);
                tweetDB.get().setTweetReplyList(tweetReplyList);
            }else {
                tweetDB.get().getTweetReplyList().add(tweetReply);
            }
            Tweet tweet=tweetRepository.save(tweetDB.get());
            log.info("Reply has been successfully done on the tweet.");
            return tweet;
        }
        log.error("Tweet not exists anymore.");
        throw new TweetNotFoundException(ApplicationConstants.TWEET_NOT_FOUND);
    }
}
