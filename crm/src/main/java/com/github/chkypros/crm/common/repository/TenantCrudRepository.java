package com.github.chkypros.crm.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface TenantCrudRepository<T, ID> extends CrudRepository<T, ID> {
    List<T> findAllByTenantUuid(UUID tenantUuid);

    Optional<T> findByUuidAndTenantUuid(ID uuid, UUID tenantUuid);
}
