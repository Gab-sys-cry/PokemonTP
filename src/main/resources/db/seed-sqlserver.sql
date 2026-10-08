-- Données de tests executé dans SSMS, BDD pokemondb

INSERT INTO dresseur (nom, region)
VALUES (N'Sacha', N'Kanto');
INSERT INTO dresseur (nom, region)
VALUES (N'Ondine', N'Kanto');
INSERT INTO dresseur (nom, region)
VALUES (N'Pierre', N'Kanto');

INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES (N'Pikachu', N'Électrik', 55, (SELECT id FROM dresseur WHERE nom = N'Sacha'));
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES (N'Dracaufeu', N'Feu', 60, (SELECT id FROM dresseur WHERE nom = N'Sacha'));
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES (N'Tortank', N'Eau', 40, (SELECT id FROM dresseur WHERE nom = N'Sacha'));

INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES (N'Stari', N'Eau', 30, (SELECT id FROM dresseur WHERE nom = N'Ondine'));
INSERT INTO pokemon (nom, type, niveau, dresseur_id)
VALUES (N'Psykokwak', N'Eau', 25, (SELECT id FROM dresseur WHERE nom = N'Ondine'));