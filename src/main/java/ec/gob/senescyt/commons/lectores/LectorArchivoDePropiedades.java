package ec.gob.senescyt.commons.lectores;

import java.util.ResourceBundle;

public class LectorArchivoDePropiedades {

    private ResourceBundle bundleArchivoDePropiedades;

    public LectorArchivoDePropiedades(String archivoDePropiedadesBaseName) {
        this.bundleArchivoDePropiedades = ResourceBundle.getBundle(archivoDePropiedadesBaseName);
    }

    public String leerPropiedad(String propiedad) {
        return bundleArchivoDePropiedades.getString(propiedad);
    }
}
