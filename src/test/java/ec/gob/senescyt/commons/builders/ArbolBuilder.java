package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.NivelArbol;

import java.util.ArrayList;
import java.util.List;

public class ArbolBuilder {

    private static ArbolBuilder arbolBuilder;

    private List<NivelArbol> nivelesArbol;

    private Integer idArbol = 1000;
    private String nombreArbol = "normativas";

    private Integer idNivelGenerales = 1001;
    private String nombreNivelGenerales = "Generales";
    private NivelArbol nivelPadreDeGenerales = null;

    private Integer idSubnivelLeyes = 1002;
    private String nombreSubnivelLeyes = "Leyes";

    private Integer idSubnivelReglamentos = 1003;
    private String nombreSubnivelReglamentos = "Reglamentos";

    private Integer idSubnivelTest = 1004;
    private String nombreSubnivelTest = "subNivelDeReglamentos";

    public Arbol generar() {
        Arbol arbol = new Arbol(idArbol, nombreArbol);
        NivelArbol nivelGenerales = new NivelArbol(idNivelGenerales, nombreNivelGenerales, nivelPadreDeGenerales, arbol);
        NivelArbol nivelReglamentos = new NivelArbol(idSubnivelReglamentos, nombreSubnivelReglamentos, nivelGenerales, arbol);
        NivelArbol nivelLeyes = new NivelArbol(idSubnivelLeyes, nombreSubnivelLeyes, nivelGenerales, arbol);
        NivelArbol subnivelTest = new NivelArbol(idSubnivelTest, nombreSubnivelTest, nivelReglamentos, arbol);

        List<NivelArbol> nivelesArbolNormativas = new ArrayList<>();
        nivelesArbolNormativas.add(nivelGenerales);
        nivelesArbolNormativas.add(nivelLeyes);
        nivelesArbolNormativas.add(nivelReglamentos);
        nivelesArbolNormativas.add(subnivelTest);

        arbol.getNivelesArbol().addAll(nivelesArbolNormativas);

        return arbol;
    }

    public static ArbolBuilder nuevoArbol() {
        arbolBuilder = new ArbolBuilder();
        return arbolBuilder;
    }
}
