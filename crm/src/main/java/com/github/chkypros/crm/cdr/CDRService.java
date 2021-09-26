package com.github.chkypros.crm.cdr;

import com.github.chkypros.crm.domain.CDR;
import com.github.chkypros.crm.domain.Contact;
import com.github.chkypros.crm.phonebook.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CDRService {
    private final CDRRepository cdrRepository;
    private final ContactService contactService;

    @Autowired
    public CDRService(final CDRRepository cdrRepository, ContactService contactService) {
        this.cdrRepository = cdrRepository;
        this.contactService = contactService;
    }

    public List<CDR> getAllCdrs(final UUID tenantId) {
        return cdrRepository.findAllByTenantUuid(tenantId);
    }

    public Optional<CDR> getCdr(final UUID cdrId, final UUID tenantId) {
        return cdrRepository.findByUuidAndTenantUuid(cdrId, tenantId);
    }

    public CDR addCdr(final CDR cdr) {
        getAllCdrs(cdr.getTenantUuid()).parallelStream()
                .filter(cdr::equals)
                .findAny()
                .ifPresent(existingCdr -> {
                    throw new IllegalArgumentException("CDR already exists");
                });

        contactService.findContactByNumber(cdr.getCallerNumber())
                .map(Contact::getName)
                .ifPresent(cdr::setCallerName);

        return cdrRepository.save(cdr);
    }

    public CDR updateCdr(final CDR cdr) {
        return cdrRepository.save(cdr);
    }

    public void removeCdr(final CDR cdr) {
        getAllCdrs(cdr.getTenantUuid()).parallelStream()
                .filter(cdr::equals)
                .findAny()
                .ifPresent(cdrRepository::delete);
    }
}
