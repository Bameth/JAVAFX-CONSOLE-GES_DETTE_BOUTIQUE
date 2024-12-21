package org.example.data.entities;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "dept")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double montant;
    private double montantVerser = 0;
    private double montantRestant;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private EtatDette etat;

    @Enumerated(EnumType.STRING)
    private TypeDette typeDette;

    @OneToMany(mappedBy = "dept", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detail> detail;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    @OneToMany(mappedBy = "dept", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiements;

    public Dept() {
        this.date = LocalDate.now();
        this.detail = new ArrayList<>();
        updateType();
        updateEtat();
    }

    public List<Detail> getDetail() {
        if (this.detail == null) {
            this.detail = new ArrayList<>();
        }
        return this.detail;
    }

    public void updateEtat() {
        if (this.montantRestant > 0) {
            this.etat = EtatDette.NONSOLDEES;
        } else {
            this.etat = EtatDette.SOLDEES;
        }
    }
    public void updateType() {
        if (this.montantRestant > 0) {
            this.typeDette = TypeDette.ENCOURS;
        } else {
            this.typeDette = TypeDette.ACCEPTER;
        }
    }

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
        updateEtat();
        updateType();
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", montant=" + montant +
                ", montantVerser=" + montantVerser +
                ", montantRestant=" + montantRestant +
                ", date=" + date +
                ", etat=" + etat +
                ", typeDette=" + typeDette +
                ", client=" + (client != null ? client.getUser().getName() : "null") +
                '}';
    }
}
