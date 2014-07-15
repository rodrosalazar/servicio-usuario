package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.CategoriaVisa;
import ec.gob.senescyt.usuario.core.Parroquia;
import ec.gob.senescyt.usuario.dao.CategoriaVisaDAO;
import ec.gob.senescyt.usuario.dao.TipoVisaDAO;
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

@Path("/tiposDeVisa")
@Produces(MediaType.APPLICATION_JSON)
public class TipoDeVisaResource {

    private TipoVisaDAO tipoVisaDAO;
    private CategoriaVisaDAO categoriaVisaDAO;

    public TipoDeVisaResource(TipoVisaDAO tipoVisaDAO, CategoriaVisaDAO categoriaVisaDAO) {
        this.tipoVisaDAO = tipoVisaDAO;
        this.categoriaVisaDAO = categoriaVisaDAO;
    }

    @GET
    @Path("{idTipoVisa}/categorias")
    @UnitOfWork
    public Response obtenerCategoriasParaTipoVisa(@PathParam("idTipoVisa") String idTipoVisa) {
        List<CategoriaVisa> categoriasParaTipoVisa = this.categoriaVisaDAO.obtenerPorTipoVisa(idTipoVisa);

        Map categoriasWrapper = new HashMap<String,List<CategoriaVisa>>();

        categoriasWrapper.put(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CATEGORIA_VISA.getNombre(), categoriasParaTipoVisa);

        return Response.ok().entity(categoriasWrapper).build();
    }
}
