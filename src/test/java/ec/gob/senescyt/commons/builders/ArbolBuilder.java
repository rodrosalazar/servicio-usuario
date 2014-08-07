package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;

import java.util.ArrayList;
import java.util.List;

public class ArbolBuilder {

    private static final NivelArbol NIVEL_PADRE_DE_GENERALES = null;
    private static ArbolBuilder arbolBuilder;

    private Integer idArbol = 1000;
    private String nombreArbol = "normativas";

    private Integer idNivelGenerales = 1001;
    private String nombreNivelGenerales = "Generales";

    private Integer idSubnivelLeyes = 1002;
    private String nombreSubnivelLeyes = "Leyes";

    private Integer idSubnivelReglamentos = 1003;
    private String nombreSubnivelReglamentos = "Reglamentos";

    private Integer idSubnivelTest = 1004;
    private String nombreSubnivelTest = "subNivelDeReglamentos";

    public Arbol generar() {
        Arbol arbol = new Arbol(idArbol, nombreArbol);

        NivelArbol nivelGenerales = new NivelArbol(idNivelGenerales, nombreNivelGenerales, NIVEL_PADRE_DE_GENERALES, arbol);
        NivelArbol nivelLeyes = new NivelArbol(idSubnivelLeyes, nombreSubnivelLeyes, nivelGenerales, arbol);
        NivelArbol nivelReglamentos = new NivelArbol(idSubnivelReglamentos, nombreSubnivelReglamentos, nivelGenerales, arbol);

        nivelGenerales.getNivelesHijos().add(nivelLeyes);
        nivelGenerales.getNivelesHijos().add(nivelReglamentos);

        NivelArbol subnivelTest = new NivelArbol(idSubnivelTest, nombreSubnivelTest, nivelReglamentos, arbol);
        nivelReglamentos.getNivelesHijos().add(subnivelTest);

        List<NivelArbol> nivelesArbolNormativas = new ArrayList<>();
        nivelesArbolNormativas.add(nivelGenerales);

        arbol.getNivelesArbol().addAll(nivelesArbolNormativas);

        return arbol;
    }

    public static ArbolBuilder nuevoArbol() {
        arbolBuilder = new ArbolBuilder();
        return arbolBuilder;
    }
}
