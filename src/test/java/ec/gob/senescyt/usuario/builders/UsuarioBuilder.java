package ec.gob.senescyt.usuario.builders;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class UsuarioBuilder {
    private static final TipoDocumentoEnum TIPO_DOCUMENTO_CEDULA = TipoDocumentoEnum.CEDULA;
    private static final TipoDocumentoEnum TIPO_DOCUMENTO_PASAPORTE = TipoDocumentoEnum.PASAPORTE;
    private static final String CEDULA_VALIDA = "1718642174";
    private static final String CEDULA_INVALIDA = "11";
    private static final String PRIMER_NOMBRE = "Nelson";
    private static final String SEGUNDO_NOMBRE = "Alberto";
    private static final String PRIMER_APELLIDO = "Jumbo";
    private static final String SEGUNDO_APELLIDO = "Hidalgo";
    private static final String EMAIL_VALIDO = "test@senescyt.gob.ec";
    private static final String EMAIL_INVALIDO = "EMAIL_INVALIDO";
    private static final String NUMERO_QUIPUX_INVALIDO = "123456";
    private static final String NUMERO_QUIPUX_VALIDO = "SENESCYT-DFAPO-2014-65946-MI";
    private static final DateTime FECHA_ACTUAL = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    private static final DateTime FECHA_HACE_UN_MES = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay().minusMonths(1);
    private static final long ID_INSTITUCION = 1l;
    private static final String NOMBRE_USUARIO = "usuarioSenescyt";
    private static final String CAMPO_EN_BLANCO = "";
    private static final String PASAPORTE = "AAD11234";

    public static Usuario usuarioConCedulaEnBlanco(){
         return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CAMPO_EN_BLANCO),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }

    public static Usuario usuarioConNumeroQuipuxEnBlanco() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, CAMPO_EN_BLANCO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }

    public static Usuario usuarioConPasaporte() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_PASAPORTE, PASAPORTE),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }

    public static Usuario usuarioConEmailInvalido() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_INVALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }

    public static Usuario usuarioValido(){
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }

    public static Usuario usuarioConFechaDeVigenciaInvalida() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_HACE_UN_MES,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }

    public static Usuario usuarioConNumeroQuipuxInvalido() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_INVALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO);
    }
}
