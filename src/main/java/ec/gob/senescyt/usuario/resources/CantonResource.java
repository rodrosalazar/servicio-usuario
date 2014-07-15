package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Parroquia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ParroquiaDAO;
import ec.gob.senescyt.usuario.enums.ElementosRaicesJSONEnum;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/cantones")
@Produces(MediaType.APPLICATION_JSON)
public class CantonResource {

    private final CantonDAO cantonDAO;
    private final ParroquiaDAO parroquiaDAO;


    public CantonResource(CantonDAO cantonDAO, ParroquiaDAO parroquiaDAO) {
        this.parroquiaDAO = parroquiaDAO;
        this.cantonDAO = cantonDAO;
    }

    @GET
    @Path("{idCanton}/parroquias")
    @UnitOfWork
    public Response obtenerParroquiasParaCanton(@PathParam("idCanton") String idCanton) {
        List<Parroquia> parroquiasParaCanton = parroquiaDAO.obtenerPorCanton(idCanton);

        Map parroquiasWrapper = new HashMap<String,List<Parroquia>>();

        parroquiasWrapper.put(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PARROQUIAS.getNombre(),parroquiasParaCanton);

        return Response.ok().entity(parroquiasWrapper).build();
    }
}
