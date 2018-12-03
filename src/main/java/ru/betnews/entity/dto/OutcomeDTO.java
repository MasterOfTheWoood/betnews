package ru.betnews.entity.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import ru.betnews.entity.Bet;
import ru.betnews.entity.Outcome;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dell on 08.05.2017.
 */
@JsonSerialize
public class OutcomeDTO {
    private Long id;
    private int number;
    private String description;
    private List<BetDTO> bets = new ArrayList<>();

    public OutcomeDTO(){}

    public OutcomeDTO(Outcome outcome){
        this.id = outcome.getId();
        this.number = outcome.getNumber();
        this.description = outcome.getDescription();
        for(Bet bet : outcome.getBets()){
            BetDTO betDTO = new BetDTO(bet);
            bets.add(betDTO);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BetDTO> getBets() {
        return bets;
    }

    public void setBets(List<BetDTO> bets) {
        this.bets = bets;
    }

}
