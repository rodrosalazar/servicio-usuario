package ec.gob.senescyt.usuario.core;

public class CedulaInfo {

    private String nombre;
    private String direccion;
    private String provincia;
    private String canton;
    private String parroquia;

    private CedulaInfo() {}

    public CedulaInfo(String nombre, String direccion, String provincia, String canton, String parroquia) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.provincia = provincia;
        this.canton = canton;
        this.parroquia = parroquia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCanton() {
        return canton;
    }

    public String getParroquia() {
        return parroquia;
    }
}