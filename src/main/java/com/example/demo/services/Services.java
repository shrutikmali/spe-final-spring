package com.example.demo.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Base64;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.models.Request;
import com.example.demo.models.Response;



@Service
public class Services {

    public final String FLASK_SERVER = "http://192.168.49.2:32000";

    private final String path = "/mnt/data";

    Logger logger = LoggerFactory.getLogger(Services.class);
    
    public Response getText(Request request) {
        logger.info("Called Services->getText");
        WebClient webClient = WebClient.create();
        String url = FLASK_SERVER + "/recognise";
        if(request.getSaveImage()) {
            this.saveImage(request);
        }
        try {
            logger.info("Making request to: " + url);
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
            logger.error("Error occurred in Services->getText", e);
            Response error = new Response();
            error.setText("An error occurred: " + e.getMessage());
            return error;
        }
    }

    public boolean saveImage(Request request) {
        try {
            String base64String[] = request.getImg().split(",");
            String extension;
            switch (base64String[0]) {
                case "data:image/jpeg;base64":
                    extension = ".jpeg";
                    break;
                case "data:image/png;base64":
                    extension = ".png";
                    break;
                default:
                    extension = ".jpg";
                    break;
            }
            byte data[] = Base64.getDecoder().decode(base64String[1]);
            String name = UUID.randomUUID().toString() + extension;
            File file = new File(path + "/" + name);
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            logger.info("Writing file: " + file.getAbsolutePath());
            outputStream.write(data);
            logger.info("File saved successfully");
            outputStream.close();
            return true;
        } 
        catch (IOException e) {
            e.printStackTrace();
            logger.error("Error in Services->saveImage: ", e);
            return false;
        }
        catch (Exception e) {
            logger.error("Error occurred in Services->saveImage", e);
            return false;
        }
    }
}
