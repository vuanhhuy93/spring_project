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

    public BaseResponse(ERROR error){
        this.code = error.code;
        this.message = error.message;
    }

    public BaseResponse(int code, String messsage){
        this.code = code;
        this.message = messsage;
    }
}