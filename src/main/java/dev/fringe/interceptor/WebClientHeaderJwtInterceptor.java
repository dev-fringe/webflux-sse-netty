package dev.fringe.interceptor;

import java.util.UUID;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class WebClientHeaderJwtInterceptor {

	
	private String accessKey;
	private String secretKey;
	
	public WebClientHeaderJwtInterceptor() {
		super();
	}

	public WebClientHeaderJwtInterceptor(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}
	
	public ExchangeFilterFunction logFilter(String accessKey, String secretKey) {
		return (clientRequest, next) -> {
			ClientRequest request = ClientRequest.from(clientRequest).header("Authorization", createJwt())
				.build();
			return next.exchange(request);
		};
	}
	public String createJwt() {
		return "Bearer " + JWT.create().withClaim("access_key", accessKey).withClaim("nonce", UUID.randomUUID().toString()).sign(Algorithm.HMAC256(secretKey));
	}
}
