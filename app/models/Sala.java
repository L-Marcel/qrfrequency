package models;

import java.util.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Sala extends Model {

	public int numSala;

	@Temporal(TemporalType.TIME)
	public Date hrAbertura, hrFechamento;
}
