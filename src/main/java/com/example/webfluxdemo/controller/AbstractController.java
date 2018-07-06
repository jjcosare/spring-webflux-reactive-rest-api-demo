package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.service.AbstractService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by jjcosare on 7/5/18.
 */
public abstract class AbstractController<S extends AbstractService, M, ID extends Serializable> {

    private S service;

    public AbstractController(S service){
        this.service = service;
    }

    @GetMapping("")
    public Flux<Tweet> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Mono<Tweet> create(@Valid @RequestBody M model) {
        return service.save(model);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<M>> getById(@PathVariable ID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<M>> update(@PathVariable ID id,
                                              @Valid @RequestBody M model) {
        return service.update(id, model);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable ID id) {
        return service.delete(id);
    }

    // Tweets are Sent to the client as Server Sent Events
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> streamAll() {
        return service.getAll();
    }

}
