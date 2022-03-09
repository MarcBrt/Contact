package fr.cnam.contact.controller;

import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private ContactRepository repository;

    @GetMapping("/")
    public String index(Model model) {

        Contact c2 = new Contact(); c2.setId(1L); c2.setLastName("Brt"); c2.setFirstName("Mrc");
        Contact c3 = new Contact(); c3.setId(2L); c3.setLastName("Brt"); c3.setFirstName("Mrc");
        repository.save(c2);
        repository.save(c3);

        System.out.println(repository.count());

        model.addAttribute("contacts", repository.findAll());
        return "index";
    }

    @GetMapping("/viewcontact/{id}")
    public String viewContact(@PathVariable long id, Model model) {
        Contact c1 = repository.findById(id).orElse(new Contact());
        model.addAttribute("contact", c1);
        return "viewContact";
    }

    @PostMapping("/viewcontact/{id}")
    public String contactSave(@PathVariable long id, @ModelAttribute Contact contact, Model model) {
        model.addAttribute("contact", contact);
        System.out.println(contact.toString());
        repository.save(contact);
        return "viewContact";
    }

    @GetMapping("/addContact")
    public String getAddContact(Contact contact, Model model, ContactRepository repository) {
        return "addContact";
    }

    @PostMapping("/addContact")
    public String postAddContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "addContact";
        }

        repository.save(contact);
        return "redirect:/";
    }

    @GetMapping("/viewcontact/{id}/delete")
    public String getDelContact(@PathVariable long id, Model model) {
        Optional<Contact> c1 = repository.findById(id);
        c1.ifPresent(contact -> repository.delete(contact));
        return "redirect:/";
    }

}