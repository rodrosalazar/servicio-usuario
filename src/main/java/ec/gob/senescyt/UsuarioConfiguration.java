package ec.gob.senescyt;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import ec.gob.senescyt.usuario.configuracion.ConfiguracionBSG;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UsuarioConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("database")
    private final DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("configuracionEmail")
    private final ConfiguracionEmail configuracionEmail = new ConfiguracionEmail();

    @Valid
    @NotNull
    @JsonProperty("configuracionBSG")
    private final ConfiguracionBSG configuracionBSG = new ConfiguracionBSG();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public ConfiguracionEmail getConfiguracionEmail() {
        return configuracionEmail;
    }

    public ConfiguracionBSG getConfiguracionBSG() {
        return configuracionBSG;
    }

    public String getDefaultSchema() {
        return database.getProperties().get("hibernate.default_schema");
    }
}
