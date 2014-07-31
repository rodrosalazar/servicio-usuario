package ec.gob.senescyt.commons.lectores.enums;

public enum ArchivosPropiedadesEnum {

    ARCHIVO_VALIDACIONES("ValidationMessages"),
    ARCHIVO_PROPIEDADES_EMAIL("PropiedadesEmail");

    private String baseName;

    private ArchivosPropiedadesEnum(final String baseName){
        this.baseName = baseName;
    }

    public String getBaseName(){
        return this.baseName;
    }
}
