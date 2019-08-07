package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Falta extends Model {

	public boolean enviado = false;
	
	@Required
	public String assunto;

	@Required
	@Lob
	public String mensagem;

	@Temporal(TemporalType.DATE)
	public Date dataEnvio;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	public Usuario usuario;	
	
	@ManyToOne
	@JoinColumn(name="atividade_id")
	public Atividade atividade;

}
