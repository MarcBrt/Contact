package fr.cnam.contact.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String lastName;
    private String firstName;

    @OneToMany
    private List<Mail> mailList = new ArrayList<Mail>();

    @ManyToMany
    private List<Adress> adressList = new ArrayList<Adress>();

    public Contact() {
    }

    public Contact( Long id, String firstName, String lastName) {
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
                ", adressList=" + adressList +
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
}
