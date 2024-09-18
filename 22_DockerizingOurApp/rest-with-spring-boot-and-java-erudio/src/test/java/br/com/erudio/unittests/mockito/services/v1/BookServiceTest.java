package br.com.erudio.unittests.mockito.services.v1;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.repositores.BookRepository;
import br.com.erudio.service.v1.BookServices;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;


    @InjectMocks
    private BookServices service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book entity = input.mockEntity(1);
        entity.setId(1);

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        var result = service.findById(entity.getId());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Title book" + 1, result.getTitle());
        assertEquals("Author book" + 1, result.getAuthor());
        assertEquals(5.0 + 1, result.getPrice());
        assertEquals(getDate(1), result.getLaunchDate());
    }

    @Test
    void create() {
        Book entity = input.mockEntity(1);
        Book persisted = entity;
        persisted.setId(1);
        BookVO vo = input.mockVO(1);
        vo.setKey(1);
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Title book" + 1, result.getTitle());
        assertEquals("Author book" + 1, result.getAuthor());
        assertEquals(5.0 + 1, result.getPrice());
        assertEquals(getDate(1), result.getLaunchDate());


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
        Book entity = input.mockEntity(1);
        Book persisted = entity;
        persisted.setId(1);
        BookVO vo = input.mockVO(1);
        vo.setKey(1);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Title book" + 1, result.getTitle());
        assertEquals("Author book" + 1, result.getAuthor());
        assertEquals(5.0 + 1, result.getPrice());
        assertEquals(getDate(1), result.getLaunchDate());
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
        Book entity = input.mockEntity(1);
        entity.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        service.delete(1);
    }

    private Date getDate(int i){
        final long DATE_TIME = 946684800000L;
        final long TWENTY_FOUR_HOUR = 86400000L;
        return new Date(DATE_TIME + (i * TWENTY_FOUR_HOUR));
    }
}