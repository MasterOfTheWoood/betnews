package ru.betnews.service.memory.impl;

import org.springframework.stereotype.Service;
import ru.betnews.entity.User;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.user.UserService;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 23.11.13
 * Time: 18:57
 * Сервис работы с онлайн пользователями, которые хранятся в памяти
 */
@Service
public class MemoryUserServiceImpl implements MemoryUserService {
    private Map<Long, User> onlineUsers = new ConcurrentHashMap<Long, User>();
    @Resource
    private UserService userService;

    @Override
    public User addUser(long userId) {
        User user;
        if(userId == 0) return null;
        if(!onlineUsers.containsKey(userId)){
            user = userService.getUserById(userId);
            onlineUsers.put(userId, user);
        }
        else user = onlineUsers.get(userId);
        return user;
    }

    @Override
    public User getUser(long userId) {
        User user = onlineUsers.get(userId);
        if(user == null)
            user = addUser(userId);
        return user;
    }

    @Override
    public User editUser(User user) {
        if(onlineUsers.containsKey(user.getId()))
            onlineUsers.put(user.getId(), user);
  //      userService.editUser(user);
        return user;
    }
}
