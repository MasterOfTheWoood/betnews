package ru.betnews.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 16:01
 * Исход новости.
 */
@Entity
@Table(name = "outcome")
public class Outcome {
    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "outcome_id")
    private long id;
    @Column(name = "outcome_num")
    private int number;
    @Column(name = "description", columnDefinition="TEXT")
    private String description;
    @OneToMany(mappedBy = "outcome", fetch = FetchType.EAGER)
    private List<Bet> bets = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id")
    private News news;
    @Column(columnDefinition="tinyint(1) default 0", name="confirmed")
    private boolean confirmed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
