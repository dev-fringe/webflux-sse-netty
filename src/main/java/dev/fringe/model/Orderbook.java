package dev.fringe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "market", "timestamp", "total_ask_size", "total_bid_size", "orderbook_units" })
@Data
public class Orderbook {

	@JsonProperty("market")
	private String market;
	@JsonProperty("timestamp")
	private Long timestamp;
	@JsonProperty("total_ask_size")
	private Double totalAskSize;
	@JsonProperty("total_bid_size")
	private Double totalBidSize;
	@JsonProperty("orderbook_units")
	private List<OrderbookUnit> orderbookUnits = null;
}