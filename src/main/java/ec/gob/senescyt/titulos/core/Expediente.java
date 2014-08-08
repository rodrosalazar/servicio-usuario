package ec.gob.senescyt.titulos.core;

public class Expediente {
    private long id;
    private PortadorTitulo portadorTitulo;
    private InformacionAcademica informacionAcademica;

    private Expediente() {
    }

    public Expediente(PortadorTitulo portadorTitulo, InformacionAcademica informacionAcademica) {

        this.portadorTitulo = portadorTitulo;
        this.informacionAcademica = informacionAcademica;
    }

    public PortadorTitulo getPortadorTitulo() {
        return portadorTitulo;
    }

    public InformacionAcademica getInformacionAcademica() {
        return informacionAcademica;
    }

    public long getId() {
        return id;
    }
}
