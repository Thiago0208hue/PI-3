package controllers;

import java.util.List;

import models.Segurado;
import models.Status;
import play.mvc.Controller;

public class Segurados extends Controller {

	public static void form() {
		Segurado s = new Segurado();
		render(s);
	}

	public static void editar(Long id) {
		Segurado s = Segurado.findById(id);
		renderTemplate("Segurados/form.html", s);
	}

	public static void listar(String termo) {
		List<Segurado> segurados = null;
		if (termo == null) {
			segurados = Segurado.find("status != ?1", Status.INATIVO).fetch();
		} else {
			segurados = Segurado.find("status != ?1 and (lower(nome) like ?2 or lower(email) like ?2)", Status.INATIVO, "%"+termo.toLowerCase()+"%").fetch();
		}
		render(segurados, termo);
	}

	public static void detalhar(Long id) {
		Segurado segurado = Segurado.findById(id);
		render(segurado);
	}

	public static void salvar(Segurado segurado) {
		segurado.nome = segurado.nome.toUpperCase();
		segurado.email = segurado.email.toLowerCase();
		segurado.save();
		flash.success("Segurado cadastrado com sucesso!");
		listar(null);
	}

	public static void remover(Long id) {
		Segurado segurado = Segurado.findById(id);
		segurado.status = Status.INATIVO;
		segurado.save();

		flash.success("Segurado removido com sucesso!");
		listar(null);
	}

}