package fr.cnam.contact.controller;

import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.entity.Mail;
import fr.cnam.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class APIController {

    @Autowired
    private ContactRepository repository;

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<Contact>> index(@RequestParam(required = false) String action, @RequestParam(defaultValue = "0") long id, Model model) {

        if (action != null) {
            switch (action) {
                case "listContacts":
                   // model.addAttribute("xml", xStream.toXML(repository.findAll()));
                    return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
                case "getContact":
                    break;
                case "delContact":
                    break;
            }
        }

        return null;
    }

}