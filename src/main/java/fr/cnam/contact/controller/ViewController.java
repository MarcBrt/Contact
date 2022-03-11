package fr.cnam.contact.controller;

import fr.cnam.contact.entity.Adress;
import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.entity.Mail;
import fr.cnam.contact.entity.User;
import fr.cnam.contact.repository.AdressRepository;
import fr.cnam.contact.repository.ContactRepository;
import fr.cnam.contact.service.SecurityUserDetailsService;
import fr.cnam.contact.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private ContactRepository repositoryContact;

    @Autowired
    private MailRepository repositoryMail;

    @Autowired
    private AdressRepository repositoryAdress;

    @GetMapping("/")
    public String index(Model model) {
        List<Contact> liste = repositoryContact.findAll();

        model.addAttribute("contacts", liste);
        return "index";
    }

    ////////////////// CONTACT /////////////////////

    @GetMapping("/viewcontact/{id}")
    public String viewContact(@PathVariable long id, Model model) {
        Contact c1 = repositoryContact.findById(id).orElse(new Contact());
        model.addAttribute("contact", c1);
        return "viewContact";
    }

    @PostMapping("/viewcontact/{id}")
    public String contactSave(@PathVariable long id, @ModelAttribute Contact contact, Model model) {
        model.addAttribute("contact", contact);
        System.out.println(contact.toString());
        repositoryContact.save(contact);
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

        repositoryContact.save(contact);
        return "redirect:/";
    }

    @GetMapping("/viewcontact/{id}/delete")
    public String getDelContact(@PathVariable long id, Model model) {
        Optional<Contact> c1 = repositoryContact.findById(id);
        c1.ifPresent(contact -> repositoryContact.delete(contact));
        return "redirect:/";
    }

    //////////////////// EMAIL ////////////////////////

    @GetMapping("/addemail")
    public String addEmail(Model model) {
        model.addAttribute("contacts", repositoryContact.findAll());
        return "addEmail";
    }

    @RequestMapping("/addemail/{id}/{adress}")
    public String getAddMail(@PathVariable String id, @PathVariable String adress) throws Exception {
        long idContact = Long.parseLong(id);

        try {

            Optional<Contact> c = repositoryContact.findById(idContact);
            if( c.isPresent() ) {
                Contact contact = c.get();

                Mail mail = new Mail();
                mail.setAdress(adress);
                mail.setOwner(contact);

                repositoryMail.save(mail);

                repositoryMail.flush();
                repositoryContact.flush();

                return "redirect:/";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "addEmail";
    }

    @GetMapping("/email/{id}/delete")
    public String getDelContact(@PathVariable long id) {
        Optional<Mail> c1 = repositoryMail.findById(id);
        c1.ifPresent(entity -> repositoryMail.delete(entity));
        return "redirect:/";
    }

    ////////////////// POSTALE ////////////////////

    @GetMapping("/postal")
    public String addPostal(Model model) {
        model.addAttribute("contacts", repositoryContact.findAll());

        return "addPostal";
    }

    @RequestMapping("/postal/{id}/{adressDesc}")
    public String getAddPostal(@PathVariable String id, @PathVariable String adressDesc) throws Exception {
        long idContact = Long.parseLong(id);

        try {

            Optional<Contact> c = repositoryContact.findById(idContact);
            if( c.isPresent() ) {
                Contact contact = c.get();

                Adress adress = new Adress();
                adress.setAdress(adressDesc);
                adress.addContact(contact);

                repositoryAdress.save(adress);

                Optional<Adress> tempAdress = repositoryAdress.findById(adress.getId());
                if( tempAdress.isPresent() ) {
                    Adress tmp = tempAdress.get();

                    contact.addAdress(tmp);
                }

                repositoryAdress.flush();
                repositoryContact.flush();


                return "redirect:/";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "addPostal";
    }

    @GetMapping("/postal/{id}/{idContact}/delete")
    public String getDelPostal(@PathVariable long id, @PathVariable long idContact) {
        try {
            Optional<Adress> c1 = repositoryAdress.findById(id);
            c1.ifPresent(entity -> {
                Optional<Contact> tempContact = repositoryContact.findById(idContact);
                tempContact.ifPresent(contact -> {
                    try {
                        contact.removeAdress(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                repositoryAdress.delete(entity);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }


    @Autowired private SecurityUserDetailsService userDetailsManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        session.setAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public String addUser(@RequestParam Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(passwordEncoder.encode(body.get("password")));
        user.setAccountNonLocked(true);
        userDetailsManager.createUser(user);

        return "redirect:/login";
    }
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
    }

}