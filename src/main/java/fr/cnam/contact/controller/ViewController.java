package fr.cnam.contact.controller;

import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.entity.Mail;
import fr.cnam.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private ContactRepository repository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", repository.findAll());
        return "index";
    }

    ////////////////// CONTACT /////////////////////

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

    //////////////////// EMAIL ////////////////////////

    @GetMapping("addemail")
    public String addEmail(Model model) {
        model.addAttribute("contacts" , repository.findAll() );

        Mail email = new Mail();

        model.addAttribute("email", email );
//        model.addAttribute("contact", email.getOwner() );

        return "addEmail";
    }

    @PostMapping("/addemail")
    public String addEmail(@ModelAttribute Mail email, @ModelAttribute Contact contact ) {
        try {
            System.out.println("TTTTTTTTTTTTTTTTT " +email);

            contact.addMail(email);
            System.out.println("TTTTTTTTTTTTTTTTT " +email);
            repository.save(contact);

        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return "redirect:/";
    }

}