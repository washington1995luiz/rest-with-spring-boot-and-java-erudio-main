package br.com.erudio.integrationstests.vo.wrappers.books.xml;

import br.com.erudio.integrationstests.vo.wrappers.books.xml.BookEmbeddedVOXml;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
@XmlRootElement(name = "PagedModel")
public class WrapperBookVOXml implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "content")
    private BookEmbeddedVOXml content;

    public WrapperBookVOXml(){}

    public BookEmbeddedVOXml getContent() {
        return content;
    }

    public void setContent(BookEmbeddedVOXml content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperBookVOXml that = (WrapperBookVOXml) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
