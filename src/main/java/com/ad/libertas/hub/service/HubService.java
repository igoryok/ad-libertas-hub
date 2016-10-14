package com.ad.libertas.hub.service;

import com.ad.libertas.hub.dto.HttpRequestMessage;
import com.ad.libertas.hub.service.receiver.MessageReceiver;
import com.ad.libertas.hub.util.HubUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Service class for UserForm processing
 *
 * @author Shevchenko Igor
 * @since 2016-09-20
 */
@Service
public class HubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HubService.class);

    @Value("${queue.name}")
    private String queueName;

    @Value("${file.name}")
    private String fileName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private HubUtil hubUtil;

//    @Autowired
//    private MessageReceiver messageReceiver;

    public HttpRequestMessage send(HttpServletRequest httpRequest) throws IOException, InterruptedException {

        Map<String, List<String>> headerNameToValue = new HashMap<>();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = httpRequest.getHeaders(headerName);
            List<String> headerValues = new ArrayList<>();
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                headerValues.add(headerValue);
            }
            headerNameToValue.put(headerName, headerValues);
        }

        Map<String, List<String>> paramNameToValue = new HashMap<>();
        Enumeration<String> parameterNames = httpRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            List<String> paramValues = Arrays.asList(httpRequest.getParameterValues(paramName));
            paramNameToValue.put(paramName, paramValues);
        }

        final String body;
        try (InputStream in = httpRequest.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            IOUtils.copy(in, out);
            JSONObject json = new JSONObject(new String(out.toByteArray(), "utf-8"));
            body = json.toString();
        }

        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(httpRequest.getMethod(),
                httpRequest.getRequestURI(), headerNameToValue, paramNameToValue, body);

        LOGGER.info("Request details: " +
                        "\n Method: {} " +
                        "\n URI: {} " +
                        "\n Headers: {} " +
                        "\n Parameters: {} " +
                        "\n Body: {} ",
                httpRequestMessage.getMethod(),
                httpRequestMessage.getUri(),
                httpRequestMessage.getHeaders(),
                httpRequestMessage.getParams(),
                httpRequestMessage.getBody());


        sendToRabbitMQ(httpRequestMessage);
        saveToFileSystem(httpRequestMessage);

        return httpRequestMessage;
    }

    /**
     * Method sends object as string to RabbitMQ
     *
     * @param httpRequestMessage object which should be sent
     */
    private void sendToRabbitMQ(HttpRequestMessage httpRequestMessage) throws InterruptedException {
        LOGGER.info("Sending to RabbitMQ following = {}", httpRequestMessage.toString());

        Message message = new Message(SerializationUtils.serialize(httpRequestMessage), new MessageProperties());

      //rabbitTemplate.send(queueName, message);

      rabbitTemplate.convertAndSend(queueName, HubUtil.convertObjectToJsonString(httpRequestMessage));

        LOGGER.info("Sending to RabbitMQ was completed");

        // messageReceiver.getCountDownLatch().await(5, TimeUnit.SECONDS);
    }

    /**
     * Method saves object as string to File system
     *
     * @param httpRequestMessage object which should be saved
     */
    private void saveToFileSystem(HttpRequestMessage httpRequestMessage) {
        try {
            File newTextFile = new File(fileName);

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(httpRequestMessage.toString());
            fw.close();

            LOGGER.info("Output file was created = {}", newTextFile.getAbsolutePath());

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
    }
}
