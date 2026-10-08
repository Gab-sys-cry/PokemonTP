package com.igensia.pokemontp.controller;

import com.igensia.pokemontp.dto.DresseurDto;
import com.igensia.pokemontp.exception.DresseurNotFoundException;
import com.igensia.pokemontp.service.DresseurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DresseurController.class)
class DresseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DresseurService dresseurService;

    @Test
    void should_return_200_and_dresseur_json_when_dresseur_exists() throws Exception {
        // Arrange
        when(dresseurService.getDresseurDto(1L))
                .thenReturn(new DresseurDto("Sacha", 3, 195));

        // Act + Assert
        mockMvc.perform(get("/dresseurs/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nomDresseur").value("Sacha"))
                .andExpect(jsonPath("$.nombrePokemons").value(3))
                .andExpect(jsonPath("$.niveauDresseur").value(195));
    }

    @Test
    void should_return_404_when_dresseur_does_not_exist() throws Exception {
        // Arrange
        when(dresseurService.getDresseurDto(99L))
                .thenThrow(new DresseurNotFoundException(99L));

        // Act + Assert
        mockMvc.perform(get("/dresseurs/{id}", 99L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Dresseur introuvable avec l'id : 99"));
    }
}