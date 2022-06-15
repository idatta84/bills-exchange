package com.sample.billsexchange.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("change-details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeConfig {
	private int coinCount;
	private List<Double> availableCoins;

	private List<Integer> allowedBills;

	public void setAvailableCoins(List<Double> availableCoins) {
		this.availableCoins = availableCoins.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}

}
