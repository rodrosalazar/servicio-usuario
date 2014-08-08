package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoDeTitulo {
    PROFESIONAL("Profesional"),
    ACADEMICO("Academico");

    private String valor;

    private TipoDeTitulo(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public String getId() {
        return this.name();
    }

    public static List<TipoDeTitulo> getAll() {
        return newArrayList(PROFESIONAL, ACADEMICO);
    }
}
