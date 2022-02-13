package com.mkyong.reactive.controller;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkyong.reactive.model.Comment;
import com.mkyong.reactive.repository.CommentRepository;

import dev.fringe.model.CandleDay;
import dev.fringe.model.Orderbook;
import dev.fringe.model.Ticker;
import dev.fringe.model.TradeTick;
import dev.fringe.service.UpbitService;
import reactor.core.publisher.Flux;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(sequence -> "Flux - " + LocalTime.now().toString());
    }
    
    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(sequence -> ServerSentEvent.<String> builder()
            .id(String.valueOf(sequence))
              .event("periodic-event")
              .data("SSE - " + LocalTime.now().toString())
              .build());
    }
    
    
    @GetMapping(path = "/comment/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Comment> feed() {
        return this.commentRepository.findAll();
    }
    
    
	@Autowired UpbitService upbitService;
	
    @GetMapping(path = "/ticker/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Ticker> ticker() {
        return upbitService.ticker();
    }

    @GetMapping(path = "/orderbook/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Orderbook> orderbook() {
        return upbitService.orderbook();
    }
    
    @GetMapping(path = "/tradetick/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TradeTick> tradetick() {
        return upbitService.tradetick();
    }
    
    @GetMapping(path = "/candleday/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CandleDay> candleday() {
        return upbitService.candleday();
    }    
}