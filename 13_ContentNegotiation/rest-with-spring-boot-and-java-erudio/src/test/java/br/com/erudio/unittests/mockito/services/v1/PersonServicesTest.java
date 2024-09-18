package br.com.erudio.unittests.mockito.services.v1;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Person;
import br.com.erudio.repositores.PersonRepository;
import br.com.erudio.service.v1.PersonServices;
import br.com.erudio.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Person> list = input.mockEntityList();

        when(personRepository.findAll()).thenReturn(list);
        var people = service.findAll();
        assertEquals(14, people.size());

        for(int i = 0; i < people.size(); i++){
            assertNotNull(people);
            assertNotNull(people.get(i).getKey());
            assertNotNull(people.get(i).getLinks());
            assertTrue(people.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
            assertEquals("Addres Test" + i, people.get(i).getAddress());
            assertEquals("First Name Test" + i, people.get(i).getFirstName());
            assertEquals("Last Name Test" + i, people.get(i).getLastName());

            assertEquals(i%2==0 ? "Male" : "Female", people.get(i).getGender());
        }

    }

    @Test
    void findById() {
        final int ID_PERSON = 1;
        Person entity = input.mockEntity(ID_PERSON);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test" + ID_PERSON, result.getAddress());
        assertEquals("First Name Test" + ID_PERSON, result.getFirstName());
        assertEquals("Last Name Test" + ID_PERSON, result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void create() {
        final int ID_PERSON = 1;
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(personRepository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test" + ID_PERSON, result.getAddress());
        assertEquals("First Name Test" + ID_PERSON, result.getFirstName());
        assertEquals("Last Name Test" + ID_PERSON, result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    void update() {
        final int ID_PERSON = 1;
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        Person persisted = entity;
        persisted.setId(1L);
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test" + ID_PERSON, result.getAddress());
        assertEquals("First Name Test" + ID_PERSON, result.getFirstName());
        assertEquals("Last Name Test" + ID_PERSON, result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It's not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void delete() {
        final int ID_PERSON = 1;
        Person entity = input.mockEntity(ID_PERSON);
        entity.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
    }
}