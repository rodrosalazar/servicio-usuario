package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/instituciones")
@Produces(MediaType.APPLICATION_JSON)
public class InstitucionResource {
    private InstitucionDAO institucionDAO;

    public InstitucionResource(InstitucionDAO institucionDAO) {
        this.institucionDAO = institucionDAO;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodas() throws JsonProcessingException {
        List<Institucion> instituciones = institucionDAO.obtenerTodas();

        return Response.ok(instituciones).build();
    }

}
