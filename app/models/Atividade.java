package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

import QRCode.CreateQR;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
public class Atividade extends GenericModel{
	
	@Expose
	@Id
    @GeneratedValue
    public Long id;
	
	@Expose
	@Required
	public String nome;
	
	@Expose
	@Required
	public String descricao;
		
	public Blob qrCode;
	
	@Expose
	@Required
	@Temporal(TemporalType.TIME)
	public Date hrAbertura, hrFechamento;
	
	@Expose
	@OneToMany(mappedBy="atividade")
	public List<Frequencia> frequencias;
	
	@ManyToMany(mappedBy="atividades")
	public List<Usuario> usuarios;
	
	@OneToMany(mappedBy="atividade")
	public List<Solicitacao> solicitacoes;
	
	@OneToMany(mappedBy="atividade")
	public List<Falta> faltas;
	
}
