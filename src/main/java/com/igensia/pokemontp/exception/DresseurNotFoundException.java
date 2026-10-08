package com.igensia.pokemontp.exception;

public class DresseurNotFoundException extends RuntimeException {

    public DresseurNotFoundException(Long id) {
        super("Dresseur introuvable avec l'id : " + id);
    }
}
