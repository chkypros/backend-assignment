package com.github.chkypros.crm.phonebook;

import com.github.chkypros.crm.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public Contact findContact(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String number) {

        if (null == name && null == number) {
            return null;
        } else {
            final var optionalContact = (null == name)
                    ? contactService.findContactByNumber(number)
                    : contactService.findContactByName(name);
            return optionalContact.orElse(null);
        }
    }

    @PostMapping
    public Contact addContact(@RequestBody final Contact contact) {
        return contactService.addContact(contact);
    }

    @PutMapping
    public Contact updateContact(@RequestBody final Contact contact) {
        return contactService.updateContact(contact);
    }

    @DeleteMapping
    public void removeContact(final Contact contact) {
        contactService.removeContact(contact);
    }
}
