package com.github.chkypros.crm.sync;

import com.github.chkypros.crm.event.pbxadded.PBXAddedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class PBXAddedSynchronization implements ApplicationListener<PayloadApplicationEvent<PBXAddedEvent>> {

    private final CDRSynchronizer cdrSynchronizer;

    @Autowired
    public PBXAddedSynchronization(CDRSynchronizer cdrSynchronizer) {
        this.cdrSynchronizer = cdrSynchronizer;
    }

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<PBXAddedEvent> event) {
        cdrSynchronizer.synchronize(event.getPayload().getPbx());
    }
}
