package br.com.brunno.mercadoLivre.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@TestComponent
public class CustomMockMvc {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    public ResultActions post(String uri, Map<String, Object> payload) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultActions postAuthenticated(String uri, Map<String, Object> payload) {
        this.post("/user", Map.of("login", "test@email.com", "password", "secret"));
        String loginResponse;
        try {
            MvcResult mvcResult = this.post("/login", Map.of("username", "test@email.com", "password", "secret"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            loginResponse = mvcResult.getResponse().getContentAsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer "+loginResponse)
                    .content(objectMapper.writeValueAsString(payload)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
