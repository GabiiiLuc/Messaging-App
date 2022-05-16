package com.example.proiectextins.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message extends Entity<Long>{
    private String from;
    private String to;
    private String message;
    private Date date;

    public Message(String from, String to, String message, Date date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public Message(String from, String to, String message){
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = java.sql.Date.valueOf(String.valueOf(LocalDateTime.now()));
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return  "|from: " + this.from + "\n" +
                "|to: " + this.to + "\n" +
                "| -> " + this.message + "\n" +
                "|____________________________\n"+
                "| at: " + this.date + "\n" +
                "|____________________________";
    }
}
