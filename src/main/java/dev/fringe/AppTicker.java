package dev.fringe;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;

import dev.fringe.config.WebClientConfig;
import dev.fringe.model.Ticker;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@ComponentScan(basePackages = { "dev.fringe.controller", "dev.fringe.service", "com.mkyong.reactive" })
@Configuration
@Import(WebClientConfig.class)
@Log4j2
public class AppTicker implements InitializingBean {

	@Autowired WebClient webClient;
	
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppTicker.class)){
			while (true) {
			}
		}
	}
	
	public void afterPropertiesSet() {
		ParameterizedTypeReference<ServerSentEvent<Ticker>> type = new ParameterizedTypeReference<ServerSentEvent<Ticker>>() {};
	    Flux<ServerSentEvent<Ticker>> eventStream = webClient.get().uri("http://localhost:8082/ticker/stream").retrieve().bodyToFlux(type);
	    eventStream.subscribe(content -> log.debug("data " + content),error -> log.error("Error receiving SSE: {}", error), () -> log.info("Completed!!!"));
	}

}
