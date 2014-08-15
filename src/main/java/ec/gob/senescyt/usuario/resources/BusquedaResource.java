package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.core.Token;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.TokenDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;
import ec.gob.senescyt.usuario.services.ServicioCedula;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("/busqueda")
@Produces(MediaType.APPLICATION_JSON)
public class BusquedaResource {

    private TokenDAO tokenDAO;
    private ServicioCedula servicioCedula;
    private UsuarioDAO usuarioDAO;
    private MensajeErrorBuilder mensajeErrorBuilder;

    public BusquedaResource(ServicioCedula servicioCedula, TokenDAO tokenDAO, UsuarioDAO usuarioDAO, LectorArchivoDePropiedades lectorPropiedadesValidacion) {
        this.servicioCedula = servicioCedula;
        this.tokenDAO = tokenDAO;
        this.usuarioDAO = usuarioDAO;
        this.mensajeErrorBuilder =  new MensajeErrorBuilder(lectorPropiedadesValidacion);
    }

    @GET
    @UnitOfWork
    public Response buscar(@QueryParam("cedula") String cedula,
                           @QueryParam("token") String idToken,
                           @QueryParam("identificacion")com.google.common.base.Optional<String> idUsuario) {
        if (cedula != null) {
            return buscarCedula(cedula);
        }
        if (idToken != null) {
            return buscarToken(idToken);
        }
        if (idUsuario.isPresent()) {
            return buscarUsuarioPorIdentificacion(idUsuario.get());
        }

        return Response.status(NOT_FOUND).build();
    }

    private Response buscarUsuarioPorIdentificacion(String identificacion) {
        com.google.common.base.Optional<Usuario> usuario = usuarioDAO.buscarPorIdentificacion(identificacion);

        if (usuario.isPresent()) {
            return Response.ok(usuario).build();
        }
        return Response.status(BAD_REQUEST).entity(mensajeErrorBuilder.mensajeUsuarioNoEncontrado()).build();
    }

    private Response buscarCedula(String cedula) {
        try {
            CedulaInfo info = servicioCedula.buscar(cedula);
            return Response.ok(info).build();
        } catch (ServicioNoDisponibleException snde) {
            return Response.status(SERVICE_UNAVAILABLE).entity(snde.getMessage()).build();
        } catch (CedulaInvalidaException | CredencialesIncorrectasException cie) {
            return Response.status(BAD_REQUEST).entity(cie.getMessage()).build();
        }
    }

    private Response buscarToken(String idToken) {
        Optional<Token> token = tokenDAO.buscar(idToken);
        if (token.isPresent()) {
            return Response.ok(token.get()).build();
        }
        return Response.status(NOT_FOUND).build();
    }
}
