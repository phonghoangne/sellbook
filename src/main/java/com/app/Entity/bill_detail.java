package com.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bill_detail")
@Entity
public class bill_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "id_bill")
    private bill bill;
    @ManyToOne
    @JoinColumn(name = "id_book")
    private book book;
    @Column(name ="quantity")
    private Integer qty;
    @Column(name = "total_product")
    private BigDecimal totalProduct;

    @Override
    public String toString() {
        return "bill_book{" +
                "Id=" + Id +
                ", bill=" + bill +
                ", book=" + book +
                ", qty=" + qty +
                ", totalProduct=" + totalProduct +
                "} /n";
    }
}
