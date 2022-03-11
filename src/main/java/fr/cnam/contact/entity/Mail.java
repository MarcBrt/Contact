package fr.cnam.contact.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.cnam.contact.repository.ContactRepository;

import javax.persistence.*;

@Entity
public class Mail {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adress;

    @JsonIgnore
    @ManyToOne
    private Contact owner;

    public Mail(String adress, Contact owner) {
        this.adress = adress;
        this.owner = owner;
    }

    public Mail() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", adress='" + adress + '\'' +
                ", owner=" + owner +
                '}';
    }
}
