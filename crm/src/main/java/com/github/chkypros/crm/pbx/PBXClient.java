package com.github.chkypros.crm.pbx;

import com.github.chkypros.crm.domain.CDR;
import com.github.chkypros.crm.domain.CDRList;
import com.github.chkypros.crm.domain.PBX;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class PBXClient {

    public Collection<CDR> getCdrs(final PBX pbx) {
        final var restTemplate = new RestTemplate();

        final var url = String.format("http://%s:%d/get_cdr", pbx.getHostname(), pbx.getPort());
        final var cdrList = restTemplate.getForObject(url, CDRList.class);

        return (null == cdrList)
            ? Collections.emptyList()
            : getCdrsWithTenantUuid(cdrList.getData(), pbx.getTenantUuid());
    }

    private Collection<CDR> getCdrsWithTenantUuid(List<CDR> cdrs, UUID tenantUuid) {
        cdrs.forEach(cdr -> cdr.setTenantUuid(tenantUuid));
        return cdrs;
    }
}
