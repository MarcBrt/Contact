package fr.cnam.contact.controller;

import fr.cnam.contact.entity.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ViewController {

    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }

    @ModelAttribute("someList")
    public List<Contact> getSomeList(){
        Contact c1 = new Contact(); c1.setId(new Long(0)); c1.setLastName("Brt"); c1.setFirstName("Mrc");
        Contact c2 = new Contact(); c1.setId(new Long(1)); c1.setLastName("Brt"); c1.setFirstName("Mrc");
        Contact c3 = new Contact(); c1.setId(new Long(2)); c1.setLastName("Brt"); c1.setFirstName("Mrc");
        List<Contact> contacts = new ArrayList<>();
        contacts.add(c1);
        contacts.add(c2);
        contacts.add(c3);
        return contacts;
    }

}