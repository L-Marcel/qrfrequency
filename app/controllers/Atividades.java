package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import QRCode.CreateQR;
import models.Atividade;
import models.Solicitacao;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Atividades extends Controller {

	public static void formAtividade() {
		render();
	}

	public static void academia() {
		render();
	}

	//Lembrar de recolocar o @valid antes do "Atividade"
	public static void salvar(Atividade atividade) {
		
		
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		String hini = params.get("atividade.hrAbertura");
		String hfim = params.get("atividade.hrFechamento");
		Date inicio = null;
		Date fim = null;
		LocalTime localTime = new LocalTime();
		System.out.println(localTime.toString());
		try {
			inicio = formato.parse(hini);
			fim = formato.parse(hfim);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		atividade.hrAbertura = inicio;
		atividade.hrFechamento = fim;
		
		boolean novaAtividade = atividade.id == null;
		atividade.save();
		if (novaAtividade) {
			String conteudoQRCode = "" + atividade.id;
			atividade.qrCode = CreateQR.generateQrCodeBlob(conteudoQRCode);
			atividade.save();
		}
		detalhes(atividade.id);
	}

	public static void listar() {
		List<Atividade> atividades = Atividade.findAll();
		render(atividades);
	}

	public static void listarAtvDisponiveis() {
		//está pegando o usuário
		Usuario user = Usuario.findById(Long.parseLong(session.get("idUsuario")));
		ArrayList<Atividade> atvs = new ArrayList();
		List<Solicitacao> solicitacoes = Solicitacao.find("usuario = ?", user).fetch();
		
		for (Solicitacao solicitacao : solicitacoes) {
			//está pegando a atividade e salvando
			atvs.add(solicitacao.atividade); 
		}
	
		List<Atividade> atividades = Atividade.findAll();
		for (Atividade atv : atvs) {
			atividades.remove(atv);
		}
	
		render(atividades);
	}

	public static void listarJson() {
		List<Atividade> atividades = Atividade.findAll();
		
		Gson gson = new GsonBuilder().create();
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String result = gson.toJson(atividades);
		renderJSON(result);
	}

	public static void deletar(Long id) {
		Atividade atividade = Atividade.findById(id);
		atividade.delete();
		listar();
	}

	public static void editar(Long id) {
		Atividade atividade = Atividade.findById(id);
		renderTemplate("Atividades/formAtividade.html", atividade);
	}

	public static void detalhes(Long id) {
		Atividade atividade = Atividade.findById(id);
		render(atividade);
	}

	public static void qrCodeAtividade(Long id) {
		Atividade atividade = Atividade.findById(id);
		notFoundIfNull(atividade);
		response.setContentTypeIfNotSet(atividade.qrCode.type());
		renderBinary(atividade.qrCode.get());
	}

	public static void gerarQRCodeAtividade(Long id) {
		Atividade atividade = Atividade.findById(id);
		String conteudoQRCode = "" + atividade.id;
		atividade.qrCode = CreateQR.generateQrCodeBlob(conteudoQRCode);
		atividade.save();
		renderTemplate("Atividades/detalhes.html", atividade);
	}
}
