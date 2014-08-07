package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.Canton;
import ec.gob.senescyt.titulos.core.Provincia;
import ec.gob.senescyt.titulos.dao.CantonDAO;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/provincias")
@Produces(MediaType.APPLICATION_JSON)
public class ProvinciaResource {

    private ProvinciaDAO provinciaDAO;
    private final CantonDAO cantonDAO;
    private final ConstructorRespuestas constructorRespuestas;


    public ProvinciaResource(ProvinciaDAO provinciaDAO, CantonDAO cantonDAO, ConstructorRespuestas constructorRespuestas) {
        this.provinciaDAO = provinciaDAO;
        this.cantonDAO = cantonDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Provincia> provincias = provinciaDAO.obtenerTodos();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PROVINCIAS,provincias);
    }


    @GET
    @Path("{idProvincia}/cantones")
    @UnitOfWork
    public Response obtenerCantonesPorProvincia(@PathParam("idProvincia") String idProvincia) {
        List<Canton> cantonesParaProvincia = cantonDAO.obtenerPorProvincia(idProvincia);
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES, cantonesParaProvincia);
    }
}
