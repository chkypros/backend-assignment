package com.github.chkypros.crm.sync;

import com.github.chkypros.crm.cdr.CDRService;
import com.github.chkypros.crm.domain.PBX;
import com.github.chkypros.crm.pbx.PBXClient;
import com.github.chkypros.crm.pbx.PBXService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class CDRSynchronizer {

    private final CDRService cdrService;
    private final PBXService pbxService;
    private final PBXClient pbxClient;

    @Autowired
    public CDRSynchronizer(CDRService cdrService, PBXService pbxService, PBXClient pbxClient) {
        this.cdrService = cdrService;
        this.pbxService = pbxService;
        this.pbxClient = pbxClient;
    }

    public void synchronize(final PBX pbx) {
        final var cdrs = pbxClient.getCdrs(pbx);

        final var knownCDRs = cdrService.getAllCdrs(pbx.getTenantUuid());
        final var unknownCdrs = cdrs.stream()
                .filter(cdr -> !knownCDRs.contains(cdr))
                .collect(Collectors.toList());

        log.info("From CDRs:{}, the ones that are not in:{}, are:{}",
                cdrs, knownCDRs, unknownCdrs);
        unknownCdrs.forEach(cdrService::addCdr);
        pbxService.updateLastSynchronizationDate(pbx);
    }

    @Scheduled(cron = "${cdr.synchronize.cron:0 0 20 * * *}")
    private void synchronize() {
        pbxService.getAllPbxs().forEach(this::synchronize);
    }
}
