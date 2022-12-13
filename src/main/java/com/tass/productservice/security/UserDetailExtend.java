package com.tass.productservice.security;

import com.tass.productservice.database.entities.User;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
public class UserDetailExtend implements UserDetails {
    private String userName;
    private String password;
    private int status;
    private List<GrantedAuthority> roles;


    public UserDetailExtend(){

    }

    public UserDetailExtend(User user , List<GrantedAuthority> roles){
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.roles = roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}
