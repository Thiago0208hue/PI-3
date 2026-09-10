package controllers;

import java.util.List;

import models.Seguro;
import models.Segurado;
import models.Status;
import play.mvc.Controller;

public class Seguros extends Controller {

	public static void form() {
		Seguro s = new Seguro();
		List<Segurado> segurados = Segurado.findAll();
		render(s, segurados);
	}

	public static void editar(Long id) {
		Seguro s = Seguro.findById(id);
		List<Segurado> segurados = Segurado.findAll();
		renderTemplate("Seguros/form.html", s, segurados);
	}

	public static void listar(String termo) {
		List<Seguro> seguros = null;
		if (termo == null) {
			seguros = Seguro.find("status != ?1", Status.INATIVO).fetch();
		} else {
			seguros = Seguro.find(
					"status != ?1 and (lower(segurado.nome) like ?2 or lower(placa) like ?2 or lower(modelo) like ?2)",
					Status.INATIVO, "%" + termo.toLowerCase() + "%").fetch();
		}
		render(seguros, termo);
	}

	public static void detalhar(Long id) {
		Seguro seguro = Seguro.findById(id);
		render(seguro);
	}

	public static void salvar(Seguro seguro) {
		seguro.placa = seguro.placa.toUpperCase();
		seguro.modelo = seguro.modelo.toUpperCase();
		seguro.save();
		flash.success("Apólice cadastrada com sucesso!");
		listar(null);
	}

	public static void remover(Long id) {
		Seguro seguro = Seguro.findById(id);
		seguro.status = Status.INATIVO;
		seguro.save();

		flash.success("Apólice removida com sucesso!");
		listar(null);
	}

}