package ru.betnews.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Evgeniy.Guzeev on 01.03.2018.
 */
@Entity
@Table(name = "user_generate_code")
public class UserGenerateCode {
    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "news_id")
    private long id;
    @Column(name = "code", unique = true)
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    //Дата создания
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name= "create_date")
    private Date createDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
