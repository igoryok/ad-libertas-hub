package com.ad.libertas.hub.controller;

import com.ad.libertas.hub.dto.HttpRequestMessage;
import com.ad.libertas.hub.service.HubService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Controller class with Rest endpoint
 *
 * @author Shevchenko Igor
 * @since 2016-09-20
 */

@RestController
public class HubController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HubController.class);

    private final HubService hubService;

    @Autowired
    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @RequestMapping("/**")
    public ResponseEntity process(HttpServletRequest httpRequest) throws Exception {
        LOGGER.info("== Request processing was started ==");

        HttpRequestMessage httpRequestMessage = hubService.send(httpRequest);

        LOGGER.info("== Request processing was finished ==");
        return ResponseEntity.ok(httpRequestMessage);
    }
}
