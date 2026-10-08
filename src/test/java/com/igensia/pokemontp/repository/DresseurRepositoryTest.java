package com.igensia.pokemontp.repository;

import com.igensia.pokemontp.entities.Dresseur;
import com.igensia.pokemontp.entities.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DresseurRepositoryTest {

    @Autowired
    private DresseurRepository dresseurRepository;

    @Test
    void should_load_3_dresseurs_when_data_sql_is_executed() {
        // Arrange
        // (jeu de données chargé automatiquement depuis src/test/resources/data.sql)

        // Act
        long total = dresseurRepository.count();

        // Assert
        assertEquals(3, total);
    }

    @Test
    void should_return_dresseur_with_3_pokemons_when_nom_is_sacha() {
        // Arrange
        String nom = "Sacha";

        // Act
        Optional<Dresseur> resultat = dresseurRepository.findByNom(nom);

        // Assert
        assertTrue(resultat.isPresent());
        assertEquals("Sacha", resultat.get().getNom());
        assertEquals("Kanto", resultat.get().getRegion());
        assertEquals(3, resultat.get().getPokemons().size());
    }

    @Test
    void should_return_dresseur_without_pokemon_when_nom_is_pierre() {
        // Arrange
        String nom = "Pierre";

        // Act
        Optional<Dresseur> resultat = dresseurRepository.findByNom(nom);

        // Assert
        assertTrue(resultat.isPresent());
        assertEquals(0, resultat.get().getPokemons().size());
    }

    @Test
    void should_return_empty_when_nom_does_not_exist() {
        // Arrange
        String nom = "Inconnu";

        // Act
        Optional<Dresseur> resultat = dresseurRepository.findByNom(nom);

        // Assert
        assertEquals(Optional.empty(), resultat);
    }

    @Test
    void should_persist_dresseur_and_its_pokemons_when_saving_new_dresseur() {
        // Arrange
        Dresseur regis = new Dresseur("Régis", "Hoenn");
        regis.addPokemon(new Pokemon("Mackogneur", "Combat", 45));

        // Act
        Dresseur sauvegarde = dresseurRepository.saveAndFlush(regis);

        // Assert
        assertNotNull(sauvegarde.getId());
        assertEquals(4, dresseurRepository.count());
        assertEquals(1, sauvegarde.getPokemons().size());
        assertNotNull(sauvegarde.getPokemons().get(0).getId());
    }
}
