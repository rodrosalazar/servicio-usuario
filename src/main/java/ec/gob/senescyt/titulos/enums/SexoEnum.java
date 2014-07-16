package ec.gob.senescyt.titulos.enums;

public enum SexoEnum {

    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private String descripcion;

    private SexoEnum(final String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

}
