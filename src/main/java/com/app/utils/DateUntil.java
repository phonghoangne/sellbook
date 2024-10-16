package com.app.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class DateUntil {
    @Autowired
    HttpServletRequest request;
    public Date getDate(String name, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String temp = request.getParameter(name);
            Date date = formatter.parse(temp);
            return date;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
