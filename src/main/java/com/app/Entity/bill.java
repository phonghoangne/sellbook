package com.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bill")
@Entity
public class bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "invoice_date")
    private Date InvoiceDate ;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    private String note;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<book> book;
    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private  account account;

    @Override
    public String toString() {
        return "bill{" +
                "Id=" + Id +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", InvoiceDate=" + InvoiceDate +
                ", totalAmount=" + totalAmount +
                ", note='" + note + '\'' +
                ", account=" + account +
                '}';
    }
}
