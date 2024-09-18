package br.com.erudio.integrationstests.vo.wrappers.person.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class WrapperPersonVOJson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private PersonEmbeddedVOJson embedded;

    public WrapperPersonVOJson() {}

    public PersonEmbeddedVOJson getEmbedded() {return embedded;}

    public void setEmbedded(PersonEmbeddedVOJson embedded) {this.embedded = embedded;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperPersonVOJson that = (WrapperPersonVOJson) o;
        return Objects.equals(embedded, that.embedded);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embedded);
    }
}
