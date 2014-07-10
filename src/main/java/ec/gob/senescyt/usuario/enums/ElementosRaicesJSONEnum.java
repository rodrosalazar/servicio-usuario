package ec.gob.senescyt.usuario.enums;

public enum ElementosRaicesJSONEnum {
    ELEMENTO_RAIZ_PROVINCIAS("provincias"),
    ELEMENTO_RAIZ_CANTONES("cantones");

    private String nombre;

    private ElementosRaicesJSONEnum(final String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
}
