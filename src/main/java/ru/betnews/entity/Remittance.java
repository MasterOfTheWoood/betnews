package ru.betnews.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Денежный перевод. Это запись в базе данных
 * при любой денежной операции: пополнение счёта, получение выигрыша, проигрышь, плата комиссии и так далее.
 * Created by Evgeniy.Guzeev on 09.04.2018.
 */
@Entity
@Table(name = "remittance")
public class Remittance {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    //Акаунт пользователя
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    //Акаунт пользователя
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_to_id")
    private Account accountTo;
    @Column (name= "amount ")
    private double amount ;//Размер платежа
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "remittance_date")
    private Date remittance_date;//Дата платежа
    @Column (name = "remittance_type")
    @Enumerated(EnumType.STRING)
    private RemittanceType remittanceType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getRemittance_date() {
        return remittance_date;
    }

    public void setRemittance_date(Date remittance_date) {
        this.remittance_date = remittance_date;
    }

    public RemittanceType getRemittanceType() {
        return remittanceType;
    }

    public void setRemittanceType(RemittanceType remittanceType) {
        this.remittanceType = remittanceType;
    }
}
