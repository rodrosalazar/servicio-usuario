package ec.gob.senescyt.ayudantes;

import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;
import org.apache.commons.lang.RandomStringUtils;

import static com.google.common.collect.Lists.newArrayList;

public class AyudantePerfil {

    public Perfil construirConPermisos(Permiso permiso) {
        return new Perfil(RandomStringUtils.random(10).toString(), newArrayList(permiso));
    }

    public Perfil construir() {
        Perfil perfil = new Perfil();
        perfil.setNombre(RandomStringUtils.random(10).toString());
        return perfil;
    }

    public Perfil construirConPermisos() {
        Perfil perfil = new Perfil();
        perfil.setNombre(RandomStringUtils.random(10).toString());
        perfil.setPermisos(newArrayList(new Permiso(RandomStringUtils.random(10).toString(), Acceso.CREAR)));
        return perfil;
    }
}
