package com.tass.productservice.services;

import com.tass.productservice.database.entities.User;
import com.tass.productservice.database.repository.UserRepository;
import com.tass.productservice.domain.RoleDomain;
import com.tass.productservice.security.UserDetailExtend;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleDomain roleDomain;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findFirstByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("username " + username + " not found on db");
        List<GrantedAuthority> authorities = roleDomain.getUserRole(user.getId());
        return new UserDetailExtend(user , authorities);
    }
}
