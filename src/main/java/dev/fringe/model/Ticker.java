package dev.fringe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "market", "trade_date", "trade_time", "trade_date_kst", "trade_time_kst", "trade_timestamp",
		"opening_price", "high_price", "low_price", "trade_price", "prev_closing_price", "change", "change_price",
		"change_rate", "signed_change_price", "signed_change_rate", "trade_volume", "acc_trade_price",
		"acc_trade_price_24h", "acc_trade_volume", "acc_trade_volume_24h", "highest_52_week_price",
		"highest_52_week_date", "lowest_52_week_price", "lowest_52_week_date", "timestamp" })
@Data
public class Ticker {

	@JsonProperty("market")
	private String market;
	@JsonProperty("trade_date")
	private String tradeDate;
	@JsonProperty("trade_time")
	private String tradeTime;
	@JsonProperty("trade_date_kst")
	private String tradeDateKst;
	@JsonProperty("trade_time_kst")
	private String tradeTimeKst;
	@JsonProperty("trade_timestamp")
	private Long tradeTimestamp;
	@JsonProperty("opening_price")
	private Integer openingPrice;
	@JsonProperty("high_price")
	private Integer highPrice;
	@JsonProperty("low_price")
	private Integer lowPrice;
	@JsonProperty("trade_price")
	private Integer tradePrice;
	@JsonProperty("prev_closing_price")
	private Integer prevClosingPrice;
	@JsonProperty("change")
	private String change;
	@JsonProperty("change_price")
	private Integer changePrice;
	@JsonProperty("change_rate")
	private Double changeRate;
	@JsonProperty("signed_change_price")
	private Integer signedChangePrice;
	@JsonProperty("signed_change_rate")
	private Double signedChangeRate;
	@JsonProperty("trade_volume")
	private Double tradeVolume;
	@JsonProperty("acc_trade_price")
	private Double accTradePrice;
	@JsonProperty("acc_trade_price_24h")
	private Double accTradePrice24h;
	@JsonProperty("acc_trade_volume")
	private Double accTradeVolume;
	@JsonProperty("acc_trade_volume_24h")
	private Double accTradeVolume24h;
	@JsonProperty("highest_52_week_price")
	private Integer highest52WeekPrice;
	@JsonProperty("highest_52_week_date")
	private String highest52WeekDate;
	@JsonProperty("lowest_52_week_price")
	private Integer lowest52WeekPrice;
	@JsonProperty("lowest_52_week_date")
	private String lowest52WeekDate;
	@JsonProperty("timestamp")
	private Long timestamp;
}