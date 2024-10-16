package com.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="book")
@Entity
public class book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String name;
    private BigDecimal price;
    @Column(name = "publication_date" , columnDefinition = "Date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date publicationDate;
    @Column(name="number_pages")
    private Integer numberPages;
    @Column(name="publishing_company")
    private String publishingCompany;
    private String author;
    private String img;
    @Column(columnDefinition = "Longtext")
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "Id")
    private category category;
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private bill bill;
    @Override
    public String toString() {
        return "book{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", publicationDate=" + publicationDate +
                ", numberPages=" + numberPages +
                ", publishingCompany='" + publishingCompany + '\'' +
                ", author='" + author + '\'' +
                ", category_id=" + category +
                '}';
    }
}
