package com.tweetapp.entity;

import com.tweetapp.model.TweetReply;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "tweet")
@Data
public class Tweet {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String message;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;

    private int likes;

    private List<String> likedByUsernames;

    private List<TweetReply> tweetReplyList;
}
