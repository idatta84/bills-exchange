package com.sample.billsexchange.controller;

import com.sample.billsexchange.model.Coin;
import com.sample.billsexchange.service.ChangeService;
import com.sample.billsexchange.validation.annotation.BillConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/get-change")
@Validated
public class ChangeController {

	@Autowired
	private ChangeService changeService;

	@GetMapping("/bills/{bill}")
	public List<Coin> getChange(@BillConstraint @PathVariable int bill) {
		return changeService.getChange(bill);
	}
	
}
