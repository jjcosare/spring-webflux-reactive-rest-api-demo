package com.example.webfluxdemo.service;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jjcosare on 7/5/18.
 */
@Service
public class TweetService extends AbstractService<TweetRepository, Tweet, String> {

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        super(tweetRepository);
    }

}
