package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
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

    public UsuarioResource(final UsuarioDAO usuarioDAO, CedulaValidator cedulaValidator) {
        this.usuarioDAO = usuarioDAO;
        this.cedulaValidator = cedulaValidator;
    }


    @GET
    @Path("/validacion")
    public Response verificarCedula(@QueryParam("cedula") final String cedula) {
        if (cedulaValidator.isValidaCedula(cedula)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Debe ingresar una cédula válida").build();
    }

    @GET
    @Path("/existe/nombreUsuario/{nombreUsuario}")
    @UnitOfWork
    public Response verificarExistenciaNombreUsuario(@PathParam("nombreUsuario")final String nombreUsuario) {
        if(usuarioDAO.isRegistradoNombreUsuario(nombreUsuario)){
            return Response.status(Response.Status.OK).entity("El nombre de usuario ya ha sido registrado").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/existe/numeroIdentificacion/{numeroIdentificacion}")
    @UnitOfWork
    public Response verificarExistenciaNumeroIdentificacion(@PathParam("numeroIdentificacion") final String numeroIdentificacion) {
        if(usuarioDAO.isRegistradoNumeroIdentificacion(numeroIdentificacion)){
            return Response.status(Response.Status.OK).entity("El número de identificación ya ha sido registrado").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @UnitOfWork
    public Response crearUsuario(@Valid final Usuario usuario) {
        Usuario usuarioCreado = usuarioDAO.guardar(usuario);


        return Response.status(Response.Status.CREATED).entity(usuarioCreado).build();
    }
}
