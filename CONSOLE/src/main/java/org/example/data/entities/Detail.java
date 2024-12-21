package org.example.data.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "detail")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int qte;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Dept dept;

    public Detail() {
    }
    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", qte=" + qte +
                ", article=" + (article != null ? article.getLibelle() : "null") +
                ", client=" + (dept != null ? dept.getClient().getUser().getName() : "null") +
                '}';
    }
}
