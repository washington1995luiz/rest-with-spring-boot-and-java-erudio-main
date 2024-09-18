package br.com.erudio.data.vo.v1;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "firstName", "lastName", "address", "gender", "birthDay" })
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    private Long key;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty   
    private String address;
    @JsonProperty
    private String gender;
    private Boolean enabled;


    public PersonVO(){
        //Constructor empty to be called in others classes
    }

    public Long getKey() {
        return key;
    }


    public void setKey(Long key) {
        this.key = key;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonVO personVO = (PersonVO) o;
        return Objects.equals(key, personVO.key) && Objects.equals(firstName, personVO.firstName) && Objects.equals(lastName, personVO.lastName) && Objects.equals(address, personVO.address) && Objects.equals(gender, personVO.gender) && Objects.equals(enabled, personVO.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, firstName, lastName, address, gender, enabled);
    }
}
