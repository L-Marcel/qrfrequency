package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.google.gson.annotations.Expose;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
public class Usuario extends GenericModel {
	
	@Expose
	@Id
    @GeneratedValue
    public Long id;

	@Expose
	public String nome;
	@Expose
	public String matricula;
	@Expose
	public String tipoVinculo;
	@Expose
	public String url_foto_75x100;
	@Expose
	public String email;

	@OneToMany(mappedBy="usuario")
	public List<Frequencia> frequencias;
	
	@ManyToMany
	@JoinTable(name="usuarios_atividades")
	public List<Atividade> atividades;
	
	@OneToMany(mappedBy="usuario")
	public List<Solicitacao> solicitacoes;
	
	@OneToMany(mappedBy="usuario")
	public List<Falta> faltas;
	
}
