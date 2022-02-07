package dev.fringe.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;

@RestController
public class HelloController {

	@RequestMapping("/hello")
	@SneakyThrows
	public String hello() {
		TimeUnit.SECONDS.sleep(1);
		return "hello world";
	}

}