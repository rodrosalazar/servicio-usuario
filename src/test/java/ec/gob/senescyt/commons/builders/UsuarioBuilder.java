package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

public class UsuarioBuilder {
    private static final TipoDocumentoEnum TIPO_DOCUMENTO_CEDULA = TipoDocumentoEnum.CEDULA;
    private static final TipoDocumentoEnum TIPO_DOCUMENTO_PASAPORTE = TipoDocumentoEnum.PASAPORTE;
    private static final String CEDULA_VALIDA = "1718642174";
    private static final String CEDULA_VALIDA_2 = "1804068953";
    private static final String CEDULA_INVALIDA = "11";
    private static final String PRIMER_NOMBRE = "Nelson";
    private static final String SEGUNDO_NOMBRE = "Alberto";
    private static final String PRIMER_APELLIDO = "Jumbo";
    private static final String SEGUNDO_APELLIDO = "Hidalgo";
    private static final String EMAIL_VALIDO = "test@senescyt.gob.ec";
    private static final String EMAIL_INVALIDO = "EMAIL_INVALIDO";
    private static final String NUMERO_QUIPUX_VALIDO = "SENESCYT-DFAPO-2014-65946-MI";
    private static final DateTime FECHA_ACTUAL = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    private static final long ID_INSTITUCION = 1l;
    private static final String NOMBRE_USUARIO = "usuarioSenescyt";
    private static final String NOMBRE_USUARIO_2 = "usuarioAdmin";
    private static final String CAMPO_EN_BLANCO = "";
    private static final String PASAPORTE_DE_21_DIGITOS = "123456789012345678901";

    public TipoDocumentoEnum tipoDocumento = TipoDocumentoEnum.CEDULA;
    public String numeroIdentificacion = "1111111116";
    public String primerNombre = "Lorem";
    public String segundoNombre = "Ipsum";
    public String primerApellido = "Dolor";
    public String segundoApellido = "Sit";
    public String emailInstitucional = "test@example.com";
    public String numeroAutorizacionQuipux = "SENESCYT-DFAPO-2014-65946-MI";
    public DateTime fechaDeVigencia = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    public long idInstitucion = 1l;
    public List<Long> perfiles = newArrayList(1l);
    public String nombreUsuario = "usuarioSenescyt";

    private static UsuarioBuilder usuarioBuilder;

    public static UsuarioBuilder nuevoUsuario() {
        usuarioBuilder = new UsuarioBuilder();
        return usuarioBuilder;
    }

    public UsuarioBuilder con(Consumer<UsuarioBuilder> consumer){
        consumer.accept(usuarioBuilder);
        return usuarioBuilder;
    }

    public Usuario generar() {
        return new Usuario(new Identificacion(tipoDocumento, numeroIdentificacion),
                new Nombre(primerNombre, segundoNombre, primerApellido, segundoApellido),
                emailInstitucional, numeroAutorizacionQuipux, fechaDeVigencia,
                idInstitucion, nombreUsuario, perfiles);
    }


    ///////////////////////////////// HERE BE DRAGONS /////////////////////////////////

    public static Usuario usuarioConDocumentoInvalido() {
        return new Usuario(new Identificacion(null, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, null);
    }

    public static Usuario usuarioConPrimerNombreEnBlanco() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(CAMPO_EN_BLANCO, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, null);
    }

    public static Usuario usuarioConPrimerApellidoEnBlanco() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, CAMPO_EN_BLANCO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, null);
    }

    public static Object usuarioConEmailEnBlanco() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                CAMPO_EN_BLANCO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, null);

    }

    public static Usuario usuarioConFechaDeVigenciaEnBlanco() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                null,
                ID_INSTITUCION, NOMBRE_USUARIO, null);
    }

    public static Usuario usuarioConIdInstitucionNulo() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                null, NOMBRE_USUARIO, null);
    }

    public static Usuario usuarioConNombreUsuarioEnBlanco() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, CAMPO_EN_BLANCO, null);
    }

    public static String usuarioConIdInstitucionEnBlanco() {
        return "{\n" +
                "    \"identificacion\": {\n" +
                "        \"tipoDocumento\": \"CEDULA\",\n" +
                "        \"numeroIdentificacion\": \"1718642174\"\n" +
                "    },\n" +
                "    \"nombre\": {\n" +
                "        \"primerNombre\": \"Nelson\",\n" +
                "        \"segundoNombre\": \"Alberto\",\n" +
                "        \"primerApellido\": \"Jumbo\",\n" +
                "        \"segundoApellido\": \"Hidalgo\"\n" +
                "    },\n" +
                "    \"emailInstitucional\":\"testEmail@senescyt.gob.ec\",\n" +
                "    \"numeroAutorizacionQuipux\":\"SENESCYT-DFAPO-2014-65946-MI\",\n" +
                "    \"finDeVigencia\":\"2016-07-25\",\n" +
                "    \"idInstitucion\":\"\",\n" +
                "    \"nombreUsuario\":\"njumbo\"\n" +
                "}";
    }

    public static Usuario usuarioValido1718642174UsuarioSenescyt(Perfil perfil) {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, newArrayList(perfil.getId()));
    }

    public static Usuario usuarioValido1804068953UsuarioSenescyt() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA_2),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, newArrayList(1l,2l,3l));
    }

    public static Usuario usuarioValido1718642174UsuarioAdmin() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO_2, newArrayList(1l,2l,3l));
    }

    public static Usuario usuarioConIdentificacionDeMasDe20Digitos() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_PASAPORTE, PASAPORTE_DE_21_DIGITOS),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO_2, null);
    }

    public static Usuario usuarioConPasaporteVacio() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_PASAPORTE, ""),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO_2, null);
    }

    public static Usuario usuarioSinPerfiles() {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, null);
    }

    public static Usuario usuarioValido(Perfil perfil) {
        return new Usuario(new Identificacion(TIPO_DOCUMENTO_CEDULA, CEDULA_VALIDA),
                new Nombre(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO),
                EMAIL_VALIDO, NUMERO_QUIPUX_VALIDO,
                FECHA_ACTUAL,
                ID_INSTITUCION, NOMBRE_USUARIO, newArrayList(perfil.getId()));
    }
}
