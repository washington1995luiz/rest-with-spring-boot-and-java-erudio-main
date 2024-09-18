package br.com.erudio.integrationstests.controller.withxml;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationstests.vo.AccountCredentialsVO;
import br.com.erudio.integrationstests.vo.BookVO;
import br.com.erudio.integrationstests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static XmlMapper xmlMapper;

	private static BookVO book;

	private static Date date;

	@BeforeAll
	public static void setup(){
		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		book = new BookVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		var accessToken = given()
				.basePath("auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body().asString();
								//.as(TokenVO.class)
				//.getAccessToken();
		TokenVO tokenVO = xmlMapper.readValue(accessToken, TokenVO.class);
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.setBasePath("/api/books/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws IOException {
		mockBooks();
		String xml = xmlMapper.writeValueAsString(book);
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.body(xml)
				.when()
				.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		BookVO persistedBook = xmlMapper.readValue(content, new TypeReference<BookVO>() {});
		book = persistedBook;

		assertNotNull(persistedBook);
        assertNotNull(persistedBook.getAuthor());
        assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getLaunchDate());

		assertEquals(book.getKey(), persistedBook.getKey());

		assertEquals("Dom Casmurro", persistedBook.getTitle());
		assertEquals("Machado de Assis", persistedBook.getAuthor());
		assertEquals(50.0, persistedBook.getPrice());
		assertEquals(date, persistedBook.getLaunchDate());

	}

	@Test
	@Order(2)
	public void testFindById() throws IOException {
		mockBooks();
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("id", book.getKey())
				.when()
				.get("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		BookVO persistedBook = xmlMapper.readValue(content, BookVO.class);
		persistedBook.setLaunchDate(date);
		book = persistedBook;

		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getLaunchDate());

		assertEquals(book.getKey(), persistedBook.getKey());

		assertEquals("Dom Casmurro", persistedBook.getTitle());
		assertEquals("Machado de Assis", persistedBook.getAuthor());
		assertEquals(50.0, persistedBook.getPrice());
		assertEquals(date.getTime(), persistedBook.getLaunchDate().getTime());

	}



	@Test
	@Order(3)
	public void testUpdate() throws IOException {
		mockBooks();
		BookVO update = book;
		update.setPrice(80.0);
		String xml = xmlMapper.writeValueAsString(update);
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.body(xml)
				.when()
				.put()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		BookVO persistedBook = xmlMapper.readValue(content, BookVO.class);
		book = persistedBook;

		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getAuthor());
		assertNotNull(persistedBook.getTitle());
		assertNotNull(persistedBook.getLaunchDate());

		assertEquals(book.getKey(), persistedBook.getKey());

		assertEquals("Dom Casmurro", persistedBook.getTitle());
		assertEquals("Machado de Assis", persistedBook.getAuthor());
		assertEquals(80.0, persistedBook.getPrice());
		assertEquals(date, persistedBook.getLaunchDate());

	}

	@Test
	@Order(4)
	public void testDelete() {
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("id", book.getKey())
				.when()
				.delete("{id}")
				.then()
				.statusCode(204);

	}

	@Test
	@Order(5)
	public void testFindAll() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.when()
				.get("/")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		List<BookVO> books = xmlMapper.readValue(content, new TypeReference<List<BookVO>>() {});
		BookVO firstBook = books.getFirst();

		assertNotNull(firstBook.getAuthor());
		assertNotNull(firstBook.getTitle());
		assertNotNull(firstBook.getLaunchDate());

		assertEquals(1, firstBook.getKey());


		assertEquals("Michael C. Feathers", firstBook.getAuthor());
		assertEquals("Working effectively with legacy code", firstBook.getTitle());
		assertEquals(49.00, firstBook.getPrice());

		BookVO lastBook = books.getLast();

		assertNotNull(lastBook.getAuthor());
		assertNotNull(lastBook.getTitle());
		assertNotNull(lastBook.getLaunchDate());

		assertEquals(15, lastBook.getKey());

		assertEquals("Aguinaldo Aragon Fernandes e Vladimir Ferraz de Abreu", lastBook.getAuthor());
		assertEquals("Implantando a governança de TI", lastBook.getTitle());
		assertEquals(54.00, lastBook.getPrice());

	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws IOException {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/books/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		given().spec(specificationWithoutToken)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.when()
				.get("/")
				.then()
				.statusCode(403);


	}


	private void mockBooks() {
		date = new Date();
		book.setAuthor("Machado de Assis");
		book.setTitle("Dom Casmurro");
		book.setPrice(50.0);
		book.setLaunchDate(date);
	}

}
