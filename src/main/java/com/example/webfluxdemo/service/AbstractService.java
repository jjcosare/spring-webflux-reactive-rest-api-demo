package com.example.webfluxdemo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Created by jjcosare on 7/5/18.
 */
public abstract class AbstractService<R extends ReactiveMongoRepository, M, ID extends Serializable> {

    private R repository;

    public AbstractService(R repository){
        this.repository = repository;
    }

    public Flux<M> getAll() {
        return repository.findAll();
    }

    public Mono<M> save(M model) {
        return repository.save(model);
    }

    public Mono<ResponseEntity<M>> getById(ID id) {
        return repository.findById(id)
                .map(dbModel -> ResponseEntity.ok(dbModel))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    public Mono<ResponseEntity<M>> update(ID id, M model) {
        return repository.findById(id)
                .flatMap(dbModel -> {
                    BeanUtils.copyProperties(model, dbModel, "id", "createdAt");
                    return repository.save(dbModel);
                })
                .map(updatedModel -> new ResponseEntity<>(updatedModel, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Void>> delete(ID id) {
        return repository.findById(id)
                .flatMap(dbModel ->
                        repository.delete(dbModel)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
