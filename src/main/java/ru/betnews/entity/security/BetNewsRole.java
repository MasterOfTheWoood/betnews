package ru.betnews.entity.security;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: guzeev
 * Date: 28.09.12
 * Time: 10:31
 * Роли
 */
@Entity
@Table(name = "bookcoa_role")
public class BetNewsRole {

    // - идентификатор *
    @Id
    @GeneratedValue
    @Column(name = "bookcoa_role_id")
    private int bookcoaRoleId;

    //Название  роли
    @Column(name = "name")
    private String name;

    public int getBookcoaRoleId() {
        return bookcoaRoleId;
    }

    public void setBookcoaRoleId(int bookcoaRoleId) {
        this.bookcoaRoleId = bookcoaRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
