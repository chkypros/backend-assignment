package com.github.chkypros.crm.cdr;

import com.github.chkypros.crm.domain.CDR;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cdr")
public class CDRController {
    private static final String TENANT_UUID_HEADER = "Tenant-UUID";

    private final CDRService cdrService;

    @Autowired
    public CDRController(CDRService cdrService) {
        this.cdrService = cdrService;
    }

    @ApiOperation("Get all CDRs for given tenant")
    @GetMapping
    public Iterable<CDR> getAllCdrsOfTenant(@RequestHeader(TENANT_UUID_HEADER) final UUID tenantUuid) {
        return cdrService.getAllCdrs(tenantUuid);
    }

    @PostMapping
    public CDR addCdr(
            @RequestHeader(TENANT_UUID_HEADER) final UUID tenantUuid,
            @RequestBody final CDR cdr
    ) {
        cdr.setTenantUuid(tenantUuid);
        return cdrService.addCdr(cdr);
    }

    @PutMapping
    public CDR updateCdr(
            @RequestHeader(TENANT_UUID_HEADER) final UUID tenantUuid,
            @RequestBody final CDR cdr
    ) {
        cdr.setTenantUuid(tenantUuid);
        return cdrService.updateCdr(cdr);
    }

    @DeleteMapping
    public void removeCdr(@RequestBody final CDR cdr) {
        cdrService.removeCdr(cdr);
    }
}
