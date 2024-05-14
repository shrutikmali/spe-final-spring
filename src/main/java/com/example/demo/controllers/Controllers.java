package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Request;
import com.example.demo.models.Response;
import com.example.demo.services.Services;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
public class Controllers {

    @Autowired
    Services services;
    
    @PostMapping("/recognise")
    public ResponseEntity<Response> recogniseText(@RequestBody Request request) {
        Response response = services.getText(request);
        return ResponseEntity.ok().body(response);
    }
    
}
