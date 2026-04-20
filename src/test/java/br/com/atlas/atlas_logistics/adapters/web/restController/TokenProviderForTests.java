package br.com.atlas.atlas_logistics.adapters.web.restController;


import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Component
@AutoConfigureMockMvc
@SpringBootTest
public class TokenProviderForTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public String receiveAccessToken(String username, String Password) throws Exception {

        HashMap<String,String> bodyContent = new HashMap<>();
        bodyContent.put("username",username);
        bodyContent.put("password",Password);

        Object body = objectMapper.writeValueAsString(bodyContent);

        MvcResult result = mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(body)))
                .andExpect(jsonPath("$.accessToken").exists()).andReturn();

        return JsonPath.read(result.getResponse().getContentAsString(),"$.accessToken");

    }
}
