package com.tweetapp.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TweetDTO {

    private String id;

    private String username;

    private String firstName;

    private String message;

    private Date createdDate;

    private Date modifiedDate;

    private int likes;

    private List<String> likedByUsernames;

    private List<TweetReply> tweetReplyList;

}
