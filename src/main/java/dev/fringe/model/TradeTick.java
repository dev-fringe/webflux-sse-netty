package dev.fringe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "market", "trade_date_utc", "trade_time_utc", "timestamp", "trade_price", "trade_volume",
		"prev_closing_price", "change_price", "ask_bid", "sequential_id" })
@Data
public class TradeTick {

	@JsonProperty("market")
	private String market;
	@JsonProperty("trade_date_utc")
	private String tradeDateUtc;
	@JsonProperty("trade_time_utc")
	private String tradeTimeUtc;
	@JsonProperty("timestamp")
	private Long timestamp;
	@JsonProperty("trade_price")
	private Integer tradePrice;
	@JsonProperty("trade_volume")
	private Double tradeVolume;
	@JsonProperty("prev_closing_price")
	private Integer prevClosingPrice;
	@JsonProperty("change_price")
	private Integer changePrice;
	@JsonProperty("ask_bid")
	private String askBid;
	@JsonProperty("sequential_id")
	private Long sequentialId;
}