package ec.gob.senescyt.usuario.enums;

public enum ElementosRaicesJSONEnum {
    ELEMENTO_RAIZ_PROVINCIAS("provincias"),
    ELEMENTO_RAIZ_CANTONES("cantones"),
    ELEMENTO_RAIZ_PARROQUIAS("parroquias"),
    ELEMENTO_RAIZ_CATEGORIA_VISA("categorias");

    private String nombre;

    private ElementosRaicesJSONEnum(final String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

}
