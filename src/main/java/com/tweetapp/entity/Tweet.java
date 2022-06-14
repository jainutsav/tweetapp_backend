package com.tweetapp.entity;

import com.tweetapp.model.TweetReply;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tweet")
@Data
public class Tweet {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String message;

    private String date;

    private int likes;

    private List<TweetReply> tweetReplyList;
}
