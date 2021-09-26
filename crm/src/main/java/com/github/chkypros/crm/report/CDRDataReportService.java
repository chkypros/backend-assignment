package com.github.chkypros.crm.report;

import com.github.chkypros.crm.cdr.CDRService;
import com.github.chkypros.crm.domain.CDR;
import com.github.chkypros.crm.domain.CDRDataReport;
import com.github.chkypros.crm.domain.PBX;
import com.github.chkypros.crm.pbx.PBXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CDRDataReportService {

    private final CDRService cdrService;
    private final PBXService pbxService;

    @Autowired
    public CDRDataReportService(CDRService cdrService, PBXService pbxService) {
        this.cdrService = cdrService;
        this.pbxService = pbxService;
    }

    public CDRDataReport getReport(final UUID tenantUuid) {
        final var cdrs = cdrService.getAllCdrs(tenantUuid);

        final var cdrsByNotAnswered = cdrs.stream()
                .collect(Collectors.partitioningBy(
                        cdr -> null == cdr.getAnswerStart() && null != cdr.getCallEnd()
                ));
        final var unmatchedCdrs = cdrsByNotAnswered.get(true);
        final var answeredCdrs = cdrsByNotAnswered.get(false);


        return CDRDataReport.builder()
                .withTotalNumberOfCdrs(cdrs.size())
                .withTotalNumberOfMinutes(
                        CDRDataReport.TotalMinutes.builder()
                                .withSuccessfulCalls(getTotalOfMinutes(answeredCdrs))
                                .withIncompleteCalls(getTotalOfMinutes(unmatchedCdrs))
                                .build()
                )
                .withTotalNumberOfUnmatchedCalls(unmatchedCdrs.size())
                .withLastSynchronizationDate(getOldestLastSynchronizationDate(tenantUuid))
                .build();
    }

    private Long getTotalOfMinutes(final List<CDR> cdrs) {
        return cdrs.stream()
                .mapToLong(CDR::getDuration)
                .sum();
    }

    private LocalDateTime getOldestLastSynchronizationDate(UUID tenantUuid) {
        return pbxService.getAllPbxsByTenant(tenantUuid).stream()
                .map(PBX::getLastSynchronizationDate)
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }
}
