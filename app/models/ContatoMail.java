package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ContatoMail extends Model{

	@Required
	@Email
	public String de = "dayvisoneryc@gmail.com";
	
	@Required
	@Email
	public String para;
	
	@Required
	public String assunto;
	
	@Required
	@Lob
	public String mensagem;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date dataEnvio;
}
