package com.gateway.app.appgatewayrouter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiGatewayEventService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void refreshRoutes(){
        log.info("refresh routes..");
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
