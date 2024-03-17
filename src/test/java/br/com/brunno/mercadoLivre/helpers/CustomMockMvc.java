package br.com.brunno.mercadoLivre.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

@TestComponent
public class CustomMockMvc {

    private final MockMvc mockMvc;

    @Autowired
    public CustomMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions post(String uri, Map<String, Object> payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
