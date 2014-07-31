package ec.gob.senescyt.commons.email;

public class ConstructorDeContenidoDeEmail {

    public static String construirEmailNotificacionUsuarioCreado(String nombreDestinatario, String nombreUsuario, String idToken) {

        StringBuilder contenidoNotificacionUsuarioCreado = new StringBuilder();
        contenidoNotificacionUsuarioCreado.append("<div align='justify'>");
        contenidoNotificacionUsuarioCreado.append("Estimado/a ").append(nombreDestinatario).append("<br/>");
        contenidoNotificacionUsuarioCreado.append("El usuario ").append(nombreUsuario)
                .append(" ha sido creado en el Sistema Nacional de Educación Superior (SNIESE).<br/>");
        contenidoNotificacionUsuarioCreado.append("Para acceder al sistema SNIESE, usted necesita crear una contraseña en la siguiente dirección web:");
        contenidoNotificacionUsuarioCreado.append("<br/>");
        contenidoNotificacionUsuarioCreado.append("<a href='http://sniese.gob.ec/usuarios/contrasenia/").append(idToken).append("'>")
                .append("http://sniese.gob.ec/usuarios/contrasenia/").append(idToken)
                .append("</a>");
        contenidoNotificacionUsuarioCreado.append("</div>");

        return contenidoNotificacionUsuarioCreado.toString();
    }
}
