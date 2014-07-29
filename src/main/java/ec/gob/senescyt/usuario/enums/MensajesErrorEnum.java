package ec.gob.senescyt.usuario.enums;

public enum MensajesErrorEnum {
    CAMPO_NUMERO_IDENTIFICACION("ec.gob.senescyt.campo.numeroIdentificacion"),
    CAMPO_NOMBRE_USUARIO("ec.gob.senescyt.campo.nombreUsuario"),
    MENSAJE_ERROR_CEDULA_INVALIDA("ec.gob.senescyt.error.cedula"),
    MENSAJE_ERROR_NUMERO_IDENTIFICACION_YA_HA_SIDO_REGISTRADO("ec.gob.senescyt.error.nombreUsuarioExistente"),
    MENSAJE_ERROR_NOMBRE_USUARIO_YA_HA_SIDO_REGISTRADO("ec.gob.senescyt.error.nombreUsuarioExistente");
    private String key;

    private MensajesErrorEnum(final String baseName){
        this.key = baseName;
    }

    public String getKey(){
        return this.key;
    }
}