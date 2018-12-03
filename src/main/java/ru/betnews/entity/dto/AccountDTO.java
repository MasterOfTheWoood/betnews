package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import ru.betnews.entity.Account;

/**
 * Created by dell on 29.04.2018.
 */
@JsonSerialize
public class AccountDTO {
    private long id;
    private double balance;
    public AccountDTO(){}
    public AccountDTO(Account account){
        this.id = account.getAccountId();
        this.balance = account.getBalance();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
