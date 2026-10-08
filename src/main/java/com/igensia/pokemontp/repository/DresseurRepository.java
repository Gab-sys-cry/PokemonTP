package com.igensia.pokemontp.repository;

import com.igensia.pokemontp.entities.Dresseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DresseurRepository extends JpaRepository<Dresseur, Long> {

    Optional<Dresseur> findByNom(String nom);
}