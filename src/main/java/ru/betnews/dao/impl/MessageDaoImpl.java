package ru.betnews.dao.impl;

import org.springframework.stereotype.Repository;
import ru.betnews.dao.AbstractHibernateDao;
import ru.betnews.dao.MessageDao;
import ru.betnews.entity.Message;
import ru.betnews.entity.User;

import java.util.List;

/**
 * Created by Evgeniy.Guzeev on 14.03.2018.
 */
@Repository
public class MessageDaoImpl  extends AbstractHibernateDao<Message, Long> implements MessageDao {
    public MessageDaoImpl() {
        super(Message.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Message> getMessages(User user1, User user2, int offset) {
        return entityManager
                .createQuery("from Message where (userFrom.id = :userId1 and userTo.id = :userId2) or (userFrom.id = :userId2 and userTo.id = :userId1)" +
                        " order by date desc")
                .setParameter("userId1", user1.getId()).setParameter("userId2", user2.getId()).setMaxResults(10).setFirstResult(offset).getResultList();
    }

    @Override
    public Message getLastMessage(User user1, User user2) {
        return (Message)entityManager
                .createQuery("from Message where (userFrom.id = :userId1 and userTo.id = :userId2) or (userFrom.id = :userId2 and userTo.id = :userId1)" +
                        " order by date desc")
                .setParameter("userId1", user1.getId()).setParameter("userId2", user2.getId()).setMaxResults(1).getSingleResult();
    }
}
