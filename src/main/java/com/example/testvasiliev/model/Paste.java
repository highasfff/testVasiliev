package com.example.testvasiliev.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "paste")
public class Paste {
    @Id
    @Column(name="hash")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hash;
    @Column(name="text")
    private String text;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="modificator")
    private Modificator modificator;
    @Column(name = "expiration_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expirationTime;
    @Transient
    private boolean isExpired;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person author;

    public Paste() {
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Modificator getModificator() {
        return modificator;
    }

    public void setModificator(Modificator modificator) {
        this.modificator = modificator;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author.getFullname();
    }

    @Override
    public String toString() {
        return "Paste{" +
                "hash=" + hash +
                ", text='" + text + '\'' +
                ", modificator=" + modificator +
                ", expirationTime=" + expirationTime.toString() +
                ", author=" + author +
                '}';
    }
}
