package org.example.data.entities;

import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "detail")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(length = 25, unique = true)
    private String reference;

    @Column(length = 25, unique = true)
    private String libelle;

    private double prix;

    private int qteStock;

    @OneToMany(mappedBy = "article")
    private List<Detail> detail;

    // Propriétés JavaFX
    public SimpleStringProperty referenceProperty() {
        return new SimpleStringProperty(reference);
    }

    public SimpleStringProperty libelleProperty() {
        return new SimpleStringProperty(libelle);
    }

    public SimpleDoubleProperty prixProperty() {
        return new SimpleDoubleProperty(prix);
    }

    public SimpleIntegerProperty qteStockProperty() {
        return new SimpleIntegerProperty(qteStock);
    }

    public void generateReference() {
        this.reference = generateNumero(this.id, "ART"); 
    }

    public String generateNumero(int nbre, String format) {
        int size = String.valueOf(nbre).length();
        return format + "0".repeat((4 - size) < 0 ? 0 : 4 - size) + nbre;
    }
}
