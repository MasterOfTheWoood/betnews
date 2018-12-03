package ru.betnews.entity;

import ru.betnews.entity.dto.UserDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Evgeniy.Guzeev on 14.03.2018.
 */
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private long id;
    @ManyToOne
    @JoinColumn(name="user_to_id")
    private User userTo;
    @ManyToOne
    @JoinColumn(name="user_from_id")
    private User userFrom;
    @Column(name = "description", columnDefinition="TEXT")
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "create_date")
    private Date date;
    //Прочитанно?
    @Column(columnDefinition="tinyint(1) default 0", name="isRead")
    private boolean isRead;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
