package com.app.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="account")
@Entity
public class account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private Boolean gender;
    @Column(columnDefinition = "Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    @Override
    public String toString() {
        return "account{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gender=" + gender +
                ", birthDay=" + birthDay +
                '}';
    }
}
