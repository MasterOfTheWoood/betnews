package ru.betnews.service.memory;

import ru.betnews.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public interface MemoryUserService {
    User addUser(long userId);
    User getUser(long userId);
    User editUser(User user);
}
