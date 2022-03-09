package fr.cnam.contact.controller;

import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private ContactRepository repository;

    @GetMapping("/")
    public String greeting(Model model) {

        Contact c1 = new Contact(); c1.setId(0L); c1.setLastName("Brt"); c1.setFirstName("Mrc");
        Contact c2 = new Contact(); c2.setId(1L); c2.setLastName("Brt"); c2.setFirstName("Mrc");
        Contact c3 = new Contact(); c3.setId(2L); c3.setLastName("Brt"); c3.setFirstName("Mrc");
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);



        model.addAttribute("contacts", repository.findAll());
        return "index";
    }

    @GetMapping("/viewcontact/{id}")
    public String viewContact(@PathVariable String id, Model model, ContactRepository repository) {
        Contact c1 = new Contact(); c1.setId(0L); c1.setLastName("Brt"); c1.setFirstName("Mrc");
        model.addAttribute("contact", c1);
        return "viewContact";
    }

}