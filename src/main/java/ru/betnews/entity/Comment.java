package ru.betnews.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 01.09.14
 * Time: 11:58
 * Комментарий к новости
 */
@Entity
@Table(name = "comment")
public class Comment {

    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private long id;
    @Column(name = "description", columnDefinition="TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    //Ноавость, которой принадлежит коменарий
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="news_id")
    private News news;
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "date")
    private Date date;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dislikes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="replay_id")
    private Comment replay;

    @OneToMany(mappedBy = "replay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> replays = new ArrayList<Comment>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getDislikes() {
        return dislikes;
    }

    public void setDislikes(Set<User> dislikes) {
        this.dislikes = dislikes;
    }

    public Comment getReplay() {
        return replay;
    }

    public void setReplay(Comment replay) {
        this.replay = replay;
    }

    public List<Comment> getReplays() {
        return replays;
    }

    public void setReplays(List<Comment> replays) {
        this.replays = replays;
    }
}
