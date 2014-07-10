package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/perfil")
@Produces(MediaType.APPLICATION_JSON)
public class PerfilResource {

    private final PerfilDAO perfilDAO;

    public PerfilResource(PerfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
    }

    @POST
    @UnitOfWork
    public Response crearPerfil(Perfil perfil) {
        if (!perfil.isValido()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Perfil perfilCreado = perfilDAO.guardar(perfil);
        return Response.status(Response.Status.CREATED).entity(perfilCreado).build();
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() throws JsonProcessingException {
        List<Perfil> perfiles = perfilDAO.obtenerTodos();

        return Response.ok(perfiles).build();
    }
}
