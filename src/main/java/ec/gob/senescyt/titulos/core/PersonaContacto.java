package ec.gob.senescyt.titulos.core;

public class PersonaContacto {
    private String nombrePersonaContacto;
    private String emailPersonaContacto;

    private PersonaContacto() {
    }

    public PersonaContacto(String nombrePersonaContacto, String emailPersonaContacto) {
        this.nombrePersonaContacto = nombrePersonaContacto;
        this.emailPersonaContacto = emailPersonaContacto;
    }

    public String getNombrePersonaContacto() {
        return nombrePersonaContacto;
    }

    public String getEmailPersonaContacto() {
        return emailPersonaContacto;
    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonaContacto that = (PersonaContacto) o;

        if (emailPersonaContacto != null ? !emailPersonaContacto.equals(that.emailPersonaContacto) : that.emailPersonaContacto != null) {
            return false;
        }
        if (nombrePersonaContacto != null ? !nombrePersonaContacto.equals(that.nombrePersonaContacto) : that.nombrePersonaContacto != null) {
            return false;

        }

        return true;
    }

    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public int hashCode() {
        int result = nombrePersonaContacto != null ? nombrePersonaContacto.hashCode() : 0;
        result = 31 * result + (emailPersonaContacto != null ? emailPersonaContacto.hashCode() : 0);
        return result;
    }
}
