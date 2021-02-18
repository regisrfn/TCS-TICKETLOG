package com.ticketlog.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ticketlog.server.model.Estado;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class PutRequestTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void clearTable() {
        jdbcTemplate.update("DELETE FROM estados");
    }

    @Test
    void itShouldUpdateEstado_populacao() throws Exception {
        JSONObject my_obj = new JSONObject();

        my_obj.put("id", "1297fbcc-b97c-4d7d-a5a9-f34bcf3be0db");
        my_obj.put("nome", "Santa Catarina");
        my_obj.put("populacao", 50000);

        mockMvc.perform(post("/api/v1/estado/save").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
                .andExpect(status().isOk()).andReturn();

        my_obj = new JSONObject();
        my_obj.put("populacao", "1");

        MvcResult result = mockMvc.perform(
                put("/api/v1/estado/update/1297fbcc-b97c-4d7d-a5a9-f34bcf3be0db/populacao").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
                .andExpect(status().isOk()).andReturn();

        Estado response = objectMapper.readValue(result.getResponse().getContentAsString(), Estado.class);
        assertThat(response.getNome()).isEqualTo("Santa Catarina");
        assertThat(response.getPopulacao()).isEqualTo(50001);
        assertThat(response.getId().toString()).isEqualTo("1297fbcc-b97c-4d7d-a5a9-f34bcf3be0db");
    }

    @Test
    void itShouldUpdateEstado_custo() throws Exception {
        JSONObject my_obj = new JSONObject();

        my_obj.put("id", "1297fbcc-b97c-4d7d-a5a9-f34bcf3be0db");
        my_obj.put("nome", "Santa Catarina");
        my_obj.put("custoEstadoUs", 10.99);

        mockMvc.perform(post("/api/v1/estado/save").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
                .andExpect(status().isOk()).andReturn();

        my_obj = new JSONObject();
        my_obj.put("custoEstadoUs", 0.01);

        MvcResult result = mockMvc.perform(
                put("/api/v1/estado/update/1297fbcc-b97c-4d7d-a5a9-f34bcf3be0db/custo").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
                .andExpect(status().isOk()).andReturn();

        Estado response = objectMapper.readValue(result.getResponse().getContentAsString(), Estado.class);
        assertThat(response.getNome()).isEqualTo("Santa Catarina");
        assertThat(response.getCustoEstadoUs()).isEqualTo(11.0);
        assertThat(response.getId().toString()).isEqualTo("1297fbcc-b97c-4d7d-a5a9-f34bcf3be0db");
    }
}
