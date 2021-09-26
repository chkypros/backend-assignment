package com.github.chkypros.crm.pbx;

import com.github.chkypros.crm.common.repository.TenantCrudRepository;
import com.github.chkypros.crm.domain.PBX;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface PBXRepository extends TenantCrudRepository<PBX, UUID> {
    @Override
    List<PBX> findAll();

    @Modifying
    @Transactional
    @Query("update PBX p set p.lastSynchronizationDate = current_date where p.uuid = :#{#pbx.uuid}")
    void updateLastSynchronizationDate(PBX pbx);
}
