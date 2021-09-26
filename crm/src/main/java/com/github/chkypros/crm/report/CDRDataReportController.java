package com.github.chkypros.crm.report;

import com.github.chkypros.crm.domain.CDRDataReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/report")
public class CDRDataReportController {

    private final CDRDataReportService cdrDataReportService;

    @Autowired
    public CDRDataReportController(CDRDataReportService cdrDataReportService) {
        this.cdrDataReportService = cdrDataReportService;
    }

    @GetMapping
    public CDRDataReport getReport(
            @RequestHeader("Tenant-UUID") final UUID tenantUuid
    ) {
        return cdrDataReportService.getReport(tenantUuid);
    }
}
