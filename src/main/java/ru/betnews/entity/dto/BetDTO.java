package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import ru.betnews.entity.Bet;

/**
 * Created by dell on 08.05.2017.
 */
@JsonSerialize
public class BetDTO {
    private Long id;
    private double betValue;
    private long userId;

    public BetDTO(){}

    public BetDTO(Bet bet){
        id = bet.getId();
        betValue = bet.getBetValue();
        userId = bet.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBetValue() {
        return betValue;
    }

    public void setBetValue(double betValue) {
        this.betValue = betValue;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
