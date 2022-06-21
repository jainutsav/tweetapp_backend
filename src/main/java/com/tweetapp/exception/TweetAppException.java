package com.tweetapp.exception;

import org.springframework.http.HttpStatus;

public class TweetAppException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public TweetAppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
