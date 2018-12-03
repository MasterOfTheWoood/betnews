package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.UserDao;
import ru.betnews.entity.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 11:33
 */
@Repository
public class UserDaoImpl  extends AbstractHibernateDao<User, Long> implements UserDao{

    public UserDaoImpl(){
        super(User.class);
    }

    @Override
    public User loadUserByEmail(String email) {
        return (User) entityManager.createQuery("from User where email = :email")
                .setParameter("email", email ).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findLikeName(String userNick) {
        return userNick.isEmpty()? entityManager.createQuery("from User ").getResultList():
                entityManager.createQuery("from User where userNick like :userNick")
                .setParameter("userNick", userNick ).getResultList();
    }

    @Override
    public User loadUserByUserNick(String userNick) {
        return (User)entityManager.createQuery("from User where userNick = :userNick")
                .setParameter("userNick", userNick ).getSingleResult();
    }

    @Override
    public List<User> getInterlocutors(User user) {
        return entityManager.createQuery("select distinct u from  User u where u.id in (select m.userTo from Message m where m.userFrom.id = :userId) or u.id in (select m.userFrom from Message m where  m.userTo.id = :userId)")
                        .setParameter("userId", user.getId()).getResultList();
    }

    @Override
    public List<User> getReferrals(User user) {
        return entityManager.createQuery("select distinct u from  User u where u.referrer.id = :userId")
                .setParameter("userId", user.getId()).getResultList();
    }
}
