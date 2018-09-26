package com.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class rabbitmq {
    public static void main(String[] args) throws Exception {
        connectToRabbit();
    }

    public static void connectToRabbit() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // "guest"/"guest" by default, limited to localhost connections
        String userName = "guest";
        factory.setUsername(userName);
        String password = "guest";
        factory.setPassword(password);
        String virtualHost = "/";
        factory.setVirtualHost(virtualHost);
        String hostName = "localhost";
        factory.setHost(hostName);
        int portNumber = 5672;
        factory.setPort(portNumber);

        Connection conn = factory.newConnection();

        System.out.println("-----------1111------");

        Channel channel = conn.createChannel();
        boolean autoAck = false;
        String queueName = "profile.profile-merge-service";
        channel.basicConsume(queueName, autoAck, "myConsumerTag",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        long deliveryTag = envelope.getDeliveryTag();

                        System.out.println("-----------2222------");
                            String message = new String(body, "UTF-8");

                        System.out.println(message);

                        // (process the message components here ...)
                        channel.basicAck(deliveryTag, false);
                    }

                });
    }
}
