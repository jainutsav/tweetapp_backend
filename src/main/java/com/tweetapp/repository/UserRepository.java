package com.tweetapp.repository;

import com.tweetapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByUsername(String username);
    Boolean existsByEmailId(String emailId);
    User findByEmailIdOrUsername(String emailId, String username);
    User findByEmailId(String emailId);
    @Query(value = "{'username': {$regex : ?0, $options: 'i'}}")
    List<User> findByUsernameRegex(String username);

}
