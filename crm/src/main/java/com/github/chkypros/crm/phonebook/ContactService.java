package com.github.chkypros.crm.phonebook;

import com.github.chkypros.crm.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Optional<Contact> findContactByName(final String name) {
        return contactRepository.findByName(name);
    }

    public Optional<Contact> findContactByNumber(final String number) {
        return contactRepository.findByNumber(number);
    }

    public Contact addContact(final Contact contact) {
        contactRepository.findByNumber(contact.getNumber())
                .ifPresent(existingContact -> {
                    throw new IllegalArgumentException("Contact already exists");
                });

        return contactRepository.save(contact);
    }

    public Contact updateContact(final Contact contact) {
        return contactRepository.save(contact);
    }

    public void removeContact(final Contact contact) {
        contactRepository.delete(contact);
    }
}
