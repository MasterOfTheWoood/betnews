package ru.betnews.service.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.betnews.entity.security.BetNewsUserDetails;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 08.12.13
 * Time: 14:09
 * Для получения текущего пользователя
 */
@Service
public class SecurityContextHelperImpl implements SecurityContextHelper {


    @Override
    public long getCurrentHeroId() {
        BetNewsUserDetails userDetails = SecurityContextHolder.getContext().getAuthentication() != null?
            (BetNewsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;
        if(userDetails != null){
            return userDetails.getUserId();
        }
        return 0;
    }
}
