package com.sabre.demo.controller;

import com.sabre.demo.service.IntegrationGateway;
import com.sabre.demo.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IntegrationController {

    @Autowired
    private IntegrationGateway gateway;

    @GetMapping(value = "/integration/{name}")
    public MessageDto getMessageFromIntegration(@PathVariable("name") String name) {
        return gateway.getMessage(new MessageDto(name));
    }
}
