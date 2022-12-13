package com.tass.productservice.model.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
@Data
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String code;

    public GrantedAuthorityImpl(){

    }

    public GrantedAuthorityImpl(String role){
        this.code = role;
    }
    @Override
    public String getAuthority() {
        return this.code;
    }
}
