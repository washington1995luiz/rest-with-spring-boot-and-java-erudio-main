package br.com.erudio.integrationstests.vo.wrappers.books.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class WrapperBookVOJson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BookEmbeddedVOJson embedded;

    public WrapperBookVOJson(){}

    public BookEmbeddedVOJson getEmbedded() {
        return embedded;
    }

    public void setEmbedded(BookEmbeddedVOJson embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperBookVOJson that = (WrapperBookVOJson) o;
        return Objects.equals(embedded, that.embedded);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embedded);
    }
}
