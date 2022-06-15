package com.sample.billsexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin {
	private double value;
	private int count;

	@Override
	public String toString() {
		return "Coin{" +
				"value=" + value +
				", count='" + count + '\'' +
				'}';
	}
}
