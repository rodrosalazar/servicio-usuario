package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Etnia;
import ec.gob.senescyt.titulos.dao.EtniaDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etnias")
@Produces(MediaType.APPLICATION_JSON)
public class EtniaResource {
    private EtniaDAO etniaDAO;
    private ConstructorRespuestas constructorRespuestas;

    public EtniaResource(EtniaDAO etniaDAO, ConstructorRespuestas constructorRespuestas) {
        this.etniaDAO = etniaDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Etnia> etnias = etniaDAO.obtenerTodos();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ETNIAS, etnias);
    }
}
