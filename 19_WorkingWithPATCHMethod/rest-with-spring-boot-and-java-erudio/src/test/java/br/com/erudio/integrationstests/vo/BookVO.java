package br.com.erudio.integrationstests.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "launch_date", "price", "title"})
@XmlRootElement
public class BookVO extends RepresentationModel<BookVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private int key;

    @JsonProperty
    private String author;

    @JsonProperty
    private double price;

    @JsonProperty("launch_date")
    private Date launchDate;

    @JsonProperty
    private String title;


    @JsonIgnore
    private List<Link> links;

    public BookVO() {
    }



    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
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
        if (!super.equals(o)) return false;
        BookVO booksVO = (BookVO) o;
        return key == booksVO.key && Double.compare(price, booksVO.price) == 0 && Objects.equals(author, booksVO.author) && Objects.equals(launchDate, booksVO.launchDate) && Objects.equals(title, booksVO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, author, price, launchDate, title);
    }
}
