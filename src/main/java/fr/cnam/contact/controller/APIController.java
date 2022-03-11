package fr.cnam.contact.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.entity.Mail;
import fr.cnam.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    @Autowired
    private ContactRepository repository;

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> index(@RequestParam(required = false) String action, @RequestParam(defaultValue = "0") long id, Model model) {

        if (action != null) {
            Optional<Contact> contact;
            switch (action) {
                case "listContacts":
                    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
                case "getContact":
                    contact = repository.findById(id);
                    return contact.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
                case "delContact":
                    contact = repository.findById(id);
                    return contact.<ResponseEntity<Object>>map(value -> {
                        repository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.OK);
                    }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        }

        return null;
    }

    @PostMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> postInfo(@RequestParam String action, @RequestParam(defaultValue = "0") long id, @RequestBody String xml, Model model) {
        if (action != null) {
            Contact value = null;
                    switch (action) {
                case "addContact":
                    try {
                        XmlMapper xmlMapper = new XmlMapper();
                        value = xmlMapper.readValue(xml, Contact.class);
                        repository.save(value);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
                case "editContact":
                    try {
                        XmlMapper xmlMapper = new XmlMapper();
                        value = xmlMapper.readValue(xml, Contact.class);
                        Contact oldContact = repository.getOne(id);
                        oldContact.setFirstName(value.getFirstName());
                        oldContact.setLastName(value.getFirstName());
                        repository.flush();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return null;
    }

}