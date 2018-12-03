package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.AccountDao;
import ru.betnews.entity.Account;

/**
 * Created by Evgeniy.Guzeev on 16.04.2018.
 */
@Repository
public class AccountDaoImpl extends AbstractHibernateDao<Account, Long> implements AccountDao {
    public AccountDaoImpl() {
        super(Account.class);
    }
}
