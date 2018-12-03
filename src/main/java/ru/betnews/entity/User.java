package ru.betnews.entity;

import ru.betnews.entity.security.BetNewsRole;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 01.09.14
 * Time: 6:41
 * Пользователь нашей системы
 */
@Entity
@Table(name = "user")
public class User {

    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long id;
    //Роли
    @JoinColumn(name = "role")
    @ManyToOne(cascade = CascadeType.ALL)
    private BetNewsRole betNewsRoles;
    //Основная информация
    //псевдоним
    @Column (name = "nick")
    private String userNick;
    //или
    //Пароль
    @Column (name= "password")
    private String password;
    //почта (можно указать после дополнительно, но только для подписки на новости и тп)
    @Column (name= "email")
    private String email;
    //или
    //id пользователя соц сети
    @Column (name= "social_user_id")
    private String socialUserId;
    //+
    //id соц сети
    @Column (name= "social_id")
    private String socialsId;

    //Дополнительная информация
    //Имя
    @Column (name= "first_name")
    private String firstName;
    //Отчество
    @Column (name= "middle_name")
    private String middleName;
    //Фамилия
    @Column (name= "last_name")
    private String lastName;
    //Дата рождения
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "birthday")
    private Date birthday;
    //Аватар
    @Column (name= "avatar")
    private String avatar;
    //Статистика или рейтинг человека, отображет на сколько уважаем участник на сайте
    @Column (name= "rating")
    private int rating;

    //Акаунт пользователя
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "user")
    private Set<News> newses = new HashSet<News>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Bet> bets = new HashSet<Bet>();
    @ManyToMany
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<User>();
    //Заблокирован ли пользователь?
    @Column(columnDefinition="tinyint(1) default 0", name="blocked")
    private boolean blocked;
    //Подтвержён ли email
    @Column(columnDefinition="tinyint(1) default 0", name="verify_email")
    private boolean verifyEmail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_id")
    private User referral;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BetNewsRole getBetNewsRoles() {
        return betNewsRoles;
    }

    public void setBetNewsRoles(BetNewsRole betNewsRoles) {
        this.betNewsRoles = betNewsRoles;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    public String getSocialsId() {
        return socialsId;
    }

    public void setSocialsId(String socialsId) {
        this.socialsId = socialsId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Set<News> getNewses() {
        return newses;
    }

    public void setNewses(Set<News> newses) {
        this.newses = newses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Bet> getBets() {
        return bets;
    }

    public void setBets(Set<Bet> bets) {
        this.bets = bets;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isVerifyEmail() {
        return verifyEmail;
    }

    public void setVerifyEmail(boolean verifyEmail) {
        this.verifyEmail = verifyEmail;
    }

    public User getReferral() {
        return referral;
    }

    public void setReferral(User referral) {
        this.referral = referral;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) && (this.id == ((User)obj).getId());
    }

    @Override
    public int hashCode() {
        return (int)id;
    }
}
