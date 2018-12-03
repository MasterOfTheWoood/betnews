package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.RemittanceDao;
import ru.betnews.entity.Remittance;

/**
 * Created by Evgeniy.Guzeev on 09.04.2018.
 */
@Repository
public class RemittanceDaoImpl  extends AbstractHibernateDao<Remittance, Long> implements RemittanceDao {
    public RemittanceDaoImpl() {
        super(Remittance.class);
    }
}
