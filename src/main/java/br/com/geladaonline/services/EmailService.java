package br.com.geladaonline.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.MultiPart;

import br.com.geladaonline.model.rest.Email;

@Path("/email")
public class EmailService {

	@POST
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.TEXT_HTML })
	public void enviarEmail(@HeaderParam("To") String para, @HeaderParam("Cc") String comCopia,
			@HeaderParam("Bcc") String comCopiaOculta, @HeaderParam("Subject") String assunto,
			String mensagem, @Context HttpHeaders httpHeaders) {
		
		Email email = new Email()
				.withAssunto(assunto)
				.withComCopia(comCopiaOculta)
				.withComCopiaOculta(comCopiaOculta)
				.withDestinatario(para)
				.withMensagem(mensagem, httpHeaders.getMediaType().toString());
		
		email.enviar();
	}
	
	
	//Metodo não atingido pelo postman
	@POST
	@Consumes("multipart/mixed")
	public void enviarEmailAnexos(@HeaderParam("To") String para, @HeaderParam("Cc") String comCopia,
			@HeaderParam("Bcc") String comCopiaOculta, @HeaderParam("Subject") String assunto,
			MultiPart multiPart) {
		
		Email email = new Email()
				.withAssunto(assunto)
				.withComCopia(comCopiaOculta)
				.withComCopiaOculta(comCopiaOculta)
				.withDestinatario(para);
		
		email.enviar();
	}


}
