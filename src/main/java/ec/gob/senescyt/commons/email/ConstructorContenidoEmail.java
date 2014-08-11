package ec.gob.senescyt.commons.email;

public class ConstructorContenidoEmail {

    public String construirEmailNotificacionUsuarioCreado(String nombreDestinatario, String nombreUsuario, String urlConToken) {
        StringBuilder contenidoNotificacionUsuarioCreado = new StringBuilder();
        contenidoNotificacionUsuarioCreado.append("<div align='justify'>");
        contenidoNotificacionUsuarioCreado.append("Estimado/a ").append(nombreDestinatario).append("<br/>");
        contenidoNotificacionUsuarioCreado.append("El usuario ").append(nombreUsuario)
                .append(" ha sido creado en el Sistema Nacional de Información de la Educación Superior del Ecuador (SNIESE).<br/>");
        contenidoNotificacionUsuarioCreado.append("Para acceder al sistema SNIESE, usted necesita crear una contraseña en la siguiente dirección web:");
        contenidoNotificacionUsuarioCreado.append("<br/>");
        contenidoNotificacionUsuarioCreado.append("<a href='").append(urlConToken).append("'>")
                .append(urlConToken)
                .append("</a>");
        contenidoNotificacionUsuarioCreado.append("</div>");

        return contenidoNotificacionUsuarioCreado.toString();
    }
}
