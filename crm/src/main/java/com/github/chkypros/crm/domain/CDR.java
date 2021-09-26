package com.github.chkypros.crm.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CDR {

    @Id
    private UUID uuid;

    @JsonAlias("tenant_uuid")
    @EqualsAndHashCode.Include
    private UUID tenantUuid;

    @JsonAlias("domain_name")
    private String domainName;

    @JsonAlias("caller_name")
    private String callerName;

    @JsonAlias("caller_number")
    @EqualsAndHashCode.Include
    private String callerNumber;

    @JsonAlias("destination_number")
    private String destinationNumber;

    private String direction;

    @JsonAlias("call_start")
    @EqualsAndHashCode.Include
    private LocalDateTime callStart;

    @JsonAlias("ring_start")
    private LocalDateTime ringStart;

    @JsonAlias("answer_start")
    private LocalDateTime answerStart;

    @JsonAlias("call_end")
    private LocalDateTime callEnd;

    private Integer duration;

    private UUID recording;

    @JsonAlias("click_to_call")
    private Boolean clickToCall;

    @JsonAlias("click_to_call_data")
    private String clickToCallData;

    @Enumerated(EnumType.STRING)
    private Action action;
}
