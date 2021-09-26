package com.github.chkypros.crm.pbx;

import com.github.chkypros.crm.domain.PBX;
import com.github.chkypros.crm.event.pbxadded.PBXAddedEvent;
import com.github.chkypros.crm.event.pbxadded.PBXAddedEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PBXService {
    private final PBXRepository pbxRepository;
    private final PBXAddedEventPublisher pbxAddedEventPublisher;

    @Autowired
    public PBXService(final PBXRepository pbxRepository, PBXAddedEventPublisher pbxAddedEventPublisher) {
        this.pbxRepository = pbxRepository;
        this.pbxAddedEventPublisher = pbxAddedEventPublisher;
    }

    public List<PBX> getAllPbxs() {
        return pbxRepository.findAll();
    }

    public List<PBX> getAllPbxsByTenant(final UUID tenantId) {
        return pbxRepository.findAllByTenantUuid(tenantId);
    }

    public Optional<PBX> getPbx(final UUID pbxId, final UUID tenantId) {
        return pbxRepository.findByUuidAndTenantUuid(pbxId, tenantId);
    }

    public PBX addPbx(final PBX pbx) {
        getAllPbxsByTenant(pbx.getTenantUuid()).parallelStream()
                .filter(pbx::equals)
                .findAny()
                .ifPresent(existingPbx -> {
                    throw new IllegalArgumentException("PBX already exists");
                });

        final var savedPbx = pbxRepository.save(pbx);
        pbxAddedEventPublisher.publish(new PBXAddedEvent(pbx));

        return savedPbx;
    }

    public PBX updatePbx(final PBX pbx) {
        return pbxRepository.save(pbx);
    }

    public void removePbx(final PBX pbx) {
        getAllPbxsByTenant(pbx.getTenantUuid()).parallelStream()
                .filter(pbx::equals)
                .findAny()
                .ifPresent(pbxRepository::delete);
    }

    public void updateLastSynchronizationDate(PBX pbx) {
        pbxRepository.updateLastSynchronizationDate(pbx);
    }
}
