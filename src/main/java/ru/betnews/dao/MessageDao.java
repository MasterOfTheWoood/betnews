package ru.betnews.dao;

import ru.betnews.entity.Message;
import ru.betnews.entity.User;

import java.util.List;

/**
 * Created by Evgeniy.Guzeev on 14.03.2018.
 */
public interface MessageDao  extends Dao<Message, Long> {
    List<Message> getMessages(User user1, User user2, int offset);
    Message getLastMessage(User user, User interlocutor);

}
