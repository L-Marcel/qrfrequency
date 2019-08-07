package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Solicitacao extends Model{
	
	public boolean confirmado = false;
	public boolean enviado = false;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	public Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="atividade_id")
	public Atividade atividade;
	
}
