package com.example.hexarchunitdemo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Utilitaires JSON inspirés du projet de référence.
 */
public final class JsonTestUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private JsonTestUtils() {
    }

    public static String loadJsonFromFile(String cheminDansLesRessources) throws IOException {
        try (InputStream flux = JsonTestUtils.class.getClassLoader().getResourceAsStream(cheminDansLesRessources)) {
            if (flux == null) {
                throw new IOException("Ressource introuvable : " + cheminDansLesRessources);
            }
            return new String(flux.readAllBytes());
        }
    }

    public static JsonNode readJson(String json) throws IOException {
        return OBJECT_MAPPER.readTree(json);
    }

    public static boolean jsonResourceEqualsIgnoringPaths(String ressourceAttendue, String jsonActuel,
                                                          String... cheminsAIgnorer) throws IOException {
        JsonNode attendu = OBJECT_MAPPER.readTree(loadJsonFromFile(ressourceAttendue));
        JsonNode actuel = OBJECT_MAPPER.readTree(jsonActuel);

        for (String chemin : cheminsAIgnorer) {
            supprimerChemin(attendu, chemin);
            supprimerChemin(actuel, chemin);
        }

        return Objects.equals(attendu, actuel);
    }

    private static void supprimerChemin(JsonNode racine, String chemin) {
        if (chemin == null || chemin.isBlank() || !chemin.startsWith("/")) {
            return;
        }

        String[] segments = chemin.substring(1).split("/");
        JsonNode courant = racine;
        for (int i = 0; i < segments.length - 1; i++) {
            courant = courant.path(segments[i]);
            if (courant.isMissingNode()) {
                return;
            }
        }

        if (courant.isObject()) {
            ((com.fasterxml.jackson.databind.node.ObjectNode) courant).remove(segments[segments.length - 1]);
        }
    }
}

