package br.com.erudio.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 180)
    private String author;

    @Column(nullable = false)
    private double price;

    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date launchDate;

    @Column(nullable = false, length = 250)
    private String title;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book books = (Book) o;
        return id == books.id && Double.compare(price, books.price) == 0 && Objects.equals(author, books.author) && Objects.equals(launchDate, books.launchDate) && Objects.equals(title, books.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, price, launchDate, title);
    }
}
