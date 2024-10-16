package com.app.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    accountLoginRequest {
    private String username;
    private String password;
    private boolean remember = false;

    @Override
    public String toString() {
        return "accountLoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
