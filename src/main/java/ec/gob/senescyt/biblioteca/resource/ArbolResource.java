package ec.gob.senescyt.biblioteca.resource;

import ec.gob.senescyt.biblioteca.Arbol;
import ec.gob.senescyt.biblioteca.dao.ArbolDAO;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/arboles")
@Produces(MediaType.APPLICATION_JSON)
public class ArbolResource {
    private ArbolDAO arbolDAO;
    private ConstructorRespuestas constructorRespuestas;

    public ArbolResource(ArbolDAO arbolDAO, ConstructorRespuestas constructorRespuestas) {
        this.arbolDAO = arbolDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Arbol> arboles = arbolDAO.obtenerTodos();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_ARBOLES, arboles);
    }

}
