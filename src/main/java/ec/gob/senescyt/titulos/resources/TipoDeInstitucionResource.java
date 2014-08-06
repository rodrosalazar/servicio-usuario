package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.TipoDeInstitucion;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tiposDeInstitucion")
@Produces(MediaType.APPLICATION_JSON)
public class TipoDeInstitucionResource {

    private ConstructorRespuestas constructorRespuestas;

    public TipoDeInstitucionResource(ConstructorRespuestas constructorRespuestas) {
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    public Response obtenerTodos(){
        List values = TipoDeInstitucion.getAll();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_TIPOS_INSTITUCION, values);
    }

}
