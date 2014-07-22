--ARBOLES
INSERT INTO arboles VALUES (1,'normativas');
INSERT INTO arboles VALUES (2,'actos_administrativos');

--NIVELES ARBOL NORMATIVAS
INSERT INTO niveles_arbol VALUES (1, 'Generales', 1, null);
INSERT INTO niveles_arbol VALUES (2, 'Leyes', 1, 1);
INSERT INTO niveles_arbol VALUES (3, 'Reglamentos', 1, 1);
INSERT INTO niveles_arbol VALUES (4, 'Creación', 1, NULL);
INSERT INTO niveles_arbol VALUES (5, 'Creación de Institutos Técnicos y Tecnológicos', 1, 4);
INSERT INTO niveles_arbol VALUES (6, 'Institutos', 1, 5);
INSERT INTO niveles_arbol VALUES (7, 'Carreras', 1, 5);
INSERT INTO niveles_arbol VALUES (8, 'Creación de Universidades', 1, 4);
INSERT INTO niveles_arbol VALUES (9, 'Instituto', 1, 8);
INSERT INTO niveles_arbol VALUES (10, 'Extensión', 1, 8);
INSERT INTO niveles_arbol VALUES (11, 'Carreras', 1, 8);
INSERT INTO niveles_arbol VALUES (12, 'Programas', 1, 8);
INSERT INTO niveles_arbol VALUES (13, 'Evaluación/Acreditación', 1, NULL);
INSERT INTO niveles_arbol VALUES (14, 'Evaluación/Acreditación de Institutos Técnicos y Tecnológicos', 1, 13);
INSERT INTO niveles_arbol VALUES (15, 'Institutos', 1, 14);
INSERT INTO niveles_arbol VALUES (16, 'Carreras', 1, 14);
INSERT INTO niveles_arbol VALUES (17, 'Evaluación/Acreditación de Universidades', 1, 13);
INSERT INTO niveles_arbol VALUES (18, 'Instituto', 1, 17);
INSERT INTO niveles_arbol VALUES (19, 'Extensión', 1, 17);
INSERT INTO niveles_arbol VALUES (20, 'Carreras', 1, 17);
INSERT INTO niveles_arbol VALUES (21, 'Programas', 1, 17);
INSERT INTO niveles_arbol VALUES (22, 'Funcionamiento', 1, NULL);
INSERT INTO niveles_arbol VALUES (23, 'Acuerdos', 1, 22);
INSERT INTO niveles_arbol VALUES (24, 'Docente', 1, 23);
INSERT INTO niveles_arbol VALUES (25, 'Titulación', 1, 23);
INSERT INTO niveles_arbol VALUES (26, 'Sanciones', 1, 23);
INSERT INTO niveles_arbol VALUES (27, 'Gratuidad', 1, 23);
INSERT INTO niveles_arbol VALUES (28, 'Aranceles', 1, 23);
INSERT INTO niveles_arbol VALUES (29, 'Régimen Académico', 1, 23);
INSERT INTO niveles_arbol VALUES (30, 'Becas', 1, 23);
INSERT INTO niveles_arbol VALUES (31, 'Investigación', 1, 23);
INSERT INTO niveles_arbol VALUES (32, 'Convenios', 1, 22);



--ARBOL ACTOS ADMINITRATIVOS
INSERT INTO niveles_arbol VALUES (33, 'Creación', 2, null);
INSERT INTO niveles_arbol VALUES (34, 'Resoluciones', 2, 33);
INSERT INTO niveles_arbol VALUES (35, 'Institutos Técnicos y Tecnológicos', 2, 34);
INSERT INTO niveles_arbol VALUES (36, 'Institutos', 2, 35);
INSERT INTO niveles_arbol VALUES (37, 'Carreras', 2, 35);
INSERT INTO niveles_arbol VALUES (38, 'Creación de Universidades', 2, 34);
INSERT INTO niveles_arbol VALUES (39, 'Instituto', 2, 38);
INSERT INTO niveles_arbol VALUES (40, 'Extensión', 2, 38);
INSERT INTO niveles_arbol VALUES (41, 'Carreras', 2, 38);
INSERT INTO niveles_arbol VALUES (42, 'Programas', 2, 38);
INSERT INTO niveles_arbol VALUES (43, 'Informes Técnicos', 2, 33);
INSERT INTO niveles_arbol VALUES (44, 'Institutos Técnicos y Tecnológicos', 2, 43);
INSERT INTO niveles_arbol VALUES (45, 'Institutos', 2, 44);
INSERT INTO niveles_arbol VALUES (46, 'Carreras', 2, 44);
INSERT INTO niveles_arbol VALUES (47, 'Creación de Universidades', 2, 43);
INSERT INTO niveles_arbol VALUES (48, 'Instituto', 2, 47);
INSERT INTO niveles_arbol VALUES (49, 'Extensión', 2, 47);
INSERT INTO niveles_arbol VALUES (50, 'Carreras', 2, 47);
INSERT INTO niveles_arbol VALUES (51, 'Programas', 2, 47);
INSERT INTO niveles_arbol VALUES (52, 'Evaluación/Acreditación', 2, NULL);
INSERT INTO niveles_arbol VALUES (53, 'Resoluciones', 2, 52);
INSERT INTO niveles_arbol VALUES (54, 'Institutos Técnicos y Tecnológicos', 2, 53);
INSERT INTO niveles_arbol VALUES (55, 'Institutos', 2, 54);
INSERT INTO niveles_arbol VALUES (56, 'Carreras', 2, 54);
INSERT INTO niveles_arbol VALUES (57, 'Creación de Universidades', 2, 53);
INSERT INTO niveles_arbol VALUES (58, 'Instituto', 2, 57);
INSERT INTO niveles_arbol VALUES (59, 'Extensión', 2, 57);
INSERT INTO niveles_arbol VALUES (60, 'Carreras', 2, 57);
INSERT INTO niveles_arbol VALUES (61, 'Programas', 2, 57);
INSERT INTO niveles_arbol VALUES (62, 'Informes Técnicos', 2, 52);
INSERT INTO niveles_arbol VALUES (63, 'Institutos Técnicos y Tecnológicos', 2, 62);
INSERT INTO niveles_arbol VALUES (64, 'Institutos', 2, 63);
INSERT INTO niveles_arbol VALUES (65, 'Carreras', 2, 63);
INSERT INTO niveles_arbol VALUES (66, 'Creación de Universidades', 2, 62);
INSERT INTO niveles_arbol VALUES (67, 'Instituto', 2, 66);
INSERT INTO niveles_arbol VALUES (68, 'Extensión', 2, 66);
INSERT INTO niveles_arbol VALUES (69, 'Carreras', 2, 66);
INSERT INTO niveles_arbol VALUES (70, 'Programas', 2, 66);
