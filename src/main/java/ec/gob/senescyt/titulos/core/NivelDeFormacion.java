package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NivelDeFormacion {
    TECNICO("Técnico"),
    TECNOLOGICO("Tecnológico"),
    TERCER_NIVEL("Tercer Nivel"),
    CUARTO_NIVEL("Cuarto Nivel");

    private String valor;

    private NivelDeFormacion(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public String getId() {
        return this.name();
    }

    public static List<NivelDeFormacion> getAll() {
        return newArrayList(TECNICO, TECNOLOGICO, TERCER_NIVEL, CUARTO_NIVEL);
    }
}
