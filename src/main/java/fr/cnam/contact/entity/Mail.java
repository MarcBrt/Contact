package fr.cnam.contact.entity;

import javax.persistence.*;

@Entity
public class Mail {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String adress;

    @ManyToOne
    private Contact owner;

    public Mail(Long id, String adress, Contact owner) {
        this.id = id;
        this.adress = adress;
        this.owner = owner;
    }

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
}
