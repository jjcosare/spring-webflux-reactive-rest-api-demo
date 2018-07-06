package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jjcosare on 7/5/18.
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
