package com.tass.productservice.model;

import lombok.Data;

@Data
public class BaseResponse {
    private int code;
    private String message;

    public BaseResponse(){
        this.code = 1;
        this.message = "SUCCESS";
    }
}