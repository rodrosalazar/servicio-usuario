package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Canton;
import ec.gob.senescyt.usuario.core.Provincia;
import ec.gob.senescyt.usuario.dao.CantonDAO;
import ec.gob.senescyt.usuario.dao.ProvinciaDAO;
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

@Path("/provincias")
@Produces(MediaType.APPLICATION_JSON)
public class ProvinciaResource {

    private ProvinciaDAO provinciaDAO;
    private final CantonDAO cantonDAO;


    public ProvinciaResource(ProvinciaDAO provinciaDAO, CantonDAO cantonDAO) {
        this.provinciaDAO = provinciaDAO;
        this.cantonDAO = cantonDAO;
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() {
        List<Provincia> provincias = provinciaDAO.obtenerTodos();
        Map provinciasWrapper = new HashMap<String,List<Canton>>();

        provinciasWrapper.put(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PROVINCIAS.getNombre(), provincias);
        return Response.ok(provinciasWrapper).build();
    }


    @GET
    @Path("{idProvincia}/cantones")
    @UnitOfWork
    public Response obtenerCantonesPorProvincia(@PathParam("idProvincia") String idProvincia) {
        List<Canton> cantonesParaProvincia = cantonDAO.obtenerPorProvincia(idProvincia);

        Map cantonesWrapper = new HashMap<String,List<Canton>>();

        cantonesWrapper.put(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES.getNombre(),cantonesParaProvincia);

        return Response.ok().entity(cantonesWrapper).build();
    }
}
