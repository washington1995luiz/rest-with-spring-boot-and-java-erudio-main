package br.com.erudio.integrationstests.vo.wrappers.person.json;

import br.com.erudio.integrationstests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PersonEmbeddedVOJson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("personVOList")
    private List<PersonVO> persons;

    public PersonEmbeddedVOJson() {}


    public List<PersonVO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonVO> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEmbeddedVOJson that = (PersonEmbeddedVOJson) o;
        return Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(persons);
    }
}
