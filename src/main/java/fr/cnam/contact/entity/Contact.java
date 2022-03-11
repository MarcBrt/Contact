package fr.cnam.contact.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 15)
    private String lastName;

    @NotNull
    @Size(min = 2, max = 15)
    private String firstName;

    @OneToMany(mappedBy="owner", cascade = CascadeType.ALL)
    private List<Mail> mailList = new ArrayList<Mail>();

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(
            name = "contact_postal",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "postal_id"))
    private List<Adress> adressList = new ArrayList<Adress>();

    public Contact() {
    }

    public Contact( Long id, String firstName, String lastName) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mailList=" + mailList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Mail> getMailList() {
        return mailList;
    }

    public Contact addMail(Mail mail) throws Exception {
        if (!this.mailList.contains(mail)) {
            this.mailList.add(mail);
            return this;
        }
        throw new Exception("Mail already exist");
    }

    public Contact removeMail(Mail mail) throws Exception {
        if (this.mailList.contains(mail)) {
            this.mailList.remove(mail);
            return this;
        }
        throw new Exception("Mail does not exist");
    }

    public List<Adress> getAdressList() {
        return adressList;
    }

    public Contact addAdress(Adress adress) throws Exception {
        if (!this.adressList.contains(adress)) {
            this.adressList.add(adress);
            return this;
        }
        throw new Exception("Adress already exist");
    }

    public Contact removeAdress(Adress adress) throws Exception {
        if (this.adressList.contains(adress)) {
            this.adressList.remove(adress);
            return this;
        }
        throw new Exception("Adress does not exist");
    }

    public void setMailList(List<Mail> mailList) {
        this.mailList = mailList;
    }

    public void setAdressList(List<Adress> adressList) {
        this.adressList = adressList;
    }
}
