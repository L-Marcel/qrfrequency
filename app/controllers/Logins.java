package controllers;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import models.Administrador;
import models.DadosSUAP;
import models.Usuario;
import play.libs.WS;
import play.mvc.Before;
import play.mvc.Controller;
import com.google.gson.Gson;

public class Logins extends Controller {

	// mod dia 12/10/2018 para quando entrar na página de login e estiver logado
	@Before(unless = "logout")
	static void checarAutenticacao() {
		if (session.get("usuarioEmail") != null) {
			Application.index();
		}
	}

	public static void login() {
		render();
	}

	public static void autenticarSuapApp(String matricula, String senha) {
		Administrador adm1 = Administrador.find("matricula = ? and senha = ?", matricula, senha).first();
		if (adm1 != null) {
			System.out.println("entrou no if");
			session.put("matriculaAdmin", adm1.matricula);
			Application.index();

		} else {

			WS.HttpResponse resposta;

			String urlToken = "https://suap.ifrn.edu.br/api/v2/autenticacao/token/";
			String urlDados = "https://suap.ifrn.edu.br/api/v2/minhas-informacoes/meus-dados/";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("username", matricula);
			parametros.put("password", senha);

			resposta = WS.url(urlToken).params(parametros).post();

			if (resposta.success()) {

				String token = resposta.getJson().getAsJsonObject().get("token").getAsString();
				Map<String, String> header = new HashMap<String, String>();
				header.put("X-CSRFToken", token);
				header.put("Authorization", "JWT " + token);

				resposta = WS.url(urlDados).headers(header).get();

				DadosSUAP dadosSUAP = new Gson().fromJson(resposta.getString(), DadosSUAP.class);

				Usuario usuario = Usuario.find("matricula = ?", dadosSUAP.matricula).first();

				if (usuario == null) {
					usuario = new Usuario();
					usuario.nome = dadosSUAP.nome_usual;
					usuario.matricula = dadosSUAP.matricula;
					usuario.tipoVinculo = dadosSUAP.tipo_vinculo;
					usuario.url_foto_75x100 = "http://suap.ifrn.edu.br" + dadosSUAP.url_foto_75x100;
					usuario.email = dadosSUAP.email;
					usuario.save();
				}
				
				
				Gson gson = new Gson();
				String json = gson.toJson(usuario);
				renderJSON(json);

			} else {
				renderText("0");
			}
		}
	}
	
	public static void autenticarSuap(String matricula, String senha) {
		Administrador adm1 = Administrador.find("matricula = ? and senha = ?", matricula, senha).first();
		if (adm1 != null) {
			System.out.println("entrou no if");
			session.put("matriculaAdmin", adm1.matricula);
			Application.index();

		} else {

			WS.HttpResponse resposta;

			String urlToken = "https://suap.ifrn.edu.br/api/v2/autenticacao/token/";
			String urlDados = "https://suap.ifrn.edu.br/api/v2/minhas-informacoes/meus-dados/";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("username", matricula);
			parametros.put("password", senha);

			resposta = WS.url(urlToken).params(parametros).post();

			if (resposta.success()) {

				String token = resposta.getJson().getAsJsonObject().get("token").getAsString();
				Map<String, String> header = new HashMap<String, String>();
				header.put("X-CSRFToken", token);
				header.put("Authorization", "JWT " + token);

				resposta = WS.url(urlDados).headers(header).get();

				DadosSUAP dadosSUAP = new Gson().fromJson(resposta.getString(), DadosSUAP.class);

				Usuario usuario = Usuario.find("matricula = ?", dadosSUAP.matricula).first();

				if (usuario == null) {
					usuario = new Usuario();
					usuario.nome = dadosSUAP.nome_usual;
					usuario.matricula = dadosSUAP.matricula;
					usuario.tipoVinculo = dadosSUAP.tipo_vinculo;
					usuario.url_foto_75x100 = "http://suap.ifrn.edu.br" + dadosSUAP.url_foto_75x100;
					usuario.email = dadosSUAP.email;
					usuario.save();
				}

				session.put("usuarioEmail", usuario.email);
				session.put("usuarioNome", usuario.nome);
				session.put("usuarioFoto", usuario.url_foto_75x100);
				session.put("idUsuario", usuario.id);
				Application.indexUsuario();// Página inicial
				return ;

			} else {
				flash.error("Email ou senha inválidos");
				login();// Redireciona para o form de login
			}
		}
	}

	public static void logout() {
		session.clear();
		login();
	}

}
