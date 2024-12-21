package org.example.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 25, unique = true)
    private String surname;

    @Column(length = 25, unique = true)
    private String phone;

    @Column(length = 25, unique = true, name = "adresse")
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    @OneToOne(mappedBy = "client")
    private Paiement paiements;

    @Override
    public String toString() {
        return "Client [id=" + id + ", surname=" + surname + ", phone=" + phone + ", address=" + address + ", user="
                + (user != null ? user.getName() : "PAS DE COMPTE") + "]";
    }
}
