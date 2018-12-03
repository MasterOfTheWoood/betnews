package ru.betnews.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 10:26
 * Ставка пользователя
 */

@Entity
@Table(name = "bet")
public class Bet {
    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "bet_id")
    private long id;
    @Column(name = "bet_value")
    private double betValue;//Ставка на новость, количество денег, которые пользователь ставит на определёный исход новости.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="outcome_id")
    private Outcome outcome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBetValue() {
        return betValue;
    }

    public void setBetValue(double betValue) {
        this.betValue = betValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }
}
