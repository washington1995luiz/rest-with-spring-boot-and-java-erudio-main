package br.com.erudio.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.model.Book;
import org.modelmapper.ModelMapper;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.model.Person;

//import com.github.dozermapper.core.DozerBeanMapperBuilder;
//import com.github.dozermapper.core.Mapper;

public class DozerMapper {
    //private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper
            .createTypeMap(Person.class,PersonVO.class)
            .addMapping(Person::getId, PersonVO::setKey);
        mapper
            .createTypeMap(PersonVO.class,Person.class)
            .addMapping(PersonVO::getKey, Person::setId);

        mapper
            .createTypeMap(Person.class,PersonVOV2.class)
            .addMapping(Person::getId, PersonVOV2::setKey);
        mapper
            .createTypeMap(PersonVOV2.class,Person.class)
            .addMapping(PersonVOV2::getKey, Person::setId);

        mapper
            .createTypeMap(Book.class, BookVO.class)
                .addMapping(Book::getId, BookVO::setKey);
        mapper
            .createTypeMap(BookVO.class, Book.class)
            .addMapping(BookVO::getKey, Book::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<D>();
        for(O o : origin){
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
