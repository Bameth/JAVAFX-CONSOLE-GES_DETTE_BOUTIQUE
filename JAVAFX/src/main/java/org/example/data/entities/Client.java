package org.example.data.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "client") 
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "addresse", nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    // Constructeur vide pour JPA
    public Client() {
    }

    // Constructeur pour initialiser les valeurs des propriétés
    public Client(String surname, String phone, String address, User user) {
        this.user = user;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }
    public Client(String surname, String phone, String address) {
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    // Propriétés JavaFX (non persistées directement, utilisées pour la liaison)
    public StringProperty surnameProperty() {
        return new SimpleStringProperty(surname);
    }

    public StringProperty phoneProperty() {
        return new SimpleStringProperty(phone);
    }

    public StringProperty addressProperty() {
        return new SimpleStringProperty(address);
    }
    public StringProperty usernameProperty() {
        if (this.user != null) {
            return new SimpleStringProperty(this.user.getName());
        } else {
            return new SimpleStringProperty("Ce client n'a pas de compte");
        }
    }    

    // Accesseurs et mutateurs pour la persistance avec JPA
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return user != null ? user.getName() : "Pas de compte";
    }

}
