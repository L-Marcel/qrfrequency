package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Atividade;
import models.Falta;
import models.Solicitacao;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Faltas extends Controller {

	public static void formFaltas() {
		render();
	}

	public static void listar() {
		List<Falta> faltas = Falta.findAll();
		render(faltas);
	}

	public static void listarPorUsuario() throws ParseException {
		Usuario user = Usuario.findById(Long.parseLong(session.get("idUsuario")));

		List<Atividade> atividades = Atividade.findAll();
		Date dt2 = null;

		try {
			dt2 = pegarHora();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Atividade atividade : atividades) {
			ArrayList<Usuario> users = new ArrayList();
			users.addAll(atividade.usuarios);
			for (Usuario usuario : users) {
				if (usuario == user) {
					Date dt1 = atividade.hrFechamento;
					if (dt1.compareTo(dt2) < 0) {
						Falta falta2 = Falta.find("atividade = ?", atividade).first();

						Falta falta3 = new Falta();
						falta3.dataEnvio = pegarData();

						if (falta2 == null) {
							Falta falta = new Falta();
							falta.usuario = user;
							falta.atividade = atividade;
							falta.dataEnvio = pegarData();
							falta.save();
						} else {
							if (falta2.dataEnvio.compareTo(falta3.dataEnvio) > 0) {
								System.out.println(falta3.dataEnvio + " oooo");
								System.out.println(falta2.dataEnvio + " zzzzz");
								Falta falta = new Falta();
								falta.usuario = user;
								falta.atividade = atividade;
								falta.dataEnvio = pegarData();
								falta.save();
							}
						}
					}
				}
			}
		}
		List<Falta> faltas = Falta.find("usuario = ?", user).fetch();
		render(faltas);
	}

	public static Date pegarHora() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date hora = new Date();
		try {
			hora = dateFormat.parse(dateFormat.format(hora));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hora;
	}

	public static Date pegarData() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date data = new Date();
		try {
			data = dateFormat.parse(dateFormat.format(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void justificarFaltas(Long id) {
		Falta falta = Falta.findById(id);
		render(falta);
	}

	public static void Registrar(Long id) throws ParseException {
		Falta falta = Falta.findById(id);
		falta.assunto = params.get("falta.assunto");
		falta.mensagem = params.get("falta.mensagem");
		falta.enviado = true;
		falta.save();
		listarPorUsuario();
	}
}
