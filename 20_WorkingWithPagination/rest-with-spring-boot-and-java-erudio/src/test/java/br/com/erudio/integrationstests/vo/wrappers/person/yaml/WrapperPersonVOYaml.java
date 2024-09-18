package br.com.erudio.integrationstests.vo.wrappers.person.yaml;

import br.com.erudio.integrationstests.vo.PersonVO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class WrapperPersonVOYaml implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<PersonVO> content;

    public WrapperPersonVOYaml() {
    }

    public List<PersonVO> getContent() {
        return content;
    }

    public void setContent(List<PersonVO> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperPersonVOYaml that = (WrapperPersonVOYaml) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
