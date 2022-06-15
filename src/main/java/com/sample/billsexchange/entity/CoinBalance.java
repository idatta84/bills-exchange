package com.sample.billsexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "coinbalances")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinBalance implements Comparable{

    @Id
    private double val;

    private int cnt;

    @Override
    public int compareTo(Object o) {
        CoinBalance coinBalance = (CoinBalance) o;
        return (int)((this.val - coinBalance.val)*100);
    }
}