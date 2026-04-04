package br.com.atlas.atlas_logistics;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class TokenProviderForTests {

    public String receiveAccessToken(String username, String Password){
        return given().contentType(ContentType.JSON).body(username, ObjectMapperType.valueOf(Password))
                .when().post("/auth/signin")
                .then().assertThat().body("accessToken",notNullValue()).extract().path("accessToken");

    }
}
