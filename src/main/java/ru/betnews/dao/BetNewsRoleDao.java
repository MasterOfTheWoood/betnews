package ru.betnews.dao;

import ru.betnews.entity.security.BetNewsRole;

/**
 * Created by Evgeniy.Guzeev on 10.04.2018.
 */
public interface BetNewsRoleDao extends Dao<BetNewsRole, Long>  {
    BetNewsRole findByName(String member);
}
