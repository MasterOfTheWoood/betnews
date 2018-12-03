package ru.betnews.entity.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.betnews.entity.User;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: guzeev
 * Date: 27.09.12
 * Time: 18:56
 * Характеристики пользователя для Spring Security
 */
public class BetNewsUserDetails implements UserDetails {
    private long userId;
    private String username;
    private String password;
    private String email;
    private Date lastPasswordReset;
    private Collection<BetNewsGrantedAuthority> betNewsGrantedAuthorities = new HashSet<BetNewsGrantedAuthority>();
    private Boolean accountNotExpired = true;
    private Boolean accountNotBlocked = true;
    private Boolean credentialsNotExpired = true;
    private Boolean enabled = true;

    public BetNewsUserDetails(User user){
        userId = user.getId();
        username = user.getUserNick();
        password = user.getPassword();
        if(user.getBetNewsRoles()!= null)
            betNewsGrantedAuthorities.add(new BetNewsGrantedAuthority(user.getBetNewsRoles().getName()));
        else
            betNewsGrantedAuthorities.add(new BetNewsGrantedAuthority("MEMBER"));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return betNewsGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }

    public Boolean getAccountNotExpired() {
        return accountNotExpired;
    }

    public void setAccountNotExpired(Boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public Boolean getAccountNotBlocked() {
        return accountNotBlocked;
    }

    public void setAccountNotBlocked(Boolean accountNotBlocked) {
        this.accountNotBlocked = accountNotBlocked;
    }

    public Boolean getCredentialsNotExpired() {
        return credentialsNotExpired;
    }

    public void setCredentialsNotExpired(Boolean credentialsNotExpired) {
        this.credentialsNotExpired = credentialsNotExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
