package com.github.chkypros.crm.phonebook;


import com.github.chkypros.crm.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

    Optional<Contact> findByName(String name);

    Optional<Contact> findByNumber(String number);
}
