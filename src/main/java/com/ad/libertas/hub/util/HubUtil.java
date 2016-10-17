package com.ad.libertas.hub.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Util class for converting objects
 *
 * @author Shevchenko Igor
 * @since 2016-10-14
 */
public class HubUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HubUtil.class);

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static String convertObjectToJsonString(Object object){
        if (object == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException exc) {
            LOG.error("Error on reading object " + object, exc);
            throw new RuntimeException(exc);
        }
    }
}
