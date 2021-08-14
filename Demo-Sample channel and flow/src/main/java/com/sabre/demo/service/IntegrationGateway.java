package com.sabre.demo.service;

import com.sabre.demo.dto.MessageDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(errorChannel = "exceptionChannel")
public interface IntegrationGateway {
    @Gateway(requestChannel = "integrationChannel")
    MessageDto getMessage(MessageDto message);
}
