package com.github.chkypros.crm.domain;

import lombok.*;

import javax.persistence.Entity;
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
public class PBX {

    @Id
    @EqualsAndHashCode.Include
    private UUID uuid;

    private String hostname;

    private Integer port;

    private UUID tenantUuid;

    private LocalDateTime lastSynchronizationDate;
}
