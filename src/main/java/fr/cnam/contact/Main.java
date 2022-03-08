package fr.cnam.contact;

import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.repository.ContactRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}