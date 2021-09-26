package com.github.chkypros.crm.sync;

import com.github.chkypros.crm.cdr.CDRService;
import com.github.chkypros.crm.domain.CDR;
import com.github.chkypros.crm.pbx.PBXService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/webhook")
public class CDRWebhookController {

    private final CDRService cdrService;
    private final PBXService pbxService;

    @Autowired
    public CDRWebhookController(CDRService cdrService, PBXService pbxService) {
        this.cdrService = cdrService;
        this.pbxService = pbxService;
    }

    @PostMapping
    public void webhook(
            @RequestHeader("Tenant-UUID") final UUID tenantUuid,
            @RequestBody final CDR cdrUpdate
            ) {
        log.info("Got cdrUpdate: {}", cdrUpdate);

        if (!pbxService.getAllPbxsByTenant(tenantUuid).isEmpty()) {
            cdrUpdate.setTenantUuid(tenantUuid);
            cdrService.updateCdr(cdrUpdate);
        }
    }
}
