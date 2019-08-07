package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

import enums.TipoFreq;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
public class Frequencia extends GenericModel {
	
	@Expose
	@Id
    @GeneratedValue
    public Long id;

	@Expose
	@Temporal(TemporalType.TIME)
	public Date hora;
	
	@Expose
	@Temporal(TemporalType.DATE)
	public Date data;
	
	@Expose
	@Enumerated(EnumType.STRING)
	public TipoFreq tipoFreq;
	
	@Expose
	@ManyToOne
	@JoinColumn(name="usuario_id")
	public Usuario usuario;	
	
	@ManyToOne
	@JoinColumn(name="atividade_id")
	public Atividade atividade;	
	
}
