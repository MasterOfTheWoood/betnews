package ru.betnews.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 01.09.14
 * Time: 7:34
 * Кошелёк с деньгами пользователя
 */
@Entity
@Table(name = "account")
public class Account {
    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private long accountId;
    //деньги на аккаунте
    @Column(name = "balance")
    private double balance;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
