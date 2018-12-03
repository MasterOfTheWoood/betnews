package ru.betnews.service.user;

import ru.betnews.entity.User;

/**
 * Created by Evgeniy.Guzeev on 14.05.2018.
 */
public interface RemittanceService {
    void doTransaction(User userFrom, User userTo, double value) throws Exception;
    void payToSystem(User user, double value) throws Exception;
    void payFromSystem(User user, double value) throws Exception;
    void payToAdmin(User user, double value) throws Exception;
}
