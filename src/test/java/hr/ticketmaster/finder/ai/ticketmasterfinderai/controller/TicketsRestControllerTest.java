package hr.ticketmaster.finder.ai.ticketmasterfinderai.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class TicketsRestControllerTest extends BaseControllerTest {

    private static Boolean database_init = false;

    private static int expected_result_size = 0;

    @BeforeEach
    void init() throws Exception {
        if (database_init) {
            return;
        }
        database_init = true;
        final String TEST_ID = "1";
        final String TEST_EVENT_NAME = "Save Init Event";
        final String TEST_VENUE =  "venue";
        final BigDecimal TEST_PRICE = BigDecimal.valueOf(1599.00);
        final String TEST_EVENT_DATE_TIME = "2024-06-18T09:30:30";
        final String TEST_DESCRIPTION = "description";


        Map<String,Object> body = new HashMap<>();
        body.put("id", TEST_ID);
        body.put("eventName", TEST_EVENT_NAME);
        body.put("eventDateTime", TEST_EVENT_DATE_TIME);
        body.put("description", TEST_DESCRIPTION);
        body.put("price", TEST_PRICE);
        body.put("venue", TEST_VENUE);

        mockMvc.perform(
                        post("/rest/ticket")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isCreated());

        expected_result_size++;
    }

    @Test
    void getAll_hasAdmin() throws Exception {
        mockMvc.perform(
                        get("/rest/tickets")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.ISO_8859_1))
                .andExpect(jsonPath("$", hasSize(expected_result_size)));
    }

    @Test
    void getAll_hasRegularUser() throws Exception {
        mockMvc.perform(
                        get("/rest/tickets")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.ISO_8859_1))
                .andExpect(jsonPath("$", hasSize(expected_result_size)));
    }
    @Test
    void getAll_hasNonAuthenticatedUser() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rest/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    void save_asAdminUser() throws Exception {
        final String TEST_ID = "2";
        final String TEST_EVENT_NAME = "Save Admin Event";
        final String TEST_VENUE =  "venue";
        final BigDecimal TEST_PRICE = BigDecimal.valueOf(1599.00);
        final String TEST_EVENT_DATE_TIME = "2024-06-18T09:30:30";
        final String TEST_DESCRIPTION = "description";


        Map<String,Object> body = new HashMap<>();
        body.put("id", TEST_ID);
        body.put("eventName", TEST_EVENT_NAME);
        body.put("eventDateTime", TEST_EVENT_DATE_TIME);
        body.put("description", TEST_DESCRIPTION);
        body.put("price", TEST_PRICE);
        body.put("venue", TEST_VENUE);

        mockMvc.perform(
                        post("/rest/ticket")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isCreated());

        expected_result_size++;
    }

    @Test
    void save_asRegularUser() throws Exception {
        final String TEST_ID = "3";
        final String TEST_EVENT_NAME = "Save User Event";
        final String TEST_VENUE =  "venue";
        final BigDecimal TEST_PRICE = BigDecimal.valueOf(1599.00);
        final String TEST_EVENT_DATE_TIME = "2024-06-18T09:30:30";
        final String TEST_DESCRIPTION = "description";


        Map<String,Object> body = new HashMap<>();
        body.put("id", TEST_ID);
        body.put("eventName", TEST_EVENT_NAME);
        body.put("eventDateTime", TEST_EVENT_DATE_TIME);
        body.put("description", TEST_DESCRIPTION);
        body.put("price", TEST_PRICE);
        body.put("venue", TEST_VENUE);

        mockMvc.perform(
                        post("/rest/ticket")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void getById_hasAdmin() throws Exception {
        final String TEST_ID = "1";
        final String TEST_EVENT_NAME = "Save Init Event";
        final String TEST_VENUE =  "venue";
        final BigDecimal TEST_PRICE = BigDecimal.valueOf(1599.00);
        final String TEST_EVENT_DATE_TIME = "2024-06-18T09:30:30";
        final String TEST_DESCRIPTION = "description";
        mockMvc.perform(
                        get("/rest/ticket/1")
                                .header(HttpHeaders.AUTHORIZATION, getAdminAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.ISO_8859_1))
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.eventName").value(TEST_EVENT_NAME))
                .andExpect(jsonPath("$.eventDateTime").value(TEST_EVENT_DATE_TIME))
                .andExpect(jsonPath("$.description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(TEST_PRICE))
                .andExpect(jsonPath("$.venue").value(TEST_VENUE));
    }

    @Test
    void getById_hasRegularUser() throws Exception {
        final String TEST_ID = "1";
        final String TEST_EVENT_NAME = "Save Init Event";
        final String TEST_VENUE =  "venue";
        final BigDecimal TEST_PRICE = BigDecimal.valueOf(1599.00);
        final String TEST_EVENT_DATE_TIME = "2024-06-18T09:30:30";
        final String TEST_DESCRIPTION = "description";
        mockMvc.perform(
                        get("/rest/ticket/1")
                                .header(HttpHeaders.AUTHORIZATION, getUserAuthorizationHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().encoding(StandardCharsets.ISO_8859_1))
                .andExpect(jsonPath("$.id").value(TEST_ID))
                .andExpect(jsonPath("$.eventName").value(TEST_EVENT_NAME))
                .andExpect(jsonPath("$.eventDateTime").value(TEST_EVENT_DATE_TIME))
                .andExpect(jsonPath("$.description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(TEST_PRICE))
                .andExpect(jsonPath("$.venue").value(TEST_VENUE));
    }
    @Test
    void getById_hasNonAuthenticatedUser() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/rest/ticket/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}