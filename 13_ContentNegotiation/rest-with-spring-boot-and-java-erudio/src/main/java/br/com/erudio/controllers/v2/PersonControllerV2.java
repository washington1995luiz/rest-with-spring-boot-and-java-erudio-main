package br.com.erudio.controllers.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.service.v2.PersonServicesV2;
import br.com.erudio.util.MediaType;

@RestController
@RequestMapping("/api/person/v2")
public class PersonControllerV2 {

    @Autowired
    private PersonServicesV2 service;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public List<PersonVOV2> findAll(){
        return service.findAll();
    }


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/x-yaml"})
    public PersonVOV2 findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }


    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/x-yaml"},produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/x-yaml"})
    public PersonVOV2 create(@RequestBody PersonVOV2 personVO){
        return service.create(personVO);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/x-yaml"},produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "application/x-yaml"})
    public PersonVOV2 update(@RequestBody PersonVOV2 personVO){
        return service.update(personVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();

    }


}