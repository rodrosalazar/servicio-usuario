package ec.gob.senescyt.commons.email;

public class ConstructorDeContenidoDeEmail {

    public String construirEmailNotificacionUsuarioCreado(String nombresCompletosUsuarioNotificado, String userNameUsuarioNotificado) {

        StringBuilder contenidoNotificacionUsuarioCreado = new StringBuilder();
        contenidoNotificacionUsuarioCreado.append("<div align='justify'>");
        contenidoNotificacionUsuarioCreado.append("Estimado/a ").append(nombresCompletosUsuarioNotificado).append("<br/>");
        contenidoNotificacionUsuarioCreado.append("El usuario ").append(userNameUsuarioNotificado)
                .append(" ha sido creado en el Sistema Nacional de Educación Superior (SNIESE).<br/>");
        contenidoNotificacionUsuarioCreado.append("Para acceder al sistema SNIESE, usted necesita crear una contraseña en la siguiente dirección web:");
        contenidoNotificacionUsuarioCreado.append("<br/>");
        contenidoNotificacionUsuarioCreado.append("<a href='http://sniese.gob.ec/usuarios/contraseña'>http://sniese.gob.ec/usuarios/contraseña</a>");
        contenidoNotificacionUsuarioCreado.append("</div>");

        return contenidoNotificacionUsuarioCreado.toString();
    }
}
