package dev.fringe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "ask_price", "bid_price", "ask_size", "bid_size" })
@Data
public class OrderbookUnit {

	@JsonProperty("ask_price")
	private Integer askPrice;
	@JsonProperty("bid_price")
	private Integer bidPrice;
	@JsonProperty("ask_size")
	private Double askSize;
	@JsonProperty("bid_size")
	private Double bidSize;
}