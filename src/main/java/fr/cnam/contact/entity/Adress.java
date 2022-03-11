package fr.cnam.contact.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String label;
    private String postalCode;
    private String city;
    private String adress;
    private String additionalInformation;

    @JsonIgnore
    @ManyToMany(mappedBy = "adressList", cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Contact> contactList = new ArrayList<Contact>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Adress addContact(Contact contact) throws Exception {
        if (!this.contactList.contains(contact)) {
            this.contactList.add(contact);
            return this;
        }
        throw new Exception("Adress already exist");
    }

    public Adress removeContact(Contact contact) throws Exception {
        if (this.contactList.contains(contact)) {
            this.contactList.remove(contact);
            return this;
        }
        throw new Exception("Adress does not exist");
    }



    public List<Contact> getContactList() {
        return contactList;
    }

}
