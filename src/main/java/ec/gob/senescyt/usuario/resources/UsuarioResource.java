package ec.gob.senescyt.usuario.resources;

import com.google.common.base.Optional;
import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO;
    private CedulaValidator cedulaValidator;
    private LectorArchivoDePropiedades lectorArchivoDePropiedades;
    private MensajeErrorBuilder mensajeErrorBuilder;

    public UsuarioResource(final UsuarioDAO usuarioDAO, final CedulaValidator cedulaValidator, final LectorArchivoDePropiedades lectorArchivoDePropiedades) {
        this.usuarioDAO = usuarioDAO;
        this.cedulaValidator = cedulaValidator;
        this.lectorArchivoDePropiedades = lectorArchivoDePropiedades;
        this.mensajeErrorBuilder = new MensajeErrorBuilder(this.lectorArchivoDePropiedades);
    }

    @GET
    @Path("/validacion")
    @UnitOfWork
    public Response validar(@QueryParam("cedula") final Optional<String> cedula,
                            @QueryParam("nombreUsuario") final Optional<String> nombreUsuario,
                            @QueryParam("numeroIdentificacion") final Optional<String> numeroIdentificacion) {

        if (cedula.isPresent()) {
            if (cedulaValidator.isValidaCedula(cedula.get())) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeErrorBuilder.mensajeNumeroIdentificacionInvalido()).build();
        }

        if (nombreUsuario.isPresent()) {
            if (!usuarioDAO.isRegistradoNombreUsuario(nombreUsuario.get())) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()).build();
        }

        if (numeroIdentificacion.isPresent()) {
            if (!usuarioDAO.isRegistradoNumeroIdentificacion(numeroIdentificacion.get())) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @UnitOfWork
    public Response crear(@Valid final Usuario usuario) {
        if (usuario.getNombreUsuario() != null
                && usuarioDAO.isRegistradoNombreUsuario(usuario.getNombreUsuario())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeErrorBuilder.mensajeNombreDeUsuarioYaHaSidoRegistrado()).build();
        }

        if (usuario.getIdentificacion().getNumeroIdentificacion() != null
                && usuarioDAO.isRegistradoNumeroIdentificacion(usuario.getIdentificacion().getNumeroIdentificacion())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(mensajeErrorBuilder.mensajeNumeroIdentificacionYaHaSidoRegistrado()).build();
        }

        Usuario usuarioCreado = usuarioDAO.guardar(usuario);

        return Response.status(Response.Status.CREATED).entity(usuarioCreado).build();
    }
}
