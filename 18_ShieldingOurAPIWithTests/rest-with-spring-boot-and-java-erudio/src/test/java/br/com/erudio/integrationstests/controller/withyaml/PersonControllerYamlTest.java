package br.com.erudio.integrationstests.controller.withyaml;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationstests.vo.AccountCredentialsVO;
import br.com.erudio.integrationstests.vo.PersonVO;
import br.com.erudio.integrationstests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YAMLMapper yamlMapper;

	private static PersonVO person;
	@BeforeAll
	public static void setup(){
		yamlMapper = new YAMLMapper();
		yamlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonVO();
	}

	@Test
	@Order(0)
	public void authorization() throws JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		String yml = mapper.writeValueAsString(user);

		config = config().encoderConfig(
				encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
		);

		var accessToken = given()
				.config(config)
				.basePath("auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(yml)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.asString();
		TokenVO tokenVO = yamlMapper.readValue(accessToken, TokenVO.class);
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
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
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		String yml = mapper.writeValueAsString(person);

		config = config().encoderConfig(
				encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
		);

		var content = given().spec(specification)
				.config(config)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(yml)
				.when()
				.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();


		PersonVO persistedPerson = yamlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}

	@Test
	@Order(2)
	public void testFindById() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.pathParam("id", person.getId())
				.when()
				.get("{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = yamlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}



	@Test
	@Order(3)
	public void testUpdate() throws IOException {

		PersonVO update = person;
		update.setLastName("Piquet Souto Maior");

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		String yml = mapper.writeValueAsString(update);

		config = config().encoderConfig(
				encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
		);


		var content = given().spec(specification)
				.config(config)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.body(yml)
				.when()
				.put()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		PersonVO persistedPerson = yamlMapper.readValue(content, PersonVO.class);
		person = persistedPerson;

		assertNotNull(persistedPerson);
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());

		assertEquals(person.getId(), persistedPerson.getId());

		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
		assertEquals("Brasilia, DF, Brazil", persistedPerson.getAddress());
		assertEquals("Male", persistedPerson.getGender());

	}

	@Test
	@Order(4)
	public void testDelete() {
		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
				.then()
				.statusCode(204);

	}

	@Test
	@Order(5)
	public void testFindAll() throws IOException {

		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.when()
				.get("/")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();

		List<PersonVO> people = yamlMapper.readValue(content, new TypeReference<List<PersonVO>>() {});
		PersonVO firstPerson = people.getFirst();

		assertNotNull(firstPerson.getId());
		assertNotNull(firstPerson.getFirstName());
		assertNotNull(firstPerson.getLastName());
		assertNotNull(firstPerson.getAddress());
		assertNotNull(firstPerson.getGender());

		assertEquals(1, firstPerson.getId());

		assertEquals("Washington", firstPerson.getFirstName());
		assertEquals("Luiz", firstPerson.getLastName());
		assertEquals("Petrolina - PE", firstPerson.getAddress());
		assertEquals("Male", firstPerson.getGender());

		PersonVO lastPerson = people.getLast();

		assertNotNull(lastPerson.getId());
		assertNotNull(lastPerson.getFirstName());
		assertNotNull(lastPerson.getLastName());
		assertNotNull(lastPerson.getAddress());
		assertNotNull(lastPerson.getGender());

		assertEquals(5, lastPerson.getId());

		assertEquals("Neymar", lastPerson.getFirstName());
		assertEquals("jr.", lastPerson.getLastName());
		assertEquals("Santos - SP", lastPerson.getAddress());
		assertEquals("Male", lastPerson.getGender());

	}

	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws IOException {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		given().spec(specificationWithoutToken)
				.contentType(TestConfigs.CONTENT_TYPE_YML)
					.accept(TestConfigs.CONTENT_TYPE_YML)
				.when()
				.get("/")
				.then()
				.statusCode(403);


	}


	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasilia, DF, Brazil");
		person.setGender("Male");
	}

}
