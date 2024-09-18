package br.com.erudio.integrationstests.controller.withxml;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationstests.vo.AccountCredentialsVO;
import br.com.erudio.integrationstests.vo.PersonVO;
import br.com.erudio.integrationstests.vo.TokenVO;
import br.com.erudio.integrationstests.vo.wrappers.person.xml.WrapperPersonVOXml;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static XmlMapper xmlMapper;

	private static PersonVO person;

	@BeforeAll
	public static void setup(){
		xmlMapper = new XmlMapper();
		xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}

	@Test
	@Order(0)
	public void authorization() {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		var accessToken = given()
				.basePath("auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_XML)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenVO.class)
				.getAccessToken();
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws IOException {
		mockPerson();

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.body(person)
				.when()
				.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		PersonVO persistedPerson = xmlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}

	@Test
	@Order(2)
	public void testUpdate() throws IOException {

		PersonVO update = person;
		update.setLastName("Piquet Souto Maior");

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.body(update)
				.when()
				.put()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = xmlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}

	@Test
	@Order(3)
	public void testDisablePersonById() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("id", person.getId())
				.when()
				.patch("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = xmlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}

	@Test
	@Order(4)
	public void testFindById() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("id", person.getId())
				.when()
				.get("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = xmlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}





	@Test
	@Order(5)
	public void testDelete() {
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
				.then()
				.statusCode(204);

	}

	@Test
	@Order(6)
	public void testFindAll() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "size", 12, "direction", "asc")
				.when()
				.get("/")
				.then()
				.statusCode(200)
				.extract()
				.body().asString();


		WrapperPersonVOXml wrapper = xmlMapper.readValue(content, WrapperPersonVOXml.class);
		var people = wrapper.getContent().getContent();
		PersonVO firstPerson = people.getFirst();


		assertNotNull(firstPerson.getId());
		assertNotNull(firstPerson.getFirstName());
		assertNotNull(firstPerson.getLastName());
		assertNotNull(firstPerson.getAddress());
		assertNotNull(firstPerson.getGender());
		assertTrue(firstPerson.getEnabled());

		assertEquals(428, firstPerson.getId());

		assertEquals("Abagael", firstPerson.getFirstName());
		assertEquals("Eronie", firstPerson.getLastName());
		assertEquals("22453 Ludington Parkway", firstPerson.getAddress());
		assertEquals("Female", firstPerson.getGender());

		PersonVO lastPerson = people.getLast();

		assertNotNull(lastPerson.getId());
		assertNotNull(lastPerson.getFirstName());
		assertNotNull(lastPerson.getLastName());
		assertNotNull(lastPerson.getAddress());
		assertNotNull(lastPerson.getGender());
		assertFalse(lastPerson.getEnabled());


		assertEquals(500, lastPerson.getId());

		assertEquals("Albertine", lastPerson.getFirstName());
		assertEquals("Dewicke", lastPerson.getLastName());
		assertEquals("67936 Macpherson Place", lastPerson.getAddress());
		assertEquals("Female", lastPerson.getGender());

	}

	@Test
	@Order(7)
	public void testFindAllWithoutToken() {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
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

	@Test
	@Order(8)
	public void testFindPersonsByFirstName() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("firstName", "Alexandro")
				.queryParams("page", 0, "size", 6, "direction", "asc")
				.when()
				.get("/findPersonByName/{firstName}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		WrapperPersonVOXml wrapper = xmlMapper.readValue(content, WrapperPersonVOXml.class);
		var people = wrapper.getContent().getContent();

		PersonVO firstPerson = people.getFirst();

		assertNotNull(firstPerson.getId());
		assertNotNull(firstPerson.getFirstName());
		assertNotNull(firstPerson.getLastName());
		assertNotNull(firstPerson.getAddress());
		assertNotNull(firstPerson.getGender());
		assertFalse(firstPerson.getEnabled());

		assertEquals(569, firstPerson.getId());

		assertEquals("Alexandro", firstPerson.getFirstName());
		assertEquals("Cutriss", firstPerson.getLastName());
		assertEquals("7 Sloan Plaza", firstPerson.getAddress());
		assertEquals("Male", firstPerson.getGender());


	}

	@Test
	@Order(9)
	public void testHATEOAS() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "size", 12, "direction", "asc")
				.when()
				.get("/")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/428</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/483</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/231</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/472</href></links>"));

		assertTrue(content.contains("<page><size>12</size><totalElements>1003</totalElements><totalPages>84</totalPages><number>0</number></page>"));

		assertTrue(content.contains("<links><rel>first</rel><href>http://localhost:8888/api/person/v1/?direction=asc&amp;page=0&amp;size=12&amp;sort=firstName,asc</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/?page=0&amp;size=12&amp;direction=asc</href></links>"));
		assertTrue(content.contains("<links><rel>next</rel><href>http://localhost:8888/api/person/v1/?direction=asc&amp;page=1&amp;size=12&amp;sort=firstName,asc</href></links>"));
		assertTrue(content.contains("<links><rel>last</rel><href>http://localhost:8888/api/person/v1/?direction=asc&amp;page=83&amp;size=12&amp;sort=firstName,asc</href></links>"));

	}

	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasilia, DF, Brazil");
		person.setGender("Male");
		person.setEnabled(true);
	}

}
