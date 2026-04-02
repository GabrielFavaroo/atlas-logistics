package br.com.atlas.atlas_logistics.adapters.web.controller;

import br.com.atlas.atlas_logistics.adapters.persistence.ProductRepository;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;
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
import static org.hamcrest.Matchers.*;


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
    void shouldSave() {
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
    void shouldDelete() {
        var product = new Product("Parafuso","HAUIQOI",new BigDecimal("2.0"));
        UUID id = given().contentType(ContentType.JSON).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue())
                .extract().path("product.id");

        given().when().delete("/products/"+id).then().assertThat().statusCode(204);

        when().get("/products/"+id).then().assertThat().statusCode(404);

    }

    @Test
    void shouldUpdate() {
        var product = new Product("Chave de fenda", "HAFASI",new BigDecimal("50.90"));
        UUID id = given().contentType(ContentType.JSON).body(product)
                .when().post("/products")
                .then().body("product.id",notNullValue())
                .extract().path("product.id");

        var newProduct = new Product("Chave Philips", "HAFASI", new BigDecimal("55.00)"));

        given().contentType(ContentType.JSON).body(newProduct)
                .when().put("/products/"+id)
                .then().assertThat().statusCode(200)
                .and().body("product.name",equalTo("Chave Philips")).and().body("product.value", equalTo(55.00));

        given().when().get("/products/"+id)
                .then().assertThat()
                .statusCode(200)
                .and().body("product.name", equalTo("Chave Philips"))
                .and().body("product.value", equalTo(55.00));

    }

    @Test
    void shouldPatch() {

        var product = new Product("Britadeira", "OQOAANA",new BigDecimal("500.00"));

        UUID id = given().contentType(ContentType.JSON).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");

        var newProduct = new PatchProductDTO(null, null, new BigDecimal("350.00"));

        given().contentType(ContentType.JSON).body(newProduct)
                .when().patch("/products/"+id)
                .then().assertThat().statusCode(200)
                .and().body("product.name", equalTo("Britadeira"))
                .and().body("product.sku",equalTo("OQOAANA"))
                .and().body("product.value", equalTo(350.00));

       given().when().get("/products/"+id)
               .then().assertThat().assertThat().statusCode(200)
               .and().body("product.name", equalTo("Britadeira"))
               .and().body("product.sku",equalTo("OQOAANA"))
               .and().body("product.value", equalTo(350.00));

    }
//
//    @Test
//    void shouldGetAll() {
//
//        var product1 = new Product("Makita", "OMAQJ",new BigDecimal("560.00"));
//
//        UUID id1 = given().contentType(ContentType.JSON).body(product1)
//                .when().post("/products")
//                .then().body("product.id", notNullValue()).extract().path("product.id");
//
//        var product2 = new Product("Parafusadeira", "PQOAMA",new BigDecimal("200.00"));
//
//        UUID id2 = given().contentType(ContentType.JSON).body(product2)
//                .when().post("/products")
//                .then().body("product.id", notNullValue()).extract().path("product.id");
//
//        var product3 = new Product("Lixadeira", "OQPQMA",new BigDecimal("670.00"));
//
//        UUID id3 = given().contentType(ContentType.JSON).body(product3)
//                .when().post("/products")
//                .then().body("product.id", notNullValue()).extract().path("product.id");
//
//        given().when().get("/products"+"?page=0&items=2").then().assertThat().body("product", hasSize(2));
//
//    }

    @Test
    void shouldGetOne() {
        var product = new Product("Broca", "OQPEN",new BigDecimal("100.00"));

        UUID id = given().contentType(ContentType.JSON).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");


        given().when().get("/products/"+id).then().assertThat().statusCode(200).and().and().body("product.name", equalTo("Broca"))
                .and().body("product.sku",equalTo("OQPEN"))
                .and().body("product.value", equalTo(100.00));


    }
}