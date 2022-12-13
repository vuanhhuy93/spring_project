package com.tass.productservice.domain;

import com.tass.productservice.database.entities.Role;
import com.tass.productservice.database.repository.RoleRepository;
import com.tass.productservice.database.repository.UserRoleRepository;
import com.tass.productservice.model.dto.GrantedAuthorityImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RoleDomain {
    private static Map<String, Role> CACHE_ROLE = new HashMap<>();

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @PostConstruct
    private void initData() {

        List<Role> roleList = roleRepository.findAll();

        if (!CollectionUtils.isEmpty(roleList)) {
            for (Role role : roleList) {

                CACHE_ROLE.put(role.getCode(), role);
            }
        }
    }

    public List<GrantedAuthority> getUserRole(long userId){
        Set<String>  roles =  userRoleRepository.getUserRoleActiveByUserId(userId);

        if (!CollectionUtils.isEmpty(roles)){

            List<GrantedAuthority> authorities = new ArrayList<>();

            for (String role : roles){
                authorities.add(new GrantedAuthorityImpl(role));
            }

            return authorities;
        }
         return new ArrayList<>();
    }
}
