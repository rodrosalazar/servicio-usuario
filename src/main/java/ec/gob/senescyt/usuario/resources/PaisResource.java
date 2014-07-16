package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.titulos.core.Pais;
import ec.gob.senescyt.titulos.dao.PaisDAO;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
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
    private final ConstructorRespuestas constructorRespuestas;

    public PaisResource(PaisDAO paisDAO, ConstructorRespuestas constructorRespuestas) {
        this.paisDAO = paisDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Pais> paises = paisDAO.obtenerTodos();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PAISES, paises);
    }
}
