package com.github.chkypros.crm.cdr;

import com.github.chkypros.crm.common.repository.TenantCrudRepository;
import com.github.chkypros.crm.domain.CDR;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CDRRepository extends TenantCrudRepository<CDR, UUID> {

}
