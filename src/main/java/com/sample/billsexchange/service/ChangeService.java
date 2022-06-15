package com.sample.billsexchange.service;

import com.sample.billsexchange.entity.CoinBalance;
import com.sample.billsexchange.exception.NoChangeLeftException;
import com.sample.billsexchange.model.Coin;
import com.sample.billsexchange.repository.CoinBalanceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ChangeService {
    @Autowired
    private CoinBalanceRepository coinBalanceRepository;

    @Transactional
    public synchronized List<Coin> getChange(int bill) {

        // get available change
        List<CoinBalance> coinBalanceList;

        try {
            coinBalanceList = (List<CoinBalance>)
                    ((List<CoinBalance>) coinBalanceRepository.findAll())
                            .stream().sorted(Comparator.reverseOrder())
                            .collect(Collectors.toList());
        } catch(NullPointerException e){
            throw new NoChangeLeftException(e);
        }
        // check if change exists
        List<Coin> coins = getChange(bill, coinBalanceList);

        // update remaining change
        for(CoinBalance coinBalance:coinBalanceList){
            coinBalanceRepository.save(coinBalance);
        }
        return coins;
    }

    private List<Coin> getChange(int bill, List<CoinBalance> coinBalanceList) {
        List<Coin> changeList = new ArrayList<>();
        double remaining = bill;
        int quotient = 0;
        int count = 0;
        for (CoinBalance coinBalance : coinBalanceList) {
            if (coinBalance.getCnt() > 0) {
                quotient = (int) (remaining / coinBalance.getVal());
                log.debug("Value {}, Count {}, Remaining {}, Quotient {}",
                        coinBalance.getVal(), coinBalance.getCnt(), remaining, quotient);
                if (quotient > 0) {
                    count = (quotient > coinBalance.getCnt()) ? coinBalance.getCnt() : quotient;
                    remaining -= coinBalance.getVal() * count;
                    changeList.add(new Coin(coinBalance.getVal(), count));
                    coinBalance.setCnt(coinBalance.getCnt() - count);
                }
            }
        }
        // Throw exception if all coins are exhausted and yet remaining amount is non zero
        if(remaining != 0){
            throw new NoChangeLeftException();
        }

        return changeList;
    }
}
