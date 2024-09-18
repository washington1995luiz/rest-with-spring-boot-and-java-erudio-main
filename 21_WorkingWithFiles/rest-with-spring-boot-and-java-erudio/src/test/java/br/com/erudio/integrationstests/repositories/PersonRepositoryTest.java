package br.com.erudio.integrationstests.repositories;

import br.com.erudio.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import br.com.erudio.repositores.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    private static Person person;

    @BeforeAll
    public static void setup(){
        person = new Person();
    }

    @Test
    @Order(1)
    public void testFindPersonsByFirstName() {

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPersonsByName("Alexandros", pageable).getContent().getFirst();

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertTrue(person.getEnabled());

        assertEquals(270, person.getId());

        assertEquals("Alexandros", person.getFirstName());
        assertEquals("Greggor", person.getLastName());
        assertEquals("446 Steensland Junction", person.getAddress());
        assertEquals("Male", person.getGender());


    }

    @Test
    @Order(2)
    public void disablePerson() {

        repository.disablePerson(person.getId());

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPersonsByName("Alexandros", pageable).getContent().getFirst();

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertFalse(person.getEnabled());

        assertEquals(270, person.getId());

        assertEquals("Alexandros", person.getFirstName());
        assertEquals("Greggor", person.getLastName());
        assertEquals("446 Steensland Junction", person.getAddress());
        assertEquals("Male", person.getGender());


    }
}
