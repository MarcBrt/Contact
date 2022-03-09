package fr.cnam.contact.repository;

import fr.cnam.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByLastName(String lastName);
}
