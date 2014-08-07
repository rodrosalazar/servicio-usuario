package ec.gob.senescyt.titulos.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.ModalidadEducacion;
import ec.gob.senescyt.titulos.core.NivelDeFormacion;
import ec.gob.senescyt.titulos.core.TipoDeInstitucion;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/catalogos")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogosResource {

    private ConstructorRespuestas constructorRespuestas;

    public CatalogosResource(ConstructorRespuestas constructorRespuestas) {
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @Path("/tiposDeInstitucion")
    public Response obtenerTiposDeInstitucion(){
        List values = TipoDeInstitucion.getAll();
        return constructorRespuestas
                .construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_TIPOS_INSTITUCION, values);
    }

    @GET
    @Path("/nivelesDeFormacion")
    public Response obtenerNivelesDeFormacion(){
        List values = NivelDeFormacion.getAll();
        return constructorRespuestas
                .construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_NIVELES_FORMACION, values);
    }

    @GET
    @Path("/modalidadesDeEducacion")
    public Response obtenerModalidadesDeEducacion(){
        List values = ModalidadEducacion.getAll();
        return constructorRespuestas
                .construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_MODALIDADES_EDUCACION, values);
    }

}
