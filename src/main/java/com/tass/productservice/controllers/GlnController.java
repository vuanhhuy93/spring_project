package com.tass.productservice.controllers;

import com.tass.productservice.model.response.SearchGlnResponse;
import com.tass.productservice.services.GlnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gln")
public class GlnController {

    @Autowired
    GlnService glnService;
    @GetMapping("/{id}")
    public SearchGlnResponse findById(@PathVariable long id){
        return glnService.findById(id);
    }
}
