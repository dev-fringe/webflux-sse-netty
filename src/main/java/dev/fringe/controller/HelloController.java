package dev.fringe.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fringe.model.Ticker;
import dev.fringe.service.UpbitService;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;

@RestController
public class HelloController {

	@RequestMapping("/hello")
	@SneakyThrows
	public String hello() {
		TimeUnit.SECONDS.sleep(1);
		return "hello world";
	}
	
}