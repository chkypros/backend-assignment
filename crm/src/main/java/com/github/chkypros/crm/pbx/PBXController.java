package com.github.chkypros.crm.pbx;

import com.github.chkypros.crm.domain.PBX;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pbx")
public class PBXController {
    private final PBXService pbxService;

    @Autowired
    public PBXController(PBXService pbxService) {
        this.pbxService = pbxService;
    }

    @ApiOperation("Get all PBXs for given tenant")
    @GetMapping
    public Iterable<PBX> getAllPbxsOfTenant(@RequestHeader final UUID tenantUuid) {
        return pbxService.getAllPbxsByTenant(tenantUuid);
    }

    @PostMapping
    public PBX addPbx(
            @RequestHeader final UUID tenantUuid,
            @RequestBody final PBX pbx
    ) {
        pbx.setTenantUuid(tenantUuid);
        return pbxService.addPbx(pbx);
    }

    @PutMapping
    public PBX updatePbx(
            @RequestHeader final UUID tenantUuid,
            @RequestBody final PBX pbx
    ) {
        pbx.setTenantUuid(tenantUuid);
        return pbxService.updatePbx(pbx);
    }

    @DeleteMapping
    public void removePbx(@RequestBody final PBX pbx) {
        pbxService.removePbx(pbx);
    }
}
