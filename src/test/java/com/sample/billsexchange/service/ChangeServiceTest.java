package com.sample.billsexchange.service;

import com.sample.billsexchange.entity.CoinBalance;
import com.sample.billsexchange.model.Coin;
import com.sample.billsexchange.repository.CoinBalanceRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChangeServiceTest {
    @Mock
    private CoinBalanceRepository coinBalanceRepository;
    @InjectMocks
    private ChangeService changeService;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    private final String ERROR_MSG_NO_CHANGE = "Change not available for this bill !";

    @Test
    public void getChange_throws_NullPointer() {
        when(coinBalanceRepository.findAll()).thenReturn(null);
        try {
            changeService.getChange(10);
        } catch(Exception e){
            assertEquals(ERROR_MSG_NO_CHANGE, e.getMessage());
        }
    }

    @Test
    public void getChange_returns_data() {
        List<CoinBalance> coinBalanceList = new ArrayList<>();
        coinBalanceList.add(new CoinBalance(0.25, 100));
        coinBalanceList.add(new CoinBalance(0.1, 100));
        coinBalanceList.add(new CoinBalance(0.05, 100));
        coinBalanceList.add(new CoinBalance(0.01, 100));
        when(coinBalanceRepository.findAll()).thenReturn(coinBalanceList);
        List<Coin> coins = changeService.getChange(10);
        assertEquals(1, coins.size());
        assertEquals(0.25, coins.get(0).getValue(), 0);
        assertEquals(40, coins.get(0).getCount());
    }

    @Test
    public void getChange_throws_exception() {
        List<CoinBalance> coinBalanceList = new ArrayList<>();
        coinBalanceList.add(new CoinBalance(0.25, 5));
        coinBalanceList.add(new CoinBalance(0.1, 5));
        coinBalanceList.add(new CoinBalance(0.05, 5));
        coinBalanceList.add(new CoinBalance(0.01, 5));
        when(coinBalanceRepository.findAll()).thenReturn(coinBalanceList);
        try {
            List<Coin> coins = changeService.getChange(10);
        } catch(Exception e){
            assertEquals(ERROR_MSG_NO_CHANGE, e.getMessage());
        }
    }
}