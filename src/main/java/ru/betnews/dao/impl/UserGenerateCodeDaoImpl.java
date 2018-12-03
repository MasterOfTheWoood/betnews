package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.UserGenerateCodeDao;
import ru.betnews.entity.User;
import ru.betnews.entity.UserGenerateCode;

/**
 * Created by Evgeniy.Guzeev on 01.03.2018.
 */
@Repository
public class UserGenerateCodeDaoImpl  extends AbstractHibernateDao<UserGenerateCode, Long> implements UserGenerateCodeDao {

    public UserGenerateCodeDaoImpl() {
        super(UserGenerateCode.class);
    }

    @Override
    public UserGenerateCode findByUser(User user) {
        return (UserGenerateCode) entityManager.createQuery("from UserGenerateCode where user.id = :userId")
                .setParameter("userId", user.getId()).getSingleResult();
    }

    @Override
    public UserGenerateCode findByCode(String code) {
        return (UserGenerateCode) entityManager.createQuery("from UserGenerateCode where code = :code")
                .setParameter("code", code).getSingleResult();
    }
}
