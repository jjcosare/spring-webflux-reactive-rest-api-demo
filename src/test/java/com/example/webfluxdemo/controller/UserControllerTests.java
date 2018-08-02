package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.repository.TweetRepository;
import com.example.webfluxdemo.repository.UserRepository;
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
public class UserControllerTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
    UserRepository repository;

	@Test
	public void testCreate() {
        final String EMAIL = "firstname1@company.com";
		User model = new User(EMAIL);

		webTestClient.post().uri("/user")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(model), User.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.email").isEqualTo(EMAIL);
	}

	@Test
    public void testGetAll() {
	    webTestClient.get().uri("/user")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Tweet.class);
    }

    @Test
    public void testGetSingle() {
        User model = repository.save(new User("firstname2@company.com")).block();

        webTestClient.get()
                .uri("/user/{id}", Collections.singletonMap("id", model.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdate() {
        User model = repository.save(new User("firstname3_1@company.com")).block();

        final String EMAIL_UPDATED = "firstname3_2@company.com";
        User form = new User(EMAIL_UPDATED);

        webTestClient.put()
                .uri("/user/{id}", Collections.singletonMap("id", model.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(form), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.email").isEqualTo(EMAIL_UPDATED);
    }

    @Test
    public void testDelete() {
	    User model = repository.save(new User("firstname4@company.com")).block();

	    webTestClient.delete()
                .uri("/user/{id}", Collections.singletonMap("id",  model.getId()))
                .exchange()
                .expectStatus().isOk();
    }
}
