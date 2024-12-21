package org.example.data.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @OneToOne
    @JoinColumn(name = "client_id")
    public Client client;
    public double Montant;
    public LocalDateTime DatePaiement;
    @ManyToOne
    @JoinColumn(name = "dept_id")
    public Dept dept;

    public Paiement() {
        DatePaiement = LocalDateTime.now();
    }
}
