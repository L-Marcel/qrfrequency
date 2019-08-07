package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Sala;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Salas extends Controller {

	public static void formSala() {
		render();
	}

	public static void salvar(Sala sala) {

		Sala salaComMesmoNum = null;

		if (sala.id == null) {
			salaComMesmoNum = Sala.find("numSala = ?", sala.numSala).first();
		} else {
			salaComMesmoNum = Sala.find("id <> ? and numSala = ?", sala.id, sala.numSala).first();
		}

		if (salaComMesmoNum == null) {
			
			if(validation.hasErrors()) {
				validation.keep();
				params.flash();
				formSala();
			} 
			
			SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
			String hini = params.get("sala.hrAbertura");
			String hfim = params.get("sala.hrFechamento");
			Date inicio = null;
			Date fim = null;

			try {
				inicio = formato.parse(hini);
				fim = formato.parse(hfim);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			sala.hrAbertura = inicio;
			sala.hrFechamento = fim;
			sala.save();
			flash.success("Sala cadastrada com sucesso!");
			listar();
		} else {
			flash.error("Já existe uma sala com o mesmo número informado.");
			formSala();
		}
	}

	public static void listar() {
		List<Sala> salas = Sala.findAll();
		render(salas);
	}
	
	public static void listarJSON() {
		List<Sala> salas = Sala.findAll();
		renderJSON(salas);
	}

	public static void deletar(Long id) {
		Sala sala = Sala.findById(id);
		sala.delete();
		listar();
	}

	public static void editar(Long id) {
		Sala sala = Sala.findById(id);
		renderTemplate("Salas/formSala.html", sala);
	}

}
