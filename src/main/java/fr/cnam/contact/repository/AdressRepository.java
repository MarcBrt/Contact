package fr.cnam.contact.repository;

import fr.cnam.contact.entity.Adress;
import fr.cnam.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdressRepository extends JpaRepository<Adress, Long> {

}
