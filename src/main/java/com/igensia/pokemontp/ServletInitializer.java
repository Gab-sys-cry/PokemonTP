package com.igensia.pokemontp;

import org.jspecify.annotations.NullMarked;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@NullMarked
@SuppressWarnings("unused")
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PokemonTpApplication.class);
    }

}
