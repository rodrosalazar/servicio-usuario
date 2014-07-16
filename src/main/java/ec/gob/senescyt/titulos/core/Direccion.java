package ec.gob.senescyt.titulos.core;

public class Direccion {
    private String callePrincipal;
    private String numeroCasa;
    private String calleSecundaria;
    private String codigoProvincia;
    private String codigoCanton;
    private String codigoParroquia;

    private Direccion() {
    }

    public Direccion(String callePrincipal, String numeroCasa, String calleSecundaria, String codigoProvincia,
                     String codigoCanton, String codigoParroquia) {

        this.callePrincipal = callePrincipal;
        this.numeroCasa = numeroCasa;
        this.calleSecundaria = calleSecundaria;
        this.codigoProvincia = codigoProvincia;
        this.codigoCanton = codigoCanton;
        this.codigoParroquia = codigoParroquia;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public String getCodigoCanton() {
        return codigoCanton;
    }

    public String getCodigoParroquia() {
        return codigoParroquia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direccion direccion = (Direccion) o;

        if (callePrincipal != null ? !callePrincipal.equals(direccion.callePrincipal) : direccion.callePrincipal != null)
            return false;
        if (calleSecundaria != null ? !calleSecundaria.equals(direccion.calleSecundaria) : direccion.calleSecundaria != null)
            return false;
        if (codigoCanton != null ? !codigoCanton.equals(direccion.codigoCanton) : direccion.codigoCanton != null)
            return false;
        if (codigoParroquia != null ? !codigoParroquia.equals(direccion.codigoParroquia) : direccion.codigoParroquia != null)
            return false;
        if (codigoProvincia != null ? !codigoProvincia.equals(direccion.codigoProvincia) : direccion.codigoProvincia != null)
            return false;
        if (numeroCasa != null ? !numeroCasa.equals(direccion.numeroCasa) : direccion.numeroCasa != null) return false;

        return true;
    }
}
