package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoDeMecanismo {
    APOSTILLA("Apostilla"),
    LEGALIZACION("Legalizacion"),
    NINGUNO("Ninguno");

    private String valor;

    private TipoDeMecanismo(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public String getId() {
        return this.name();
    }

    public static List<TipoDeMecanismo> getAll() {
        return newArrayList(APOSTILLA, LEGALIZACION,NINGUNO);
    }
}
