/*

*
* Copyright (C) 2013 Libreria para Cliente Consulta Títulos development team.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
******************************************************
*  SUBSECRETARIA DE TECNOLOGIAS DE LA INFORMACION
*   DIRECCION DE INTEROPERABILIDAD GUBERNAMENTAL
*
* FECHA CREACION : 02 -02-2013
* AUTHOR: DIG DIRECCION DE INTEROPERABILIDAD GUBERNAMENTAL
* PROGRAMADOR:LUIGGI ANDRADE 
* CONCEPTO   :  Consulta De Titulos de Ciudadanos
* Utilización del servicio web de la senascyt  
*
*
* MODIFICACION (Fecha, Autor y la descripción de la modificación)
*
*
*******************************************************

 */
package ec.gob.senescyt.usuario.client;


import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * @author landrade
 */
@SuppressWarnings({
        "PMD.AvoidCatchingGenericException",
        "PMD.AvoidPrintStackTrace",
})
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {

    public static final String WSS = "wss";
    public static final String WSU = "wsu";

    DatosHeader dhLista = new DatosHeader();

    public HeaderHandler(DatosHeader datosHeader) {

        dhLista.setDigest(datosHeader.getDigest());
        dhLista.setNonce(datosHeader.getNonce());
        dhLista.setFecha(datosHeader.getFecha());
        dhLista.setFechaf(datosHeader.getFechaf());
        dhLista.setUsuario(datosHeader.getUsuario());

    }


    public boolean handleMessage(SOAPMessageContext smc) {

        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);


        if (outboundProperty.booleanValue()) {

            SOAPMessage message = smc.getMessage();

            try {

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                if (envelope.getHeader() != null) {
                    envelope.getHeader().detachNode();
                }
                SOAPHeader header = envelope.addHeader();

                // get SOAP envelope from SOAP message

                SOAPElement security =
                        header.addChildElement("Security", WSS, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");


                SOAPElement timeStamp = security
                        .addChildElement(
                                "Timestamp",
                                WSU,
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");


                SOAPElement createdTime =
                        timeStamp.addChildElement("Created", WSU);
                createdTime.addTextNode(dhLista.getFecha());

                SOAPElement expires =
                        timeStamp.addChildElement("Expires", WSU);
                expires.addTextNode(dhLista.getFechaf());


                SOAPElement usernameToken =
                        security.addChildElement("UsernameToken", WSS);
                //usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

                SOAPElement username =
                        usernameToken.addChildElement("Username", WSS);
                username.addTextNode(dhLista.getUsuario());

                SOAPElement password =
                        usernameToken.addChildElement("Password", WSS);
                password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                password.addTextNode(dhLista.getDigest());
                //password.addTextNode("Qro+U/5Swf50Pt04i4WS/PsbljY=");

                SOAPElement nonce =
                        usernameToken.addChildElement("Nonce", WSS);
                nonce.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
                nonce.addTextNode(dhLista.getNonce());
                //nonce.addTextNode("He+y+afyHy7lWugWrn6LBQ==");               

                SOAPElement created = usernameToken.addChildElement("Created", WSU, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                created.addTextNode(dhLista.getFecha());
                //created.addTextNode("2012-12-07T13:40:21Z");

                //Print out the outbound SOAP message to System.out
                message.writeTo(System.out);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {

                //This handler does nothing with the response from the Web Service so
                //we just print out the SOAP message.

                SOAPMessage message = smc.getMessage();
                message.writeTo(System.out);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return outboundProperty;
    }

    public Set getHeaders() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    public boolean handleFault(SOAPMessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return false;
    }

    public void close(MessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}