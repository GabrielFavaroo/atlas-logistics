package br.com.atlas.atlas_logistics.adapters.web.controller;

import br.com.atlas.atlas_logistics.adapters.persistence.ProductRepository;
import br.com.atlas.atlas_logistics.domain.model.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


import io.restassured.matcher.RestAssuredMatchers.*;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;
import java.util.UUID;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private Integer port;

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18-alpine");

    @BeforeAll
    static void start(){
        postgres.start();
    }


    @AfterAll
    static void endUp(){
        postgres.close();
    }


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username",postgres::getUsername);
        registry.add("spring.datasource.password",postgres::getPassword);

    }

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        baseURI = "http://localhost:" + port;
        productRepository.deleteAll();
    }

    @Test
    void save() {
      var product = new Product("Carro","AFKAAO",new BigDecimal("100.00"));


      UUID id = given().contentType(ContentType.JSON).body(product)
              .when().post("/products")
              .then().assertThat()
              .statusCode(201)
              .and().body("product.name", equalTo("Carro"))
              .and().body("product.id", notNullValue()).extract().path("product.id");



      given().when().get("/products/"+ id).then().assertThat().statusCode(200).and().body("name", equalTo("Carro"));


    }


    @Test
    void delete() {
        var product = new Product("Parafuso","HAUIQOI",new BigDecimal("2.0"));
        UUID id = given().contentType(ContentType.JSON).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue())
                .extract().path("product.id");

        given().when().delete("/products/"+id).then().assertThat().statusCode(204);

        when().get("/products/"+id).then().assertThat().statusCode(404);

    }

    @Test
    void update() {
    }

    @Test
    void patch() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getOne() {
    }
}