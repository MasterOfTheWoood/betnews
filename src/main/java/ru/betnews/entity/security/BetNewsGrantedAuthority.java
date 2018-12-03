package ru.betnews.entity.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created with IntelliJ IDEA.
 * User: guzeev
 * Date: 27.09.12
 * Time: 18:58
 * Роли доступа
 */
public class BetNewsGrantedAuthority implements GrantedAuthority {
    private String authority;

    public BetNewsGrantedAuthority(){}

    public BetNewsGrantedAuthority(String authority){
       this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
