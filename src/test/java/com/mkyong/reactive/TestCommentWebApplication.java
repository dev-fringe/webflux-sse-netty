package com.mkyong.reactive;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mkyong.reactive.model.Comment;

// junit 4
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient(timeout = "10000")//10 seconds
public class TestCommentWebApplication {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testCommentStream() {

        List<Comment> comments = webClient
                .get().uri("/comment/stream")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                //.expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON) // caused timeout
                .returnResult(Comment.class)
                .getResponseBody()
                .take(3)
                .collectList()
                .block();

        comments.forEach(x -> System.out.println(x));


    }

}
