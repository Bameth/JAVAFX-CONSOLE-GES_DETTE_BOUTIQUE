package org.example.data.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import org.example.data.enums.Role;
import org.example.data.enums.TypeEtat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = "client") 
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 25, unique = true)
    private String name;

    @Column(length = 25, unique = true)
    private String login;

    @Column(length = 25)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private TypeEtat typeEtat;

    @OneToOne(mappedBy = "user")
    private Client client;

    // Property methods for JavaFX bindings
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty loginProperty() {
        return new SimpleStringProperty(login);
    }

    public StringProperty roleProperty() {
        return new SimpleStringProperty(role != null ? role.name() : "");
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", login=" + login + ", password="
                + password + ", role=" + role + ", typeEtat=" + typeEtat + "]";
    }
}
