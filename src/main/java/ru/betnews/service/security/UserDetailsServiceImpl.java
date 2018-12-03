package ru.betnews.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.betnews.dao.UserDao;
import ru.betnews.entity.User;
import ru.betnews.entity.security.BetNewsUserDetails;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: guzeev
 * Date: 04.09.12
 * Time: 15:02
 * Авторизитация
 */
@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.loadUserByUserNick(s);
        if(user == null){
            throw new UsernameNotFoundException(String.format("No user found with username '$s'.", s));
        }
        return new BetNewsUserDetails(user);
    }
}
