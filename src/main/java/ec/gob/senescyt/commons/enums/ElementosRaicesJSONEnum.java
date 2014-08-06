package ec.gob.senescyt.commons.enums;

public enum ElementosRaicesJSONEnum {
    ELEMENTO_RAIZ_PROVINCIAS("provincias"),
    ELEMENTO_RAIZ_CANTONES("cantones"),
    ELEMENTO_RAIZ_PARROQUIAS("parroquias"),
    ELEMENTO_RAIZ_CATEGORIA_VISA("categorias"),
    ELEMENTO_RAIZ_PERFILES("perfiles"),
    ELEMENTO_RAIZ_PAISES("paises"),
    ELEMENTO_RAIZ_INSTITUCIONES("instituciones"),
    ELEMENTO_RAIZ_ETNIAS("etnias"),
    ELEMENTO_RAIZ_TIPO_VISA("tiposDeVisa"),
    ELEMENTO_RAIZ_ARBOLES("arboles"),
    ELEMENTO_RAIZ_UNIVERSIDADES_CONVENIO("universidadesConvenio"),
    ELEMENTO_RAIZ_UNIVERSIDADES_LISTADO("universidadesListado"),
    ELEMENTO_RAIZ_TIPOS_INSTITUCION("tiposDeInstitucion");

    private String nombre;

    private ElementosRaicesJSONEnum(final String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

}
