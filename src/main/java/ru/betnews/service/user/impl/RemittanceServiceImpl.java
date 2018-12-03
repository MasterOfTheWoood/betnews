package ru.betnews.service.user.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.betnews.dao.AccountDao;
import ru.betnews.dao.RemittanceDao;
import ru.betnews.dao.UserDao;
import ru.betnews.entity.Remittance;
import ru.betnews.entity.RemittanceType;
import ru.betnews.entity.User;
import ru.betnews.entity.dto.UserDTO;
import ru.betnews.service.user.RemittanceService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Evgeniy.Guzeev on 14.05.2018.
 */
@Service
public class RemittanceServiceImpl implements RemittanceService {

    @Resource
    private RemittanceDao remittanceDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private UserDao userDao;

    @Override
    @Transactional
    public void doTransaction(User userFrom, User userTo, double value) throws Exception {
        Remittance remittance = new Remittance();
        remittance.setAccount(userFrom.getAccount());
        remittance.setAccountTo(userTo.getAccount());
        if(value <= 0) throw new Exception("negative.amount");//Отрицательное значение платежа
        remittance.setAmount(value);
        remittance.setRemittance_date(new Date());
        remittance.setRemittanceType(RemittanceType.VOTE);
        userFrom.getAccount().setBalance(userFrom.getAccount().getBalance() - value);
        if(userFrom.getAccount().getBalance() < 0) throw new Exception("negative.balance");
        accountDao.saveOrUpdate(userFrom.getAccount());
        userTo.getAccount().setBalance(userTo.getAccount().getBalance() + value);
        accountDao.saveOrUpdate(userTo.getAccount());
        remittanceDao.create(remittance);
    }

    @Override
    public void payToSystem(User user, double value) throws Exception {
        User system = userDao.read(1L);
        doTransaction(user, system, value);
    }

    @Override
    public void payFromSystem(User user, double value) throws Exception {
        User system = userDao.read(1L);
        doTransaction(system, user, value);
    }

    @Override
    public void payToAdmin(User user, double value) throws Exception {
        User system = userDao.read(2L);
        doTransaction(user, system, value);
    }
}
