package controllers;

import java.util.List;

import models.Atividade;
import models.Solicitacao;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Solicitacoes extends Controller {

	public static void listar() {
		List<Solicitacao> solicitacoes = Solicitacao.findAll();
		render(solicitacoes);
	}

	public static void solicitarCadastro(Long idAtividade, Long idUsuario) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.atividade = Atividade.findById(idAtividade);
		solicitacao.usuario = Usuario.findById(idUsuario);
		solicitacao.enviado = true;
		solicitacao.save();
		flash.success("Solicitação enviada com sucesso!");
		Atividades.listarAtvDisponiveis();
	}

	public static void cadastrarUsuariuo(Long id) {
		Solicitacao solicitacao = Solicitacao.findById(id);
		//solicitacao.atividade.usuarios.add(solicitacao.usuario);
		Atividade atividade = Atividade.findById(solicitacao.atividade.id);
		Usuario usuario = Usuario.findById(solicitacao.usuario.id);
		atividade.usuarios.add(usuario);
		atividade.save();
		usuario.atividades.add(atividade);
		usuario.save();
		solicitacao.confirmado = true;
		solicitacao.save();
		flash.success("O usuário foi cadastrado com sucesso na atividade " + solicitacao.atividade.nome + ".");
		Solicitacoes.listar();
	}

}
