package br.com.atlas.atlas_logistics.adapters.web.controller;

import br.com.atlas.atlas_logistics.TokenProviderForTests;
import br.com.atlas.atlas_logistics.adapters.persistence.ProductRepository;
import br.com.atlas.atlas_logistics.adapters.web.controller.dtos.request.product.PatchProductDTO;
import br.com.atlas.atlas_logistics.domain.model.Product;
import br.com.atlas.atlas_logistics.domain.model.User;
import br.com.atlas.atlas_logistics.infrastructure.persistence.UserRepository;
import br.com.atlas.atlas_logistics.usersForTests.UsersFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.matcher.RestAssuredMatchers.*;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {


    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    static {
        postgres.start();
    }
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username",postgres::getUsername);
        registry.add("spring.datasource.password",postgres::getPassword);

    }


    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersFactory usersFactory;
    @Autowired
    TokenProviderForTests tokenProviderForTests;
    @LocalServerPort
    private Integer port;

    String adminAccessToken;
    String operatorAccessToken;
    String inventoryAccessToken;
    String auditorAccessToken;


    @BeforeAll
    void start(){

        //CREATE TABLE users(
        //    id uuid PRIMARY KEY NOT NULL,
        //    username VARCHAR(100) NOT NULL UNIQUE,
        //    email VARCHAR(150) NOT NULL UNIQUE,
        //    password VARCHAR(255) NOT NULL,
        //    enabled BOOLEAN NOT NULL DEFAULT TRUE,
        //    created_at TIMESTAMP NOT NULL DEFAULT now(),
        //    updated_at TIMESTAMP NOT NULL DEFAULT now()
        //
        //);





        User admintest = usersFactory.createAdmin();
        User operatortest = usersFactory.createOperator();
        User inventorytest = usersFactory.createInventory();
        User auditortest = usersFactory.createAuditor();


        userRepository.save(admintest);
        userRepository.save(operatortest);
        userRepository.save(inventorytest);
        userRepository.save(auditortest);

        adminAccessToken = tokenProviderForTests.receiveAccessToken(admintest.getUsername(), admintest.getPassword());
        operatorAccessToken = tokenProviderForTests.receiveAccessToken(operatortest.getUsername(), operatortest.getPassword());
        inventoryAccessToken = tokenProviderForTests.receiveAccessToken(inventorytest.getUsername(), inventorytest.getPassword());
        auditorAccessToken =  tokenProviderForTests.receiveAccessToken(auditortest.getUsername(), auditortest.getPassword());


    }


    @AfterAll
    void endUp(){
        userRepository.deleteAll();

    }




    @BeforeEach
    void setUp() {
        baseURI = "http://localhost:" + port;
        productRepository.deleteAll();

    }



    @Test
    void shouldSave() {
      var product = new Product("Carro","AFKAAO",new BigDecimal("100.00"));


      UUID id = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ operatorAccessToken).body(product)
              .when().post("/products")
              .then().assertThat()
              .statusCode(201)
              .and().body("product.name", equalTo("Carro"))
              .and().body("product.id", notNullValue()).extract().path("product.id");



      given().contentType(ContentType.JSON).header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/"+ id).then().assertThat().statusCode(200).and().body("name", equalTo("Carro"));


    }


    @Test
    void shouldDelete() {
        var product = new Product("Parafuso","HAUIQOI",new BigDecimal("2.0"));
        UUID id = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ adminAccessToken).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue())
                .extract().path("product.id");

        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ adminAccessToken).when().delete("/products/"+id).then().assertThat().statusCode(204);

        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/"+id).then().assertThat().statusCode(404);

    }

    @Test
    void shouldUpdate() {
        var product = new Product("Chave de fenda", "HAFASI",new BigDecimal("50.90"));
        UUID id = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ adminAccessToken).body(product)
                .when().post("/products")
                .then().body("product.id",notNullValue())
                .extract().path("product.id");

        var newProduct = new Product("Chave Philips", "HAFASI", new BigDecimal("55.00)"));

        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ adminAccessToken).body(newProduct)
                .when().put("/products/"+id)
                .then().assertThat().statusCode(200)
                .and().body("product.name",equalTo("Chave Philips")).and().body("product.value", equalTo(55.00));

        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ adminAccessToken).when().get("/products/"+id)
                .then().assertThat()
                .statusCode(200)
                .and().body("product.name", equalTo("Chave Philips"))
                .and().body("product.value", equalTo(55.00));

    }

    @Test
    void shouldPatch() {

        var product = new Product("Britadeira", "OQOAANA",new BigDecimal("500.00"));

        UUID id = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ inventoryAccessToken).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");

        var newProduct = new PatchProductDTO(null, null, new BigDecimal("350.00"));

        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ inventoryAccessToken).body(newProduct)
                .when().patch("/products/"+id)
                .then().assertThat().statusCode(200)
                .and().body("product.name", equalTo("Britadeira"))
                .and().body("product.sku",equalTo("OQOAANA"))
                .and().body("product.value", equalTo(350.00));

       given().contentType(ContentType.JSON).header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/"+id)
               .then().assertThat().assertThat().statusCode(200)
               .and().body("product.name", equalTo("Britadeira"))
               .and().body("product.sku",equalTo("OQOAANA"))
               .and().body("product.value", equalTo(350.00));

    }

    @Test
    void shouldGetAll() {

        var product1 = new Product("Makita", "OMAQJ",new BigDecimal("560.00"));

        UUID id1 = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ operatorAccessToken).body(product1)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");

        var product2 = new Product("Parafusadeira", "PQOAMA",new BigDecimal("200.00"));

        UUID id2 = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ operatorAccessToken).body(product2)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");

        var product3 = new Product("Lixadeira", "OQPQMA",new BigDecimal("670.00"));

        UUID id3 = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ operatorAccessToken).body(product3)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");

        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ auditorAccessToken).when().get("/products"+"?page=0&items=2").then().assertThat().body("product", hasSize(2));

    }

    @Test
    void shouldGetOne() {
        var product = new Product("Broca", "OQPEN",new BigDecimal("100.00"));

        UUID id = given().contentType(ContentType.JSON).header("Authorization","Bearer "+ operatorAccessToken).body(product)
                .when().post("/products")
                .then().body("product.id", notNullValue()).extract().path("product.id");


        given().contentType(ContentType.JSON).header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/"+id).then().assertThat().statusCode(200).and().and().body("product.name", equalTo("Broca"))
                .and().body("product.sku",equalTo("OQPEN"))
                .and().body("product.value", equalTo(100.00));


    }
}