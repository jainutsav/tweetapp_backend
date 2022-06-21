# Tweet App - Backend

Overview

This project is based on developing an app like Twitter. The main idea behind this project is to gain more knowledge about the understanding of Java, React & AWS. This project is currently consist of only backend which is developed in Java Spring Boot.

Features:

1. Users can sign up and login.
2. Users can create a tweet.
3. Users can delete a tweet.
4. Users can like a tweet.
5. Users can reply to a tweet.
6. Users can reset their password.
7. Users can see all other users who have signed up on the app.
8. Users can see tweets of a particular user.
9. Users can search a user by username (will provide a list of all users who contains that username)

Note: Only users who are logged in can use the application.

API Endpoints:

| Request Type | Endpoint                                | Images                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|--------------|-----------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST         | /api/v1.0/tweets/register               | [User successfully Registered](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/User%20Successfully%20Registered.png) <br/>[Email id already exists](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/email%20id%20already%20exists.png)<br/> [Username already exists](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/username%20already%20exists.png)<br/>[Username and Email Id already exists](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/username%20and%20email%20id%20already%20exists.png)                                                                                                                                                                                                      |
| GET          | /api/v1.0/tweets/login                  | [Login successful with Email](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Login%20successful%20with%20Email.png)<br/>[Login successful with Username](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Login%20successful%20with%20Username.png)  <br/>  [Login Exception](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Login%20Exception.png)                                                                                                                                                                                                                                                                                                                                                                                        |
| GET          | /api/v1.0/tweets/{username}/forgot      |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| GET          | /api/v1.0/tweets/all                    | [Get All Tweets](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Get%20All%20Tweets.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| GET          | /api/v1.0/tweets/users/all              | [All users](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/All%20users.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| GET          | /api/v1.0/tweets/{username}             | [Get User Tweets](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Get%20User%20Tweets.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| POST         | /api/v1.0/tweets/{username}/add         | [New Tweet](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/New%20Tweet.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| PUT          | /api/v1.0/tweets/{username}/update/{id} | [Updating Tweet](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Updating%20Tweet.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| DELETE       | /api/v1.0/tweets/{username}/delete/{id} | [Delete Tweet](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Delete%20Tweet.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| PUT          | /api/v1.0/tweets/{username}/like/{id}   | [Like Tweet](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Like%20Tweet.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     <br/>[Like Tweet Exception](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Like%20Tweet%20Exception.png) |
| POST         | /api/v1.0/tweets/{username}/reply/{id}  | [Tweet Reply](https://github.com/jainutsav/tweetapp_backend/blob/master/images/API%20endpoints/Tweet%20Reply.png)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |

# ELK

##### Images link:
1. [Elastic Search](https://github.com/jainutsav/tweetapp_backend/blob/master/images/ELK/Elastic%20Search.png)
2. [Logstash](https://github.com/jainutsav/tweetapp_backend/blob/master/images/ELK/Logstash.png)
3. [Kibana](https://github.com/jainutsav/tweetapp_backend/blob/master/images/ELK/Kibana.png) 

# Prometheus & Grafana

##### Images link:
1. [Prometheus](https://github.com/jainutsav/tweetapp_backend/blob/master/images/Prometheus%20%26%20Grafana/Prometheus.png)
2. [Grafana](https://github.com/jainutsav/tweetapp_backend/blob/master/images/Prometheus%20%26%20Grafana/Grafana.png)

# Swagger

Image link: 
1. [API Endpoints](https://github.com/jainutsav/tweetapp_backend/blob/master/images/Swagger/Swagger%20-%20API%20endpoints.png)
2. [Models](https://github.com/jainutsav/tweetapp_backend/blob/master/images/Swagger/Swagger%20-%20Models.png)

# Sonarqube

Image link: [Sonarqube](https://github.com/jainutsav/tweetapp_backend/blob/master/images/Sonarqube/Sonarqube.png)











