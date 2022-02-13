package dev.fringe.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "market", "korean_name", "english_name" })
@Data
public class Market {

	@JsonProperty("market")
	private String market;
	@JsonProperty("korean_name")
	private String koreanName;
	@JsonProperty("english_name")
	private String englishName;
}