package com.sabre.demo.service;

import com.sabre.demo.dto.MessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

@Component
public class IntegrationService {

    @Bean
    public MessageChannel integrationChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel intermediateChannel1() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel intermediateChannel2() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel endChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel exceptionChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "integrationChannel",
            outputChannel = "intermediateChannel1")
    public MessageDto processMessage(MessageDto message) {
        return new MessageDto("Hello, " + message.getMessage().toUpperCase()
                + " your request is processed successfully.");
    }


    @ServiceActivator(inputChannel = "intermediateChannel1",
            outputChannel = "intermediateChannel2")
    public MessageDto processMessage1(MessageDto message) {
        return new MessageDto(message.getMessage() + ": intermediateChannel1");
    }


    @ServiceActivator(inputChannel = "intermediateChannel2",
            outputChannel = "filterChannel")
    public MessageDto processMessage2(MessageDto message) {
        return new MessageDto(message.getMessage() + ": intermediateChannel2");
    }

    @Bean
    @Filter(inputChannel = "filterChannel",
            outputChannel = "endChannel",
            discardChannel = "errorChannel")
    public MessageSelector getFilter() {
        return message -> (((MessageDto) message.getPayload()).getMessage().contains("INDIA"));
    }

    @ServiceActivator(inputChannel = "endChannel")
    public MessageDto endChannel(MessageDto message) {
        return new MessageDto(message.getMessage() + "!");
    }


    @ServiceActivator(inputChannel = "exceptionChannel")
    public MessageDto exceptionChannel(Message<MessageHandlingException> message) {
        return new MessageDto("Exception occurred. ::" + message.getPayload().getMessage());
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public MessageDto errorChannel(MessageDto message) {
        return new MessageDto("Error occurred. ::" + message.getMessage());
    }
}
