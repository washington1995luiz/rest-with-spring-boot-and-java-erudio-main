package br.com.erudio.service.v2;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import br.com.erudio.controllers.v2.PersonControllerV2;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositores.PersonRepository;

@Service
public class PersonServicesV2 {
    private Logger logger = Logger.getLogger(PersonServicesV2.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    public List<PersonVOV2> findAll(){
        logger.info("Finding all people!");
        var persons =  DozerMapper.parseListObjects(repository.findAll(), PersonVOV2.class);
        persons
            .stream()
            .forEach(p -> p.add(linkTo(methodOn(PersonControllerV2.class).findById(p.getKey())).withSelfRel()));;
        return persons;
    }

    public PersonVOV2 findById(Long id){
        logger.info("Finding one person!");
        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVOV2.class);
        vo.add(linkTo(methodOn(PersonControllerV2.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVOV2 findByIdV2(Long id){
        logger.info("Finding one person!");
        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, PersonVOV2.class);
        vo.add(linkTo(methodOn(PersonControllerV2.class).findById(id)).withSelfRel());
        return vo;
    }
    
    public PersonVOV2 create(PersonVOV2 person){
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVOV2.class);
        vo.add(linkTo(methodOn(PersonControllerV2.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person){
        logger.info("Creating one person!");
        var entity = mapper.convertVoToEntity(person);
        var vo = mapper.converEntityToVo(repository.save(entity));
        vo.add(linkTo(methodOn(PersonControllerV2.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 update(PersonVOV2 person){
        logger.info("Updating one person!");
        Person entity = repository.findById(person.getKey())
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo = DozerMapper.parseObject(repository.save(entity), PersonVOV2.class);
        vo.add(linkTo(methodOn(PersonControllerV2.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(Long id){
        logger.info("Deleting one person!");
        var entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
