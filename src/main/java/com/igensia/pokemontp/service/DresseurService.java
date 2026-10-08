package com.igensia.pokemontp.service;

import com.igensia.pokemontp.dto.DresseurDto;
import com.igensia.pokemontp.entities.Dresseur;
import com.igensia.pokemontp.entities.Pokemon;
import com.igensia.pokemontp.exception.DresseurNotFoundException;
import com.igensia.pokemontp.repository.DresseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DresseurService {

    static final int NIVEAU_SANS_POKEMON = 1;
    static final int SEUIL_DIVERSITE = 3;
    static final int BONUS_DIVERSITE = 20;
    static final int SEUIL_ELITE = 50;
    static final int BONUS_ELITE = 10;

    private final DresseurRepository dresseurRepository;

    public DresseurService(DresseurRepository dresseurRepository) {
        this.dresseurRepository = dresseurRepository;
    }

    @Transactional(readOnly = true)
    public int calculerNiveauDresseur(Long dresseurId) {
        Dresseur dresseur = trouverDresseur(dresseurId);
        return calculerNiveau(dresseur);
    }

    @Transactional(readOnly = true)
    public DresseurDto getDresseurDto(Long dresseurId) {
        Dresseur dresseur = trouverDresseur(dresseurId);
        return new DresseurDto(
                dresseur.getNom(),
                dresseur.getPokemons().size(),
                calculerNiveau(dresseur));
    }

    private Dresseur trouverDresseur(Long dresseurId) {
        return dresseurRepository.findById(dresseurId)
                .orElseThrow(() -> new DresseurNotFoundException(dresseurId));
    }

    private int calculerNiveau(Dresseur dresseur) {
        List<Pokemon> pokemons = dresseur.getPokemons();

        if (pokemons == null || pokemons.isEmpty()) {
            return NIVEAU_SANS_POKEMON;
        }

        int base = pokemons.stream().mapToInt(Pokemon::getNiveau).sum();

        long typesDistincts = pokemons.stream()
                .map(Pokemon::getType)
                .filter(Objects::nonNull)
                .distinct()
                .count();
        int bonusDiversite = typesDistincts >= SEUIL_DIVERSITE ? BONUS_DIVERSITE : 0;

        long pokemonsElite = pokemons.stream()
                .filter(p -> p.getNiveau() >= SEUIL_ELITE)
                .count();
        int bonusElite = (int) pokemonsElite * BONUS_ELITE;

        return base + bonusDiversite + bonusElite;
    }
}
