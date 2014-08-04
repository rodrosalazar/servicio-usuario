DELETE FROM niveles_arbol;
DELETE FROM arboles;

--ARBOLES
INSERT INTO arboles VALUES (1,'normativas');
INSERT INTO arboles VALUES (2,'actos_administrativos');

--NIVELES ARBOL NORMATIVAS
INSERT INTO niveles_arbol VALUES (1, 'Generales', 1, null);
INSERT INTO niveles_arbol VALUES (2, 'Leyes', 1, 1);
INSERT INTO niveles_arbol VALUES (3, 'Reglamento', 1, 1);

INSERT INTO niveles_arbol VALUES (4, 'Creación, Suspensión e Intervención de Instituciones de Educación Superior', 1, NULL);
INSERT INTO niveles_arbol VALUES (5, 'Institutos Técnicos y Tecnológicos', 1, 4);
INSERT INTO niveles_arbol VALUES (6, 'Institución', 1, 5);
INSERT INTO niveles_arbol VALUES (7, 'Carrera', 1, 5);
INSERT INTO niveles_arbol VALUES (8, 'Universidades', 1, 4);
INSERT INTO niveles_arbol VALUES (9, 'Institución', 1, 8);
INSERT INTO niveles_arbol VALUES (10, 'Extensión', 1, 8);
INSERT INTO niveles_arbol VALUES (11, 'Carrera', 1, 8);
INSERT INTO niveles_arbol VALUES (12, 'Programa', 1, 8);

INSERT INTO niveles_arbol VALUES (13, 'Evaluación/Acreditación', 1, NULL);
INSERT INTO niveles_arbol VALUES (14, 'Institutos Técnicos y Tecnológicos', 1, 13);
INSERT INTO niveles_arbol VALUES (15, 'Institución', 1, 14);
INSERT INTO niveles_arbol VALUES (16, 'Carrera', 1, 14);
INSERT INTO niveles_arbol VALUES (17, 'Universidades', 1, 13);
INSERT INTO niveles_arbol VALUES (18, 'Institución', 1, 17);
INSERT INTO niveles_arbol VALUES (19, 'Extensión', 1, 17);
INSERT INTO niveles_arbol VALUES (20, 'Carrera', 1, 17);
INSERT INTO niveles_arbol VALUES (21, 'Programa', 1, 17);

INSERT INTO niveles_arbol VALUES (22, 'Funcionamiento', 1, NULL);
INSERT INTO niveles_arbol VALUES (24, 'Docentes', 1, 22);
INSERT INTO niveles_arbol VALUES (25, 'Titulación', 1, 22);
INSERT INTO niveles_arbol VALUES (26, 'Sanciones', 1, 22);
INSERT INTO niveles_arbol VALUES (27, 'Gratuidad', 1, 22);
INSERT INTO niveles_arbol VALUES (28, 'Aranceles', 1, 22);
INSERT INTO niveles_arbol VALUES (29, 'Régimen Académico', 1, 22);
INSERT INTO niveles_arbol VALUES (30, 'Becas', 1, 22);
INSERT INTO niveles_arbol VALUES (31, 'Investigación', 1, 22);

INSERT INTO niveles_arbol VALUES (32, 'Convenios', 1, null);



--ARBOL ACTOS ADMINITRATIVOS
INSERT INTO niveles_arbol VALUES (33, 'Creación, Suspensión e Intervención de Instituciones de Educación Superior', 2, null);
INSERT INTO niveles_arbol VALUES (35, 'Institutos Técnicos y Tecnológicos', 2, 33);
INSERT INTO niveles_arbol VALUES (36, 'Institución', 2, 35);
INSERT INTO niveles_arbol VALUES (37, 'Carrera', 2, 35);

INSERT INTO niveles_arbol VALUES (38, 'Universidades', 2, 33);
INSERT INTO niveles_arbol VALUES (39, 'Institución', 2, 38);
INSERT INTO niveles_arbol VALUES (40, 'Extensión', 2, 38);
INSERT INTO niveles_arbol VALUES (41, 'Carrera', 2, 38);
INSERT INTO niveles_arbol VALUES (42, 'Programa', 2, 38);

INSERT INTO niveles_arbol VALUES (52, 'Evaluación/Acreditación', 2, NULL);
INSERT INTO niveles_arbol VALUES (54, 'Institutos Técnicos y Tecnológicos', 2, 52);
INSERT INTO niveles_arbol VALUES (55, 'Institución', 2, 54);
INSERT INTO niveles_arbol VALUES (56, 'Carrera', 2, 54);

INSERT INTO niveles_arbol VALUES (57, 'Universidades', 2, 52);
INSERT INTO niveles_arbol VALUES (58, 'Institución', 2, 57);
INSERT INTO niveles_arbol VALUES (59, 'Extensión', 2, 57);
INSERT INTO niveles_arbol VALUES (60, 'Carrera', 2, 57);
INSERT INTO niveles_arbol VALUES (61, 'Programa', 2, 57);

INSERT INTO niveles_arbol VALUES (62, 'Funcionamiento', 2, 52);
INSERT INTO niveles_arbol VALUES (63, 'Docentes', 2, 62);
INSERT INTO niveles_arbol VALUES (64, 'Titulación', 2, 62);
INSERT INTO niveles_arbol VALUES (65, 'Sanciones', 2, 62);
INSERT INTO niveles_arbol VALUES (66, 'Gratuidad', 2, 62);
INSERT INTO niveles_arbol VALUES (67, 'Aranceles', 2, 62);
INSERT INTO niveles_arbol VALUES (68, 'Régimen Académico', 2, 62);
INSERT INTO niveles_arbol VALUES (69, 'Becas', 2, 62);
INSERT INTO niveles_arbol VALUES (70, 'Investigación', 2, 62);
