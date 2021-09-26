package com.github.chkypros.crm.event.pbxadded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PBXAddedEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PBXAddedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(PBXAddedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
