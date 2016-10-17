package com.ad.libertas.hub.service.receiver;

import com.ad.libertas.hub.dto.HttpRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.utils.SerializationUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Class for receive messages from RabbitMQ
 *
 * @author Shevchenko Igor
 * @since 2016-09-20
 */
public class MessageReceiver {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
//    private CountDownLatch countDownLatch = new CountDownLatch(1);
//
//    public void receiveMsg(byte[] body) {
//        HttpRequestMessage message  = (HttpRequestMessage) SerializationUtils.deserialize(body);
//        LOGGER.info("Message Received: \n ={}", message.toString());
//        countDownLatch.countDown();
//    }
//
//    public void receiveMsg(String body) {
//        LOGGER.info("Message Received: \n ={}", body);
//        countDownLatch.countDown();
//    }
//
//    public CountDownLatch getCountDownLatch() {
//        return countDownLatch;
//    }
}