package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Servidor extends Model {

	public int matricula;
	public String nomeServidor;
	public Blob foto;
	public String email;

}
