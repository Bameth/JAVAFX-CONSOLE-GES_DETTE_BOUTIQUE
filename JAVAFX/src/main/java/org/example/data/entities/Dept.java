package org.example.data.entities;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import org.example.data.enums.EtatDette;
import org.example.data.enums.TypeDette;

@Getter
@Setter
@ToString
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

    @OneToMany(mappedBy = "dept")
    private List<Detail> detail;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    public Dept() {
        this.date = LocalDate.now();
        this.detail = new ArrayList<>();
        this.typeDette = typeDette.ENCOURS;
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

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
        updateEtat();
    }
}
