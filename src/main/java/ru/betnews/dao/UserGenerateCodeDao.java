package ru.betnews.dao;

import ru.betnews.entity.User;
import ru.betnews.entity.UserGenerateCode;

/**
 * Created by Evgeniy.Guzeev on 01.03.2018.
 */
public interface UserGenerateCodeDao extends Dao<UserGenerateCode, Long> {
    UserGenerateCode findByUser(User user);

    UserGenerateCode findByCode(String code);
}
