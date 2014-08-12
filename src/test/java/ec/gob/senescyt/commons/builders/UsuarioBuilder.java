package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

public class UsuarioBuilder {

    public TipoDocumento tipoDocumento = TipoDocumento.CEDULA;
    public String numeroIdentificacion = "1111111116";
    public String primerNombre = "Lorem";
    public String segundoNombre = "Ipsum";
    public String primerApellido = "Dolor";
    public String segundoApellido = "Sit";
    public String emailInstitucional = "test@example.com";
    public String numeroAutorizacionQuipux = "SENESCYT-DFAPO-2014-65946-MI";
    public DateTime fechaDeVigencia = new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    public Long idInstitucion = 1L;
    public List<Long> perfiles = newArrayList(1L);
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

}
