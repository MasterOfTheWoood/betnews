package ru.betnews.service.user;

import ru.betnews.entity.Message;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.DialogDTO;
import ru.betnews.entity.dto.MessageDTO;
import ru.betnews.entity.dto.UserMinDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Evgeniy.Guzeev on 14.03.2018.
 */
public interface MessageService {
    MessageDTO sendMessage(String message, User userFrom, User userTo);

    List<DialogDTO> getDialogs(User user);

    List<Message> getMessages(User user, User user2, int offset);
}
