package ec.gob.senescyt.usuario.core;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class PerfilTest {
    private String nombre;
    private List<Permiso> permisos = newArrayList(new Permiso());
    private Perfil perfil;

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Test
    public void debeInicializarConNombreYPermisos() {
        perfil = new Perfil(nombre, permisos);
        assertThat(perfil.getNombre(), is(nombre));
        assertThat(perfil.getPermisos(), is(permisos));
    }

    @Test
    public void debeTenerIdAutoAsignado() {
        perfil = new Perfil(nombre, permisos);
        assertThat(perfil.getId(), is(nullValue()));
    }

    @Test
    public void debeNoTieneNombreVacio() {
        perfil = new Perfil();
        perfil.setPermisos(permisos);
        Set<ConstraintViolation<Perfil>> constraintViolations = validator.validate(perfil);
        assertThat(constraintViolations.size(), is(1));
        ConstraintViolation<Perfil> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().iterator().next().getName(), is("nombre"));
        assertThat(violation.getMessage(), is("El campo es obligatorio"));
    }

    @Test
    public void debeNoTienePermisosVacio() {
        perfil = new Perfil();
        perfil.setNombre(nombre);
        Set<ConstraintViolation<Perfil>> constraintViolations = validator.validate(perfil);
        assertThat(constraintViolations.size(), is(1));
        ConstraintViolation<Perfil> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().iterator().next().getName(), is("permisos"));
        assertThat(violation.getMessage(), is("El campo es obligatorio"));
    }

    @Test
    public void debeNombreNoSeaSuperiorA100() {
        nombre = RandomStringUtils.randomAlphabetic(101).toString();
        perfil = new Perfil(nombre, permisos);
        Set<ConstraintViolation<Perfil>> constraintViolations = validator.validate(perfil);
        assertThat(constraintViolations.size(), is(1));
        ConstraintViolation<Perfil> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().iterator().next().getName(), is("nombre"));
        assertThat(violation.getMessage(), is("El campo debe estar entre 1 y 100"));
    }
}

