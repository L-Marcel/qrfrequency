package controllers;

import java.util.List;

import models.*;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Servidores extends Controller {

	public static void formServidor() {
		render();
	}

	public static void salvar(Servidor servidor) {
		servidor.save();
		flash.success("Servidor cadastrado com sucesso!");
		listar();
	}

	public static void listar() {
		List<Servidor> servidores = Servidor.findAll();
		render(servidores);
	}
	
	public static void listarJSON() {
		List<Servidor> servidores = Servidor.findAll();
		renderJSON(servidores);
	}

	public static void deletar(Long id) {
		Servidor servidor = Servidor.findById(id);
		servidor.delete();
		listar();
	}

	public static void editar(Long id) {
		Servidor servidor = Servidor.findById(id);
		renderTemplate("Servidores/formServidor.html", servidor);
	}
	
	public  static  void  fotoServidor(Long  id) {
	    Servidor servidor = Servidor.findById(id);
	    notFoundIfNull(servidor);
	    response.setContentTypeIfNotSet(servidor.foto.type());
	    renderBinary(servidor.foto.get());
	}

}
