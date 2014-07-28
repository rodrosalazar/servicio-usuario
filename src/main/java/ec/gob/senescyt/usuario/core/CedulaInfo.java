package ec.gob.senescyt.usuario.core;

public class CedulaInfo {

    private String nombre;
    private String direccionCompleta;
    private String provincia;
    private String canton;
    private String parroquia;
    private String fechaNacimiento;
    private String genero;
    private String nacionalidad;

    private CedulaInfo() {}

    public CedulaInfo(String nombre, String direccionCompleta, String provincia, String canton, String parroquia, String fechaNacimiento, String genero, String nacionalidad) {
        this.nombre = nombre;
        this.direccionCompleta = direccionCompleta;
        this.provincia = provincia;
        this.canton = canton;
        this.parroquia = parroquia;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
}