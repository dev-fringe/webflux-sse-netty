package dev.fringe.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.fringe.model.CandleDay;
import dev.fringe.model.CandleMinute;
import dev.fringe.model.CandleMonth;
import dev.fringe.model.CandleWeek;
import dev.fringe.model.Orderbook;
import dev.fringe.model.Ticker;
import dev.fringe.model.TradeTick;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Service
@Log4j2
public class UpbitService {

	@Autowired
	WebClient webClient;

	public List<CandleMinute> candleMinutes(long interval) {
		return webClient.get().uri("https://api.upbit.com/v1/candles/minutes/1?market=KRW-BTC&count=240").retrieve().bodyToFlux(CandleMinute.class).collectList().block();
	}
	public Flux<CandleDay> candleDays(String market) {
		return webClient.get().uri("https://api.upbit.com/v1/candles/days?count=240&market=" + market).retrieve().bodyToFlux(CandleDay.class);
	}
	public List<CandleWeek> candleWeeks() {
		return webClient.get().uri("https://api.upbit.com/v1/candles/weeks?market=KRW-BTC&count=200").retrieve().bodyToFlux(CandleWeek.class).collectList().block();
	}
	public List<CandleMonth> candleMonths() {
		return webClient.get().uri("https://api.upbit.com/v1/candles/months?market=KRW-BTC&count=200").retrieve().bodyToFlux(CandleMonth.class).collectList().block();
	}
	public Flux<TradeTick> tradeTicks(String market) {
		return webClient.get().uri("https://api.upbit.com/v1/trades/ticks?count=1&market="+ market).retrieve().bodyToFlux(TradeTick.class);
	}
	public Flux<Ticker> tickers(String markets) {
		return webClient.get().uri("https://api.upbit.com/v1/ticker?markets=" + markets).retrieve().bodyToFlux(Ticker.class);
	}
	public Flux<Orderbook> orderbooks(String markets) {
		return webClient.get().uri("https://api.upbit.com/v1/orderbook?markets=" + markets).retrieve().bodyToFlux(Orderbook.class);
	}
	public Flux<Ticker> ticker(){
		return Flux.interval(Duration.ofSeconds(1)).flatMap(it -> tickers("KRW-XRP"));
	}
	
	public Flux<Orderbook> orderbook(){
		return Flux.interval(Duration.ofSeconds(1)).flatMap(it -> orderbooks("KRW-XRP"));
	}
	public Flux<TradeTick> tradetick(){
		return Flux.interval(Duration.ofSeconds(1)).flatMap(it -> tradeTicks("KRW-XRP"));
	}
	public Flux<CandleDay> candleday(){
		return Flux.interval(Duration.ofSeconds(10)).onBackpressureDrop().flatMap(it -> candleDays("KRW-XRP"));
	}
}
