package br.com.erudio.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.model.Book;

public class MockBook {

    public Book mockEntity() {

        return mockEntity(0);
    }

    public BookVO mockVO() {

        return mockVO(0);
    }

    public List<Book> mockEntityList(){
        List<Book> books = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number){
        Book book = new Book();
        book.setId(number);
        book.setTitle("Title book" + number);
        book.setAuthor("Author book" + number);
        book.setPrice(5.0 + number);
        book.setLaunchDate(getDate(number));
        return book;
    }

    public BookVO mockVO(Integer number){
        BookVO book = new BookVO();
        book.setKey(number);
        book.setTitle("Title book" + number);
        book.setAuthor("Author book" + number);
        book.setPrice(5.0 + number);
        book.setLaunchDate(getDate(number));
        return book;
    }

    private Date getDate(int i){
        final long DATE_TIME = 946684800000L; // Fri Dec 31 21:00:00 GFT 1999 -> Date in milliseconds
        final long TWENTY_FOUR_HOUR = 86400000L; // 24 hour in milliseconds
        return new Date(DATE_TIME + (i * TWENTY_FOUR_HOUR));
    }
}
