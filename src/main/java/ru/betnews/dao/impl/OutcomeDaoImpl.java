package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.OutcomeDao;
import ru.betnews.entity.Outcome;

/**
 * Created by Евгений on 24.04.2015.
 */
@Repository
public class OutcomeDaoImpl extends AbstractHibernateDao<Outcome, Long> implements OutcomeDao {

    public OutcomeDaoImpl(){
        super(Outcome.class);
    }
}
