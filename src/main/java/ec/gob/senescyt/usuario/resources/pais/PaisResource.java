package ec.gob.senescyt.usuario.resources.pais;

import ec.gob.senescyt.usuario.core.pais.Pais;
import ec.gob.senescyt.usuario.dao.pais.PaisDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/paises")
@Produces(MediaType.APPLICATION_JSON)
public class PaisResource {

    private PaisDAO paisDAO;

    public PaisResource(PaisDAO paisDAO) {
        this.paisDAO = paisDAO;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {

        List<Pais> paises = paisDAO.obtenerTodos();

        return Response.ok(paises).build();
    }
}
