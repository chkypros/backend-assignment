package com.github.chkypros.crm.phonebook;

import com.github.chkypros.crm.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Component
public class ContactsInitializer {

    private final ContactService contactService;
    private final Resource initFile;

    @Autowired
    public ContactsInitializer(
            ContactService contactService,
            @Value("${phonebook.init.file}") Resource initFile) {
        this.contactService = contactService;
        this.initFile = initFile;
    }

    @PostConstruct
    private void init() {
        log.info("Loading contacts from file:{}", initFile);
        try (InputStream inputStream = initFile.getInputStream()) {
            new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .skip(1)
                    .map(line -> {
                        final var elements = line.split(",");
                        return new Contact(null, elements[0], elements[1], elements[2]);
                    })
                    .forEach(contactService::addContact);
        } catch (IOException e) {
            log.warn("Failed to load initial phonebook entries from: " + initFile, e);
        }
    }
}
