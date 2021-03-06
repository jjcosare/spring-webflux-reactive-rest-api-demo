package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.repository.TweetRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
    TweetRepository repository;

	@Test
	public void testCreate() {
	    final String TWEET_TEXT = "This is a Test Tweet";
		Tweet model = new Tweet(TWEET_TEXT);

		webTestClient.post().uri("/tweet")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(model), Tweet.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.text").isEqualTo(TWEET_TEXT);
	}

	@Test
    public void testGetAll() {
	    webTestClient.get().uri("/tweet")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Tweet.class);
    }

    @Test
    public void testGetSingle() {
        Tweet model = repository.save(new Tweet("Hello, World!")).block();

        webTestClient.get()
                .uri("/tweet/{id}", Collections.singletonMap("id", model.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdate() {
        Tweet model = repository.save(new Tweet("Initial Tweet")).block();

        final String TWEET_TEXT_UPDATE = "Updated Tweet";
        Tweet form = new Tweet(TWEET_TEXT_UPDATE);

        webTestClient.put()
                .uri("/tweet/{id}", Collections.singletonMap("id", model.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(form), Tweet.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.text").isEqualTo(TWEET_TEXT_UPDATE);
    }

    @Test
    public void testDelete() {
	    Tweet model = repository.save(new Tweet("To be deleted")).block();

	    webTestClient.delete()
                .uri("/tweet/{id}", Collections.singletonMap("id",  model.getId()))
                .exchange()
                .expectStatus().isOk();
    }

}
