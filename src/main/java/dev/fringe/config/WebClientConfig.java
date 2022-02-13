package dev.fringe.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Configuration
@Log4j2
public class WebClientConfig {
	
	@Value("${app.access.key:qXmSMObHB9DyMmab2HgZxwiJvKxsWmQcTn28mxBW}")
	private String accessKey;
	@Value("${app.secret.key:BlBtxhQ96UZWgx5WXvTIZvBcV7vPd1kClDEYatKF}")
	private String secretKey;

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
