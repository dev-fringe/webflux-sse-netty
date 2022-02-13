package dev.fringe;

import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mkyong.reactive.model.Comment;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ComponentScan(basePackages = { "dev.fringe.controller", "com.mkyong.reactive" })
@Configuration
@Log4j2
public class AppApiTest implements InitializingBean {

	@Value("${app.access.key:qXmSMObHB9DyMmab2HgZxwiJvKxsWmQcTn28mxBW}")
	private String accessKey;
	@Value("${app.secret.key:BlBtxhQ96UZWgx5WXvTIZvBcV7vPd1kClDEYatKF}")
	private String secretKey;
	
	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppApiTest.class)){}
	}

	@Autowired WebClient webClient;
	@SneakyThrows
	public void afterPropertiesSet() {
		log.info("server started");
		System.out.println(webClient);
		
	    WebClient client = WebClient.create("http://localhost:8082/");
	    ParameterizedTypeReference<ServerSentEvent<String>> type
	     = new ParameterizedTypeReference<ServerSentEvent<String>>() {};

	    Flux<ServerSentEvent<String>> eventStream = webClient.get()
	      .uri("/stream-sse")
	      .retrieve()
	      .bodyToFlux(type);

//	    eventStream.subscribe(
//	      content -> log.info("Time: {} - event: name[{}], id [{}], content[{}] ",
//	        LocalTime.now(), content.event(), content.id(), content.data()),
//	      error -> log.error("Error receiving SSE: {}", error),
//	      () -> log.info("Completed!!!"));
	    
		System.out.println(webClient.post().uri("http://localhost:8082/hello").retrieve().bodyToMono(String.class).block());
		ParameterizedTypeReference<ServerSentEvent<Comment>> type2
	     = new ParameterizedTypeReference<ServerSentEvent<Comment>>() {};
	     
	     Flux<ServerSentEvent<Comment>> eventStream2 = webClient.get().uri("http://localhost:8082/comment/stream").retrieve().bodyToFlux(type2);
	     eventStream2.subscribe(
	    	      content -> log.debug("data " + content),
	    	      error -> log.error("Error receiving SSE: {}", error),
	    	      () -> log.info("Completed!!!"));
	}

	@Bean
	public WebClient webClient() {
		WebClient webClient = WebClient.builder().filters(
				exchangeFilterFunctions -> {
				      exchangeFilterFunctions.add(logRequest());
				      exchangeFilterFunctions.add(addAuthHeaderFilterFunction(accessKey, secretKey));
				  }
				).build();
		return webClient;
	}

	public ExchangeFilterFunction addAuthHeaderFilterFunction(String accessKey, String secretKey) {
		return (clientRequest, next) -> {
			ClientRequest request = ClientRequest.from(clientRequest)
					.header("Authorization", createJwt(accessKey, secretKey)).build();
			return next.exchange(request);
		};
	}

	public String createJwt(String accessKey, String secretKey) {
		return "Bearer " + JWT.create().withClaim("access_key", accessKey)
				.withClaim("nonce", UUID.randomUUID().toString()).sign(Algorithm.HMAC256(secretKey));
	}
	public static ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(request -> {
			logMethodAndUrl(request);
			logHeaders(request);
			
			return Mono.just(request);
		});
	}
	private static void logHeaders(ClientRequest request) {
		request.headers().forEach((name, values) -> {
			values.forEach(value -> {
				logNameAndValuePair(name, value);
			});
		});
	}
	
	
	private static void logNameAndValuePair(String name, String value) {
		log.debug("{}={}", name, value);
	}
	
	
	private static void logMethodAndUrl(ClientRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.method().name());
		sb.append(" to ");
		sb.append(request.url());
		
		log.debug(sb.toString());
	}
}
