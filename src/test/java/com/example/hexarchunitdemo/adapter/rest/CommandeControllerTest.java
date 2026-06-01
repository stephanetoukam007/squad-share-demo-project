package com.example.hexarchunitdemo.adapter.rest;

import com.example.hexarchunitdemo.util.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommandeControllerTest {

    private static final String BASE_URL = "/api/v1/commandes";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void enregistrerCommande_QUAND_requeteValide_ALORS_retourne201EtPayloadStandard() throws Exception {
        MvcResult resultat = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonTestUtils.loadJsonFromFile("json/commande/request-valide.json")))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", org.hamcrest.Matchers.startsWith(BASE_URL + "/")))
                .andReturn();

        assertThat(JsonTestUtils.jsonResourceEqualsIgnoringPaths(
                "json/commande/response-created.json",
                resultat.getResponse().getContentAsString(),
                "/horodatage",
                "/donnees/id",
                "/donnees/dateCreation"
        )).isTrue();
    }

    @Test
    void enregistrerCommande_QUAND_requeteInvalide_ALORS_retourne400Standardise() throws Exception {
        MvcResult resultat = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonTestUtils.loadJsonFromFile("json/commande/request-invalide.json")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(JsonTestUtils.jsonResourceEqualsIgnoringPaths(
                "json/commande/response-bad-request.json",
                resultat.getResponse().getContentAsString(),
                "/horodatage"
        )).isTrue();
    }

    @Test
    void trouverCommande_QUAND_commandeIntrouvable_ALORS_retourne404Standardise() throws Exception {
        MvcResult resultat = mockMvc.perform(get(BASE_URL + "/{commandeId}", "00000000-0000-0000-0000-000000000111"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(JsonTestUtils.jsonResourceEqualsIgnoringPaths(
                "json/commande/response-not-found.json",
                resultat.getResponse().getContentAsString(),
                "/horodatage"
        )).isTrue();
    }
}

