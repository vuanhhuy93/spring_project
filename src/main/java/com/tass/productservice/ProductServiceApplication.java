package com.tass.productservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Log4j2
public class ProductServiceApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ProductServiceApplication.class, args);

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }
    @EventListener(ApplicationReadyEvent.class)
    private void serverReady(){

        System.out.println("==================================================");
        System.out.println("|         WORKFLOW  SERVICE SERVER IS READY       |");
        System.out.println("|         DEPLOY BY HUYVA (huyva@gimo.vn)         |");
        System.out.println("==================================================");
        System.out.println("--------------------------------------------------");

        System.out.println("--------------------------------------------------");

        log.info("==================================================");
        log.info("|        PRODUCT  SERVICE SERVER IS READY       |");
        log.info("|        DEPLOY BY HUYVA (huyva@gimo.vn)         |");
        log.info("|                   STARTED                      |");
        log.info("==================================================");
        log.info("-----------------------------------");


    }
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
