package ec.gob.senescyt.titulos.enums;

public enum GeneroEnum {

    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private String descripcion;

    private GeneroEnum(final String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

}
