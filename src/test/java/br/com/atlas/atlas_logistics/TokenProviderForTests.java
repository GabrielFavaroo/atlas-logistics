package br.com.atlas.atlas_logistics;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.stereotype.Component;
import org.springframework.test.context.DynamicPropertySource;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.notNullValue;

@Component
public class TokenProviderForTests {


    public String receiveAccessToken(String username, String Password){

        HashMap<String,String> bodyContent = new HashMap<>();
        bodyContent.put("username",username);
        bodyContent.put("password",Password);
        return given().port(port).contentType(ContentType.JSON).body(bodyContent)
                .when().post("/auth/signin")
                .then().log().all().assertThat().body("accessToken",notNullValue()).extract().path("accessToken");

    }
}
