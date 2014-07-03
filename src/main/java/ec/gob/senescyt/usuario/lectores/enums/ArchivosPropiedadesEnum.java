package ec.gob.senescyt.usuario.lectores.enums;

public enum ArchivosPropiedadesEnum {

    ARCHIVO_VALIDACIONES("ValidationMessages");

    private String baseName;

    private ArchivosPropiedadesEnum(final String baseName){
        this.baseName = baseName;
    }

    public String getBaseName(){
        return this.baseName;
    }
}
