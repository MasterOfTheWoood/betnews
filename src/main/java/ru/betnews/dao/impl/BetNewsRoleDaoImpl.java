package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.BetNewsRoleDao;
import ru.betnews.entity.security.BetNewsRole;

/**
 * Created by Evgeniy.Guzeev on 10.04.2018.
 */
@Repository
public class BetNewsRoleDaoImpl  extends AbstractHibernateDao<BetNewsRole, Long> implements BetNewsRoleDao {
    public BetNewsRoleDaoImpl() {
        super(BetNewsRole.class);
    }

    @Override
    public BetNewsRole findByName(String name) {
        return (BetNewsRole) entityManager.createQuery("from BetNewsRole where name = :name")
                        .setParameter("name", name ).getSingleResult();
    }
}
