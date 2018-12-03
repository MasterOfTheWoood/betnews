package ru.betnews.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 01.09.14
 * Time: 8:38
 * новость
 */
@Entity
@Table(name = "news")
public class News {
    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "news_id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition="TEXT")
    private String description;
    //Иллюстрация к новости. Ссылка на сторонний ресурс с изображением
    @Column(name = "illustration")
    private String illustration;
    //В какой разделе хранится новость
    @Column (name = "topic")
    @Enumerated(EnumType.STRING)
    private NewsTopic newsTopic;
    @Column (name = "type")
    @Enumerated(EnumType.STRING)
    private NewsType newsType;
    @Column (name= "rating")
    private int rating;
    //Спам индекс
    @Column (name= "spam")
    private int spam;
    //Дата создания
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "create_date")
    private Date createDate;
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name="arbiter_id")
    private User arbiter; // Арбитр новости
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<Comment>();
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Outcome> outcomes = new ArrayList<>();
    @Column(columnDefinition="tinyint(1) default 0", name="archive")
    private boolean archive;//Архивная новость, которая старела. По истечению месяца, после её аривации она будет удаляться.
   //Дата удаления в архив
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "archive_date")
    private Date archiveDate;
    //В случае приватной новости есть спискок тех, кто может проголосовать за эту новость, кто её вообще может видеть, смотреть и голосовать в ней.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "can_vote_users",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> canVoteUsers = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "news_likes",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "news_dislikes",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public NewsTopic getNewsTopic() {
        return newsTopic;
    }

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getSpam() {
        return spam;
    }

    public void setSpam(int spam) {
        this.spam = spam;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getArbiter() {
        return arbiter;
    }

    public void setArbiter(User arbiter) {
        this.arbiter = arbiter;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    public Set<User> getCanVoteUsers() {
        return canVoteUsers;
    }

    public void setCanVoteUsers(Set<User> canVoteUsers) {
        this.canVoteUsers = canVoteUsers;
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
}
