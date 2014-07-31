package ec.gob.senescyt.usuario.enums;

public enum PropiedadesEmailEnum {
    ASUNTO("ec.gob.senescyt.email.usuario.confirmacion.asunto"),
    URL("ec.gob.senescyt.email.usuario.confirmacion.url");
    private String key;

    private PropiedadesEmailEnum(final String baseName){
        this.key = baseName;
    }

    public String getKey(){
        return this.key;
    }
}