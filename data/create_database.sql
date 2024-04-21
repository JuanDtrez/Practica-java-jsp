CREATE TABLE "peliculas" (
	"id"	INTEGER,
	"titulo"	TEXT,
	"id_director"	INTEGER,
	"anyo"	INTEGER,
	"url_caratula"	TEXT,
	"id_genero"	INTEGER,
	"es_animacion"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("id_genero") REFERENCES "generos"("id"),
	FOREIGN KEY("id_director") REFERENCES "directores"("id")
);

CREATE TABLE "artistas" (
	"id"	INTEGER,
	"nombre"	TEXT,
	"url_foto"	TEXT,
	"url_web"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "directores" (
	"id"	INTEGER,
	"nombre"	TEXT,
	"url_foto"	TEXT,
	"url_web"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "generos" (
	"id"	INTEGER,
	"descripcion"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "repartos" (
	"id_pelicula"	INTEGER,
	"id_artista"	INTEGER,
	FOREIGN KEY("id_pelicula") REFERENCES "peliculas"("id"),
	PRIMARY KEY("id_pelicula","id_artista"),
	FOREIGN KEY("id_artista") REFERENCES "artistas"("id")
);


INSERT INTO "generos" ("descripcion") VALUES 
('Accion'),
('Aventura'),
('Comedia'),
('Drama'),
('Fantasia'),
('Terror'),
('Ciencia_ficcion'),
('Musical'),
('Suspense'),
('Western'),
('Documental'),
('Biografico'),
('Romance');

INSERT INTO directores (nombre, url_foto, url_web) VALUES
    ('Steven Spielberg', 'http://url_spielberg.com', 'http://web_spielberg.com'),
    ('James Cameron', 'http://url_cameron.com', 'http://web_cameron.com'),
    ('Peter Jackson', 'http://url_jackson.com', 'http://web_jackson.com'),
    ('John Lasseter', 'http://url_lasseter.com', 'http://web_lasseter.com');

COMMIT;
