package com.github.chkypros.crm.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class CDRDataReport {
    private long totalNumberOfCdrs;
    private TotalMinutes totalNumberOfMinutes;
    private long totalNumberOfUnmatchedCalls;
    private LocalDateTime lastSynchronizationDate;

    @Getter
    @Setter
    @Builder(setterPrefix = "with")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TotalMinutes {
        private long successfulCalls;
        private long incompleteCalls;
    }
}
