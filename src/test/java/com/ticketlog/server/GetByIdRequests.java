package com.ticketlog.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

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
public class GetByIdRequests {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void clearTable() {
        jdbcTemplate.update("DELETE FROM estados");
        jdbcTemplate.update("DELETE FROM cidades");
    }

    @Test
    void itShouldSaveEstado() throws Exception {
        JSONObject my_obj = new JSONObject();

        my_obj.put("id", "SC");
        my_obj.put("nome", "Santa Catarina");

        mockMvc.perform(post("/api/v1/estado/save").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
                .andExpect(status().isOk()).andReturn();

        jdbcTemplate.update(
                "INSERT INTO cidades (id,nome,populacao,custo_cidade_us,id_estado)VALUES ('cba3ff2e-3087-49bd-bc9b-285e809e7b32','Joinville',590400,1,0),('846e1a32-f831-4bee-a6bc-673b5f901d7b','Florianopolis',508826,2,0);");

        MvcResult result = mockMvc
                .perform(
                        get("/api/v1/estado/get/sc").contentType(MediaType.APPLICATION_JSON).content(my_obj.toString()))
                .andExpect(status().isOk()).andReturn();

        Estado response = objectMapper.readValue(result.getResponse().getContentAsString(), Estado.class);
        assertThat(response.getNome()).isEqualTo("Santa Catarina");
        assertThat(response.getPopulacao()).isEqualTo(590400 + 508826);
        assertThat(response.getCustoEstadoUs()).isEqualTo(3.0);

    }
}
