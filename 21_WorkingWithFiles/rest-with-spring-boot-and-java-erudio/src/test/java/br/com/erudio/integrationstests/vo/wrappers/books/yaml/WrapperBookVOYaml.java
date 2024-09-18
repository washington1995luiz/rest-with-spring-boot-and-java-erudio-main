package br.com.erudio.integrationstests.vo.wrappers.books.yaml;

import br.com.erudio.integrationstests.vo.BookVO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class WrapperBookVOYaml implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    List<BookVO> content;

    public WrapperBookVOYaml() {
    }

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
        WrapperBookVOYaml that = (WrapperBookVOYaml) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
