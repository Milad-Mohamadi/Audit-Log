package com.miladovsky.money_manager.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "person")
public class Person {

    @Id
    private String id;
    private double amount;
    private LocalDate date;
    private String source;


    public Person(double amount, LocalDate date, String source) {
        this.amount = amount;
        this.date = date;
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
