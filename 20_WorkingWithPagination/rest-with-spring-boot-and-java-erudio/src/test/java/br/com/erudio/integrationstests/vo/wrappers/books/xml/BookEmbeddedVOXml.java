package br.com.erudio.integrationstests.vo.wrappers.books.xml;

import br.com.erudio.integrationstests.vo.BookVO;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
@XmlRootElement
public class BookEmbeddedVOXml implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JacksonXmlElementWrapper(useWrapping = false) // remove o wrapper de lista no XML
    @JacksonXmlProperty(localName = "content")
    private List<BookVO> content;

    public BookEmbeddedVOXml(){}

    public List<BookVO> getContent() {
        return content;
    }

    public void setContent(List<BookVO> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEmbeddedVOXml that = (BookEmbeddedVOXml) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
