package com.mkyong.reactive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String index(final Model model) {
		return "index";
	}

	@GetMapping("/ticker")
	public String ticker(final Model model) {
		return "ticker";
	}

	@GetMapping("/orderbook")
	public String orderbook(final Model model) {
		return "orderbook";
	}

	@GetMapping("/tradetick")
	public String tradetick(final Model model) {
		return "tradetick";
	}
	
	@GetMapping("/candleday")
	public String candleday(final Model model) {
		return "candleday";
	}
	
}
