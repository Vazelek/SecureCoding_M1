package hr.ticketmaster.finder.ai.ticketmasterfinderai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public abstract class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String getAdminAuthorizationHeader() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("username", "admin");
        body.put("password", "admin");

        MvcResult mvcResult = mockMvc.perform(
                post("/auth/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
        ).andReturn();

        String jwt = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.accessToken");

        return "Bearer " + jwt;
    }

    protected String getUserAuthorizationHeader() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("username", "user");
        body.put("password", "user");

        MvcResult mvcResult = mockMvc.perform(
                post("/auth/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
        ).andReturn();

        String jwt = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.accessToken");

        return "Bearer " + jwt;
    }

}