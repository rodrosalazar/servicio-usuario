package ec.gob.senescyt.usuario.resources.management;

import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.core.Nombre;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import ec.gob.senescyt.usuario.utils.Hasher;
import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.google.common.collect.Lists.newArrayList;

@Path("/limpieza")
@Produces(MediaType.APPLICATION_JSON)
public class LimpiezaResource {

    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;
    private InstitucionDAO institucionDAO;
    private CredencialDAO credencialDAO;
    private Hasher hasher;

    public LimpiezaResource(UsuarioDAO usuarioDAO, PerfilDAO perfilDAO, InstitucionDAO institucionDAO,
                            CredencialDAO credencialDAO, Hasher hasher) {
        this.usuarioDAO = usuarioDAO;
        this.perfilDAO = perfilDAO;
        this.institucionDAO = institucionDAO;
        this.credencialDAO = credencialDAO;
        this.hasher = hasher;
    }

    @GET
    @UnitOfWork
    public Response inicializarDatosParaPruebas() {
        usuarioDAO.limpiar();
        perfilDAO.limpiar();

        String nombreSuperUsuario = "superUser";
        String password = "superUser123";

        Perfil perfilCreado = guardarPerfil(nombreSuperUsuario);
        guardarUsuario(nombreSuperUsuario, perfilCreado.getId());

        guardarCredencial(nombreSuperUsuario, password);
        CredencialLogin credencialLogin = new CredencialLogin(nombreSuperUsuario, password);

        return Response.ok(credencialLogin).build();
    }


    private Perfil guardarPerfil(String nombreSuperUsuario) {
        Perfil perfil = new Perfil(nombreSuperUsuario, newArrayList(new Permiso("nombre", Acceso.CREAR)));
        perfilDAO.guardar(perfil);
        return perfil;
    }

    private void guardarUsuario(String nombreSuperUsuario, Long idPerfil) {
        Nombre nombre = new Nombre(nombreSuperUsuario, null, nombreSuperUsuario, null);
        DateTime now = DateTime.now();
        Institucion institucion = institucionDAO.obtenerTodas().get(0);

        Identificacion identificacion = new Identificacion(TipoDocumento.PASAPORTE,"USER123");

        Usuario usuario = new Usuario(identificacion, nombre, "myeamil@myemail.com", "SENESCYT-DFAPO-2014-65946-MI",
                now, institucion, nombreSuperUsuario, newArrayList(idPerfil));
        usuarioDAO.guardar(usuario);
    }

    private void guardarCredencial(String nombreSuperUsuario, String password) {
        Credencial credencial = new Credencial(nombreSuperUsuario, hasher.calcularHash(password));
        credencialDAO.guardar(credencial);
    }
}
