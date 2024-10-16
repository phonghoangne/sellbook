package com.app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Table(name="cart")
@Entity
public class cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "id")
    private book book;
    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private account account;
    private BigDecimal totalProduct;
    private BigDecimal totalCart;

    public cart(){};

    public cart(Integer id, Integer quantity, com.app.Entity.book book, com.app.Entity.account account) {
        Id = id;
        this.quantity = quantity;
        this.book = book;
        this.account = account;
        caculateTotalProduct();
    }
    public void caculateTotalProduct(){
        this.totalProduct = book.getPrice().multiply(new BigDecimal(quantity));

    }

    @Override
    public String toString() {
        return "cart{" +
                "Id=" + Id +
                ", quantity=" + quantity +
                ", book=" + book +
                ", account=" + account +
                ", totalProduct=" + totalProduct +
                ", totalCart=" + totalCart +
                '}';
    }
}
