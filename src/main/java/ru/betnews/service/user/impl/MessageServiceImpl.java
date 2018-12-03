package ru.betnews.service.user.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.ManagedTransactionAdapter;
import ru.betnews.dao.MessageDao;
import ru.betnews.dao.UserDao;
import ru.betnews.entity.Message;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.DialogDTO;
import ru.betnews.entity.dto.MessageDTO;
import ru.betnews.entity.dto.UserMinDTO;
import ru.betnews.service.memory.MemoryUserService;
import ru.betnews.service.user.MessageService;

import javax.annotation.Resource;
import java.util.*;

/**
 * Сервис обмена и получения сообщеинй
 * Created by Evgeniy.Guzeev on 14.03.2018.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;
    @Resource
    private UserDao userDao;
    @Resource
    private MemoryUserService memoryUserService;

    @Override
    @Transactional
    public MessageDTO sendMessage(String messageText, User userFrom, User userTo) {
        Message message = new Message();
        message.setMessage(messageText);
        message.setUserFrom(userFrom);
        message.setUserTo(userTo);
        message.setDate(new Date());
        message = messageDao.create(message);
     //   memoryUserService.addMessage();
        return new MessageDTO(message);
    }

    @Override
    public List<DialogDTO> getDialogs(User user) {
        List<DialogDTO> dialogs = new ArrayList<>();
        List<User> interlocutors = userDao.getInterlocutors(user);
        for(User interlocutor : interlocutors){
            UserMinDTO userMinDTO = new UserMinDTO(interlocutor);
            Message messages = messageDao.getLastMessage(user, interlocutor);
            dialogs.add(new DialogDTO(userMinDTO, new MessageDTO(messages)));
        }
        return dialogs;
    }

    @Override
    public List<Message> getMessages(User user, User userFrom, int offset) {
        return messageDao.getMessages(user, userFrom, offset);
    }
}
