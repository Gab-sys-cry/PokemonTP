package com.igensia.pokemontp.service;

import com.igensia.pokemontp.dto.DresseurDto;
import com.igensia.pokemontp.entities.Dresseur;
import com.igensia.pokemontp.entities.Pokemon;
import com.igensia.pokemontp.exception.DresseurNotFoundException;
import com.igensia.pokemontp.repository.DresseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DresseurServiceTest {

    @Mock
    private DresseurRepository dresseurRepository;

    @InjectMocks
    private DresseurService dresseurService;

    @Test
    void should_return_sum_of_levels_when_dresseur_has_2_pokemons_and_no_bonus() {
        // Arrange
        Dresseur dresseur = new Dresseur("Ondine", "Kanto");
        dresseur.addPokemon(new Pokemon("Carapuce", "Eau", 30));
        dresseur.addPokemon(new Pokemon("Salamèche", "Feu", 10));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(40, niveau);
    }

    @Test
    void should_return_1_when_dresseur_has_no_pokemon() {
        // Arrange
        Dresseur dresseur = new Dresseur("Pierre", "Kanto");
        when(dresseurRepository.findById(3L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(3L);

        // Assert
        assertEquals(1, niveau);
    }

    @Test
    void should_apply_diversity_bonus_when_dresseur_has_3_different_types() {
        // Arrange
        Dresseur dresseur = new Dresseur("Flora", "Johto");
        dresseur.addPokemon(new Pokemon("Salamèche", "Feu", 10));
        dresseur.addPokemon(new Pokemon("Carapuce", "Eau", 20));
        dresseur.addPokemon(new Pokemon("Bulbizarre", "Plante", 30));
        when(dresseurRepository.findById(2L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(2L);

        // Assert
        assertEquals(80, niveau); // 60 (base) + 20 (diversité)
    }

    @Test
    void should_not_apply_diversity_bonus_when_dresseur_has_only_2_distinct_types() {
        // Arrange
        Dresseur dresseur = new Dresseur("Jacques", "Sinnoh");
        dresseur.addPokemon(new Pokemon("Salamèche", "Feu", 10));
        dresseur.addPokemon(new Pokemon("Goupix", "Feu", 20));
        dresseur.addPokemon(new Pokemon("Carapuce", "Eau", 30));
        when(dresseurRepository.findById(4L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(4L);

        // Assert
        assertEquals(60, niveau); // base uniquement
    }

    @Test
    void should_apply_elite_bonus_for_each_pokemon_when_level_is_50_or_more() {
        // Arrange
        Dresseur dresseur = new Dresseur("Aurore", "Hoenn");
        dresseur.addPokemon(new Pokemon("Arcanin", "Feu", 50));    // limite : bonus
        dresseur.addPokemon(new Pokemon("Ronflex", "Normal", 70)); // bonus
        dresseur.addPokemon(new Pokemon("Roucool", "Normal", 49)); // pas de bonus
        when(dresseurRepository.findById(5L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(5L);

        // Assert
        assertEquals(189, niveau); // 169 (base) + 2 x 10 (élite), 2 types donc pas de diversité
    }

    @Test
    void should_cumulate_diversity_and_elite_bonuses_when_dresseur_has_3_types_and_elite_pokemons() {
        // Arrange
        Dresseur dresseur = new Dresseur("Sacha", "Kanto");
        dresseur.addPokemon(new Pokemon("Pikachu", "Électrik", 55));
        dresseur.addPokemon(new Pokemon("Dracaufeu", "Feu", 60));
        dresseur.addPokemon(new Pokemon("Tortank", "Eau", 40));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = dresseurService.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(195, niveau); // 155 (base) + 20 (diversité) + 2 x 10 (élite)
    }

    @Test
    void should_throw_dresseur_not_found_exception_when_dresseur_does_not_exist() {
        // Arrange
        when(dresseurRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        DresseurNotFoundException exception = assertThrows(
                DresseurNotFoundException.class,
                () -> dresseurService.calculerNiveauDresseur(99L));

        // Assert
        assertEquals("Dresseur introuvable avec l'id : 99", exception.getMessage());
        verify(dresseurRepository).findById(99L);
    }

    @Test
    void should_return_dto_with_name_count_and_level_when_dresseur_exists() {
        // Arrange
        Dresseur dresseur = new Dresseur("Sacha", "Kanto");
        dresseur.addPokemon(new Pokemon("Pikachu", "Électrik", 55));
        dresseur.addPokemon(new Pokemon("Dracaufeu", "Feu", 60));
        dresseur.addPokemon(new Pokemon("Tortank", "Eau", 40));
        when(dresseurRepository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        DresseurDto dto = dresseurService.getDresseurDto(1L);

        // Assert
        assertEquals("Sacha", dto.nomDresseur());
        assertEquals(3, dto.nombrePokemons());
        assertEquals(195, dto.niveauDresseur());
    }
}
