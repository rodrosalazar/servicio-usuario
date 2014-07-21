CREATE TABLE niveles_arbol (
  id INTEGER NOT NULL PRIMARY KEY,
  nombre VARCHAR(100),
  id_arbol INTEGER NOT NULL REFERENCES arboles(id),
  id_nivel_padre INTEGER REFERENCES niveles_arbol(id)
);