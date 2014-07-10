package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cantones")
@Produces(MediaType.APPLICATION_JSON)
public class CantonResource {
    private final CantonDAO cantonDAO;

    public CantonResource(CantonDAO cantonDAO) {
        this.cantonDAO = cantonDAO;
    }

    @GET
    @Path("{idProvincia}")
    @UnitOfWork
    public Response obtenerCantonesPorProvincia(@PathParam("idProvincia") String idProvincia) {
        List<Canton> cantonesParaProvincia = cantonDAO.obtenerPorProvincia(idProvincia);

        return Response.ok().entity(cantonesParaProvincia).build();
    }
}
