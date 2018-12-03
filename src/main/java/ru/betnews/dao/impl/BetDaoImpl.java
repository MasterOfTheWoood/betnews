package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.BetDao;
import ru.betnews.entity.Bet;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 11:53
 *
 */
@Repository
public class BetDaoImpl extends AbstractHibernateDao<Bet, Long> implements BetDao{
    public BetDaoImpl(){
        super(Bet.class);
    }
}
