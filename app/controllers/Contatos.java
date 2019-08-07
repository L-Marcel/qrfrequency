package controllers;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.HtmlEmail;

import models.ContatoMail;
import play.data.validation.Error;
import play.data.validation.Valid;
import play.libs.Mail;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Contatos extends Controller {
	
	public static void form() {
		render();
	}
	
	public static void enviar(@Valid ContatoMail email) {
		email.dataEnvio = new Date();
		if(validation.hasErrors()) {
			validation.keep();
			params.flash();
			form();
		}
		HtmlEmail emailHtml = new HtmlEmail();
		try {
			emailHtml.setFrom(email.de);
			emailHtml.addTo(email.para);
			emailHtml.setSubject(email.assunto);
			emailHtml.setHtmlMsg(email.mensagem);
			Mail.send(emailHtml); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		email.save();
		detalhes(email.id);
	}
	
	public static void detalhes(Long id) {
		ContatoMail email = ContatoMail.findById(id);
		render(email);
	}
	
	public static void listar() {
		List<ContatoMail> emails = ContatoMail.findAll();
		render(emails);
	}
}