package br.com.erudio.integrationstests.vo.wrappers.books.json;

import br.com.erudio.integrationstests.vo.BookVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BookEmbeddedVOJson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("bookVOList")
    private List<BookVO> books;

    public BookEmbeddedVOJson(){}

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEmbeddedVOJson that = (BookEmbeddedVOJson) o;
        return Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(books);
    }
}
