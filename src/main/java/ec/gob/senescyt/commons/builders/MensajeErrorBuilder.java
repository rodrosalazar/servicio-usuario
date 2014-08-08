package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.usuario.enums.MensajesErrorEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class MensajeErrorBuilder {

    private final LectorArchivoDePropiedades lectorArchivoDePropiedades;
    private static final String SEPARADOR_GRUPOS = " ";

    public MensajeErrorBuilder(LectorArchivoDePropiedades lectorArchivoDePropiedades) {
        this.lectorArchivoDePropiedades = lectorArchivoDePropiedades;
    }

    public String mensajeNumeroIdentificacionInvalido() {
        return lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.CAMPO_NUMERO_IDENTIFICACION.getKey()) +
                SEPARADOR_GRUPOS +
                lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.MENSAJE_ERROR_CEDULA_INVALIDA.getKey());
    }

    public String mensajeNombreDeUsuarioYaHaSidoRegistrado() {
        return lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.CAMPO_NOMBRE_USUARIO.getKey()) +
                SEPARADOR_GRUPOS +
                lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.MENSAJE_ERROR_NOMBRE_USUARIO_YA_HA_SIDO_REGISTRADO.getKey());
    }

    public String mensajeNumeroIdentificacionYaHaSidoRegistrado() {
        return lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.CAMPO_NUMERO_IDENTIFICACION.getKey()) +
                SEPARADOR_GRUPOS +
                lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.MENSAJE_ERROR_NUMERO_IDENTIFICACION_YA_HA_SIDO_REGISTRADO.getKey());
    }

    public String mensajeLoginIncorrecto(){
        return lectorArchivoDePropiedades.leerPropiedad(MensajesErrorEnum.MENSAJE_ERROR_LOGIN.getKey());
    }

    public Map<String, List<String>> construirErrorCampo(String campo, MensajesErrorEnum mensajeError) {
        Map<String, List<String>> entidadRespuesta = new HashMap<>();
        entidadRespuesta.put("errors", newArrayList(campo + " " +
                lectorArchivoDePropiedades.leerPropiedad(mensajeError.getKey())));

        return entidadRespuesta;
    }
}
