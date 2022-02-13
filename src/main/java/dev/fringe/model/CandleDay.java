package dev.fringe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "market", "candle_date_time_utc", "candle_date_time_kst", "opening_price", "high_price",
		"low_price", "trade_price", "timestamp", "candle_acc_trade_price", "candle_acc_trade_volume",
		"prev_closing_price", "change_price", "change_rate" })
@Data
public class CandleDay {
	@JsonProperty("market")
	private String market;
	@JsonProperty("candle_date_time_utc")
	private String candleDateTimeUtc;
	@JsonProperty("candle_date_time_kst")
	private String candleDateTimeKst;
	@JsonProperty("opening_price")
	private Integer openingPrice;
	@JsonProperty("high_price")
	private Integer highPrice;
	@JsonProperty("low_price")
	private Integer lowPrice;
	@JsonProperty("trade_price")
	private Integer tradePrice;
	@JsonProperty("timestamp")
	private Long timestamp;
	@JsonProperty("candle_acc_trade_price")
	private Double candleAccTradePrice;
	@JsonProperty("candle_acc_trade_volume")
	private Double candleAccTradeVolume;
	@JsonProperty("prev_closing_price")
	private Integer prevClosingPrice;
	@JsonProperty("change_price")
	private Integer changePrice;
	@JsonProperty("change_rate")
	private Double changeRate;
}