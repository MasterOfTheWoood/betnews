package ru.betnews.dao;

import ru.betnews.entity.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 11:32
 *
 */
public interface UserDao  extends Dao<User, Long> {
    /**
     * Получеине пользователя по
     *
     * @param email email
     * @return
     */
    User loadUserByEmail(String email);

    List<User> findLikeName(String userNick);

    User loadUserByUserNick(String userNick);

    List<User> getInterlocutors(User user);

    List<User> getReferrals(User user);
}
