-- Dresseurs
INSERT INTO dresseur (nom, region)
VALUES ('Sacha', 'Kanto');
INSERT INTO dresseur (nom, region)
VALUES ('Ondine', 'Kanto');
INSERT INTO dresseur (nom, region)
VALUES ('Pierre', 'Kanto');

-- Sacha : 3 types distincts, 2 Pokémon de niveau >= 50
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES ('Pikachu', 'Électrik', 55, (SELECT id FROM dresseur WHERE nom = 'Sacha'));
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES ('Dracaufeu', 'Feu', 60, (SELECT id FROM dresseur WHERE nom = 'Sacha'));
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES ('Tortank', 'Eau', 40, (SELECT id FROM dresseur WHERE nom = 'Sacha'));

-- Ondine : 1 seul type, aucun Pokémon élite
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES ('Stari', 'Eau', 30, (SELECT id FROM dresseur WHERE nom = 'Ondine'));
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES ('Psykokwak', 'Eau', 25, (SELECT id FROM dresseur WHERE nom = 'Ondine'));

-- Pierre : aucun Pokémon