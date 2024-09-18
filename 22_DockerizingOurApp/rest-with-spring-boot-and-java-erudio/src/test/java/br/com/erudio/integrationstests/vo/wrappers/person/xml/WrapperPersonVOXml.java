package br.com.erudio.integrationstests.vo.wrappers.person.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "PagedModel")
public class WrapperPersonVOXml implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "content")
    private PersonEmbeddedVOXml content;

    public WrapperPersonVOXml() {}

    public PersonEmbeddedVOXml getContent() {
        return content;
    }

    public void setContent(PersonEmbeddedVOXml content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperPersonVOXml that = (WrapperPersonVOXml) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
