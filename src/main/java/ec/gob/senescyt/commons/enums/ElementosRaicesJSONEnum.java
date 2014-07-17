package ec.gob.senescyt.commons.enums;

public enum ElementosRaicesJSONEnum {
    ELEMENTO_RAIZ_PROVINCIAS("provincias"),
    ELEMENTO_RAIZ_CANTONES("cantones"),
    ELEMENTO_RAIZ_PARROQUIAS("parroquias"),
    ELEMENTO_RAIZ_CATEGORIA_VISA("categorias"),
    ELEMENTO_RAIZ_PERFILES("perfiles"),
    ELEMENTO_RAIZ_PAISES("paises"),
    ELEMENTO_RAIZ_INSTITUCIONES("instituciones"),
    ELEMENTO_RAIZ_ETNIAS("etnias");

    private String nombre;

    private ElementosRaicesJSONEnum(final String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

}