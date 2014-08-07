package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Parroquia;
import ec.gob.senescyt.titulos.dao.ParroquiaDAO;
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

    private final ParroquiaDAO parroquiaDAO;
    private final ConstructorRespuestas constructorRespuestas;

    public CantonResource(ParroquiaDAO parroquiaDAO, ConstructorRespuestas constructorRespuestas) {
        this.parroquiaDAO = parroquiaDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @Path("{idCanton}/parroquias")
    @UnitOfWork
    public Response obtenerParroquiasParaCanton(@PathParam("idCanton") String idCanton) {

        List<Parroquia> parroquiasParaCanton = parroquiaDAO.obtenerPorCanton(idCanton);
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PARROQUIAS, parroquiasParaCanton);
    }
}
