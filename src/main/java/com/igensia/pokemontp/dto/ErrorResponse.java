package com.igensia.pokemontp.dto;

public record ErrorResponse(int status, String error, String message) {
}
