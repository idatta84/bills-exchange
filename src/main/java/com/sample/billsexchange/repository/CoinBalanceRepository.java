package com.sample.billsexchange.repository;

import com.sample.billsexchange.entity.CoinBalance;
import org.springframework.data.repository.CrudRepository;

public interface CoinBalanceRepository extends CrudRepository<CoinBalance, Integer> {
}
