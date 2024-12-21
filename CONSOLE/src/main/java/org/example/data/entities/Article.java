package org.example.data.entities;

import java.util.List;

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

    public void generateReference() {
        this.reference = generateNumero(this.id, "ART"); 
    }

    public String generateNumero(int nbre, String format) {
        int size = String.valueOf(nbre).length();
        return format + "0".repeat((4 - size) < 0 ? 0 : 4 - size) + nbre;
    }
}
