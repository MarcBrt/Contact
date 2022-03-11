package fr.cnam.contact.repository;

import fr.cnam.contact.entity.Contact;
import fr.cnam.contact.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findMailByOwner(Contact owner);
}
