package br.com.atlas.atlas_logistics.adapters.web.restController;

import br.com.atlas.atlas_logistics.adapters.persistence.ProductRepository;
import br.com.atlas.atlas_logistics.adapters.web.dtos.request.product.PatchProductDTO;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.items.Product;
import br.com.atlas.atlas_logistics.domain.model.relationalModels.users.User;
import br.com.atlas.atlas_logistics.infrastructure.persistence.UserRepository;
import br.com.atlas.atlas_logistics.usersForTests.UsersFactory;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ProductRestControllerTest {


    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    static{
        postgres.start();


    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username",postgres::getUsername);
        registry.add("spring.datasource.password",postgres::getPassword);

    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersFactory usersFactory;
    @Autowired
    TokenProviderForTests tokenProviderForTests;



    String adminAccessToken;
    String operatorAccessToken;
    String inventoryAccessToken;
    String auditorAccessToken;


    @BeforeAll
    void start() throws Exception {



        User admintest = usersFactory.createAdmin();
        User operatortest = usersFactory.createOperator();
        User inventorytest = usersFactory.createInventory();
        User auditortest = usersFactory.createAuditor();



        userRepository.saveAll(List.of(admintest,operatortest,inventorytest,auditortest));

        adminAccessToken = tokenProviderForTests.receiveAccessToken(admintest.getUsername(), "1234");
        operatorAccessToken = tokenProviderForTests.receiveAccessToken(operatortest.getUsername(), "1234");
        inventoryAccessToken = tokenProviderForTests.receiveAccessToken(inventorytest.getUsername(), "1234");
        auditorAccessToken =  tokenProviderForTests.receiveAccessToken(auditortest.getUsername(), "1234");


    }


    @AfterAll
    void endUp(){
        userRepository.deleteAll();

    }




    @BeforeEach
    void setUp() {

        productRepository.deleteAll();

    }



    @Test
    void shouldSave() throws Exception {
      var product = new Product("Carro","AFKAAO",new BigDecimal("100.00"));

      String body = objectMapper.writeValueAsString(product);
      MvcResult result = mockMvc.perform(post("/products")
              .header("Authorization","Bearer "+ operatorAccessToken)
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.name").value("Carro"))
              .andExpect(jsonPath("$.id").exists())
              .andReturn()
      ;

      Object resultId = JsonPath.read(result.getResponse().getContentAsString(),"$.id");

//      given().pathParam("id",id).header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/{id}").then().assertThat().statusCode(200).and().body("name", equalTo("Carro"));


    }


    @Test
    void shouldDelete() throws Exception {
        var product = new Product("Parafuso","HAUIQOI",new BigDecimal("2.0"));
        String body = objectMapper.writeValueAsString(product);

//
        MvcResult result = mockMvc.perform(post("/products")
                .header("Authorization","Bearer "+ adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .exists()).andReturn();


        Object resultId = JsonPath.read(result.getResponse().getContentAsString(),"$.id");

        mockMvc.perform(delete("/products/"+ resultId)
                .header("Authorization","Bearer "+ adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(204)).andReturn();
//        given().header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/"+id).then().assertThat().statusCode(404);

    }

    @Test
    void shouldUpdate() throws Exception {
        var product = new Product("Chave de fenda", "HAFASI",new BigDecimal("50.90"));
        String body = objectMapper.writeValueAsString(product);
//

        MvcResult result = mockMvc.perform(post("/products")
                .header("Authorization","Bearer "+ adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists()).andReturn();

        Object resultId = JsonPath.read(result.getResponse().getContentAsString(),"$.id");

        var newProduct = new Product("Chave Philips", "HAFASI", new BigDecimal("55.00"));
        body = objectMapper.writeValueAsString(newProduct);

        mockMvc.perform(put("/products/"+resultId)
                .header("Authorization","Bearer "+ adminAccessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chave Philips"))
                .andExpect(jsonPath("$.value").value(55.00))
        ;


//        given().header("Authorization","Bearer "+ adminAccessToken).when().get("/products/"+id)
//                .then().assertThat()
//                .statusCode(200)
//                .and().body("name", equalTo("Chave Philips"))
//                .and().body("value", equalTo(55.00));

    }

    @Test
    void shouldPatch() throws Exception {

        var product = new Product("Britadeira", "OQOAANA",new BigDecimal("500.00"));
        String body = objectMapper.writeValueAsString(product);

        MvcResult result = mockMvc.perform(post("/products")
                .header("Authorization","Bearer "+ inventoryAccessToken)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()).andReturn();

        Object resultId = JsonPath.read(result.getResponse().getContentAsString(),"$.id");

        var newProduct = new PatchProductDTO(null, null, new BigDecimal("350.00"));
        body = objectMapper.writeValueAsString(newProduct);

        mockMvc.perform(patch("/products/"+resultId)
                .header("Authorization","Bearer "+ inventoryAccessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Britadeira"))
                .andExpect(jsonPath("$.sku").value("OQOAANA"))
                .andExpect(jsonPath("$.value").value(350.00));



//       given().header("Authorization","Bearer "+ auditorAccessToken).when().get("/products/"+id)
//               .then().assertThat().assertThat().statusCode(200)
//               .and().body("name", equalTo("Britadeira"))
//               .and().body("sku",equalTo("OQOAANA"))
//               .and().body("value", equalTo(350.00));

    }


}