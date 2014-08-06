package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoDeInstitucion {
    PUBLICA("Pública"),
    PRIVADA("Privada");

    private String valor;

    private TipoDeInstitucion(String valor){
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public String getId() {
        return this.name();
    }

    public static List<TipoDeInstitucion> getAll() {
        return newArrayList(PUBLICA, PRIVADA);
    }
}
