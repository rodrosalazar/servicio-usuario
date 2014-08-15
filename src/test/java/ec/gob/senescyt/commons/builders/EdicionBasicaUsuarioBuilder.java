package ec.gob.senescyt.commons.builders;

import com.google.common.collect.Lists;
import ec.gob.senescyt.usuario.core.EstadoUsuario;
import ec.gob.senescyt.usuario.dto.EdicionBasicaUsuario;

import java.util.List;
import java.util.function.Consumer;

public class EdicionBasicaUsuarioBuilder {
    public EstadoUsuario estadoUsuario = EstadoUsuario.ACTIVO;
    public List<Long> perfiles = Lists.newArrayList();

    private static EdicionBasicaUsuarioBuilder builder;

    public static EdicionBasicaUsuarioBuilder nuevaEdicionBasicaUsuario() {
        builder = new EdicionBasicaUsuarioBuilder();
        return builder;
    }

    public EdicionBasicaUsuarioBuilder con(Consumer<EdicionBasicaUsuarioBuilder> consumer){
        consumer.accept(builder);
        return builder;
    }

    public EdicionBasicaUsuario generar() {
        return new EdicionBasicaUsuario(estadoUsuario, perfiles);
    }
}
