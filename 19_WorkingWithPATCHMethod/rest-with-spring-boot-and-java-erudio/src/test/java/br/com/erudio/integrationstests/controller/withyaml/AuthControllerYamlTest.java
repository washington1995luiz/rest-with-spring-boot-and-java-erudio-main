package br.com.erudio.integrationstests.controller.withyaml;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationstests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationstests.vo.AccountCredentialsVO;
import br.com.erudio.integrationstests.vo.TokenVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;

    @Test
    @Order(1)
    public void testSignin() throws IOException {

        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String yml = mapper.writeValueAsString(user);

        config = config().encoderConfig(
                encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
        );

        tokenVO = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .config(config)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .body(yml)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);


        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());

    }

    @Test
    @Order(2)
    public void restRefresh() {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
        config = config().encoderConfig(
                encoderConfig().encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)
        );
        var newTokenVO = given()
                .basePath("/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .config(config)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("username", tokenVO.getUsername())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                .put("{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class);
        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());

    }
}
