package ru.betnews.service.user.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.betnews.dao.AccountDao;
import ru.betnews.dao.BetNewsRoleDao;
import ru.betnews.dao.UserDao;
import ru.betnews.dao.UserGenerateCodeDao;
import ru.betnews.entity.Account;
import ru.betnews.entity.User;
import ru.betnews.entity.UserGenerateCode;
import ru.betnews.entity.dto.UserDTO;
import ru.betnews.service.user.UserService;
import ru.betnews.service.utils.UtilService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 16.09.14
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UtilService utilService;
    @Resource
    private UserDao userDao;
    @Resource
    private UserGenerateCodeDao userGenerateCodeDao;
    @Resource
    private BetNewsRoleDao betNewsRoleDao;
    @Resource
    private AccountDao accountDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${security.salt}")
    private String salt;

    @Override
    @Transactional
    public User registration(UserDTO userDTO) throws Exception{
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUserNick(userDTO.getUserNick());
        if(!userDTO.getPassword().equals(userDTO.getPasswordConfirm()))
            throw new Exception("incorrect password");

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setBetNewsRoles(betNewsRoleDao.findByName("MEMBER"));
        user.setRating(0);
        Account account = new Account();
        account.setBalance(0);
        account = accountDao.create(account);
        user.setAccount(account);
        user = userDao.create(user);
        UserGenerateCode userGenerateCode = genCode(user);
        Map<String, Object> params = new HashMap<>();
        params.put("code", userGenerateCode);
        utilService.sendEmail(user.getEmail(), "Verify Email", "verify_email", params);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long userId) {
        return userDao.read(userId);
    }

    @Override
    @Transactional
    public void editUser(UserDTO userDTO) {
        try {
        User user = userDao.read(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setBirthday(userDTO.getBirthdayDate());
        user.setEmail(userDTO.getEmail());
        userDao.saveOrUpdate(user);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void sendRemindPasswordMail(String email)throws Exception {
        User user = userDao.loadUserByEmail(email);
        if(user != null){
            UserGenerateCode oldCode = userGenerateCodeDao.findByUser(user);
            if(oldCode != null){
                userGenerateCodeDao.delete(oldCode);
            }
            UserGenerateCode userGenerateCode = genCode(user);
            Map<String, Object> params = new HashMap<>();
            params.put("code", userGenerateCode);
            utilService.sendEmail(email, "Remain password", "remain_password", params);
        }
        else throw new Exception("remind.wrong.email");
    }

    @Override
    @Transactional
    public boolean remindPassword(String code) throws Exception {
        UserGenerateCode userGenerateCode = userGenerateCodeDao.findByCode(code);
        if(userGenerateCode != null){
            userGenerateCodeDao.delete(userGenerateCode);
            return true;
        }
        return false;
    }

    private UserGenerateCode genCode(User user){
        String remainCode = utilService.md5Java(RandomStringUtils.random(16));
        UserGenerateCode userGenerateCode = new UserGenerateCode();
        userGenerateCode.setCode(remainCode);
        userGenerateCode.setUser(user);
        return userGenerateCodeDao.create(userGenerateCode);
    }

    @Override
    public List<UserDTO> findLikeName(String userName) {
        List<User> users = userDao.findLikeName(userName != null? userName : "");
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user : users){
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    @Override
    @Transactional
    public boolean verifyEmail(String code) {
        UserGenerateCode userGenerateCode = userGenerateCodeDao.findByCode(code);
        if(userGenerateCode != null){
            userGenerateCode.getUser().setVerifyEmail(true);
            userDao.saveOrUpdate(userGenerateCode.getUser());
            userGenerateCodeDao.delete(userGenerateCode);
            return true;
        }
        return false;
    }

    @Override
    public void sendVerifyEmailMail(User user) {
        UserGenerateCode userGenerateCode = genCode(user);
        Map<String, Object> params = new HashMap<>();
        params.put("code", userGenerateCode);
        utilService.sendEmail(user.getEmail(), "Verify Email", "verify_email", params);
    }
}
