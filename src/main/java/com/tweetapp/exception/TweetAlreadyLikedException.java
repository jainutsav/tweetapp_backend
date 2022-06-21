package com.tweetapp.exception;

public class TweetAlreadyLikedException extends RuntimeException{

    public TweetAlreadyLikedException(String message){
        super(message);
    }
}
