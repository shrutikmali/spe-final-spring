package com.example.demo.services;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.models.Request;
import com.example.demo.models.Response;



@Service
public class Services {

    public final String FLASK_SERVER = "http://127.0.0.1:5000";

    Logger logger = LoggerFactory.getLogger(Services.class);
    
    public Response getText(Request request) {
        logger.info("Called Services->getText");
        WebClient webClient = WebClient.create();

        try {
            Response res = webClient
            .post()
            .uri(new URI(FLASK_SERVER + "/recognise"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Response.class)
            .block();

            return res;
        }
        catch (Exception e) {
            logger.error("Error in Services->getText: " + e.getLocalizedMessage());
            logger.error(e.getMessage());
            Response error = new Response();
            error.setText("An error occurred");
            return error;
        }
    }

}
