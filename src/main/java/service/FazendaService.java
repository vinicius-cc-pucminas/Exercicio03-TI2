package service;

import model.Fazenda;
import dao.FazendaDAO;

import spark.*;

import java.io.File;
import java.util.Scanner;

import com.google.gson.*;

public class FazendaService {	
	private static FazendaDAO fazendaDao = new FazendaDAO();
	private String form;
	
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_FAZENDEIRO = 2;
	
	public FazendaService() {
		this.makeForm();
	}
	
	public void makeForm() {
		this.makeForm(FORM_INSERT, new Fazenda(), FORM_ORDERBY_FAZENDEIRO);
	}
	
	public void makeForm(int orderBy) {
		this.makeForm(FORM_INSERT, new Fazenda(), orderBy);
	}
	
	public void makeForm(int tipo, Fazenda fazenda, int orderBy) {
		String nomeArquivo = "index.html";
		form = ""; 
		
		try {
			Scanner entrada = new Scanner(new File(nomeArquivo));
			String teste;
			
			while (entrada.hasNext()) {
				form += ((teste = entrada.nextLine()) + "\n");
				//System.out.println(teste + "\n");
			}
			
			entrada.close(); 
		} catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umaFazenda = ""; 
		if (tipo != FORM_INSERT) {
			umaFazenda += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaFazenda += "\t\t<tr>";
			umaFazenda += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/fazenda/list/1\">Nova Fazenda</a></b></font></td>";
			umaFazenda += "\t\t</tr>";
			umaFazenda += "\t</table>";
			umaFazenda += "\t<br>";	
		}
		
		if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/fazenda/";
			String name, descricao, btnLabel;
			
			if (tipo == FORM_INSERT) {
				action += "insert"; 
				name = "Inserir Fazenda"; 
				descricao = "Fazendeiro, quantidade de vacas, galinhas e porcos"; 
				btnLabel = "Inserir";
			} else {
				action += "update/" + fazenda.getCodigo();
				name = "Atualizar Fazenda do " + fazenda.getFazendeiro();
				descricao = "Atualizar fazendeiro, quantidade de vacas, galinhas e porcos";
				btnLabel = "Atualizar"; 
			}
			
			umaFazenda += "<h2 class=\"text-center mt-4\">" + name + "</h2>";
			umaFazenda += "<p class=\"text-center mt-4 text-secondary\">" + descricao + "</p>";
			umaFazenda += "<section class=\"row mt-4 mb-5\">";
			umaFazenda += "<div class=\"col p-4 bg-light\">";
			umaFazenda += "<form action=\"" + action + "\" method=\"post\" class=\"form\" id=\"frmFazenda\">";
			umaFazenda += "<div class=\"row mb-4\">";
			umaFazenda += "<div class=\"col-6\"><label for=\"codigo\" class=\"form-label\">Código:</label><input type=\"number\" class=\"form-control\" value=\"" + fazenda.getCodigo() + "\" id=\"codigo\" name=\"codigo\" required></div>";
			umaFazenda += "</div>"; 
			umaFazenda += "<div class=\"row mb-4\">"; 
			umaFazenda += "<div class=\"col\"><label for=\"fazendeiro\" class=\"form-label\">Fazendeiro:</label><input type=\"text\" class=\"form-control\" value=\"" + fazenda.getFazendeiro() + "\" id=\"fazendeiro\" name=\"fazendeiro\" required></div>";
			umaFazenda += "</div>"; 
			umaFazenda += "<div class=\"row mb-4\">"; 
			umaFazenda += "<div class=\"col\"><label for=\"vacas\" class=\"form-label\">Quantidade Vacas:</label><input type=\"number\" class=\"form-control\" value=\"" + fazenda.getVacas() + "\" id=\"vacas\" name=\"vacas\" required></div>";
			umaFazenda += "<div class=\"col\"><label for=\"galinhas\" class=\"form-label\">Quantidade Galinhas:</label><input type=\"number\" class=\"form-control\" value=\"" + fazenda.getGalinhas() + "\" id=\"galinhas\" name=\"galinhas\" required></div>";
			umaFazenda += "<div class=\"col\"><label for=\"porcos\" class=\"form-label\">Quantidade Porcos:</label><input type=\"number\" class=\"form-control\" value=\"" + fazenda.getPorcos() + "\" id=\"porcos\" name=\"porcos\" required></div>";
			umaFazenda += "</div>"; 
			umaFazenda += "<div class=\"row\">";
			umaFazenda += "<div class=\"col d-flex justify-content-end gap-4\">"; 
			umaFazenda += "<button type=\"submit\" id=\"btnSalvar\" class=\"btn btn-outline-primary\">" + btnLabel + "</button>";
			umaFazenda += "<button type=\"reset\" id=\"btnReset\" class=\"btn btn-outline-danger\">Apagar</button>"; 
			umaFazenda += "</div></div></form></div></section>";
		
		} else if (tipo == FORM_DETAIL) {
			
			umaFazenda += "<div class=\"card text-white bg-secondary d-flex justify-content-center\" style=\"max-width: 18rem;\">";
			umaFazenda += "<div class=\"card-header\"> Fazenda de código: " + fazenda.getCodigo() + "</div>";
			umaFazenda += "<div class=\"card-body\">";
			umaFazenda += "<h5 class=\"card-title\">Fazendeiro: " + fazenda.getFazendeiro() + "</h5>";
			umaFazenda += "<p class=\"card-text\">Vacas: " + fazenda.getVacas() + "</p>";
			umaFazenda += "<p class=\"card-text\">Galinhas: " + fazenda.getGalinhas() + "</p>";
			umaFazenda += "<p class=\"card-text\">Porcos: " + fazenda.getPorcos() + "</p>";
			umaFazenda += "</div></div>";

		} else {
			
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		
		form = form.replaceFirst("<UMA-FAZENDA>", umaFazenda);
		
		String lista = "<section class=\"row\"><div class=\"col\">";
		lista += "<table class=\"table table-hover\"><thead><tr>";
		lista += "<th scope=\"col\"><a href=\"/fazenda/list/" + FORM_ORDERBY_ID + "\">Código</a></th>";
		lista += "<th scope=\"col\"><a href=\"/fazenda/list/" + FORM_ORDERBY_FAZENDEIRO + "\">Fazendeiro</a></th>";
		lista += "<th scope=\"col\">Vacas</th>";
		lista += "<th scope=\"col\">Galinhas</th>";
		lista += "<th scope=\"col\">Porcos</th>";
		lista += "<th scope=\"col\">Ações</th>";
		lista += "</tr></thead>"; 
		lista += "<tbody id=\"tbody\">"; 
		
		Fazenda[] fazendas;
		
		if (orderBy == FORM_ORDERBY_ID) fazendas = fazendaDao.getFazendas("codigo");
		else if (orderBy == FORM_ORDERBY_FAZENDEIRO) fazendas = fazendaDao.getFazendas("fazendeiro");
		else fazendas = fazendaDao.getFazendas();		

		for (Fazenda f : fazendas) {
			lista += "<tr>";
			lista += "<td>" + f.getCodigo() + "</td>";
			lista += "<td>" + f.getFazendeiro() + "</td>";
			lista += "<td>" + f.getVacas() + "</td>";
			lista += "<td>" + f.getGalinhas() + "</td>";
			lista += "<td>" + f.getPorcos() + "</td>";
			lista += "<td class=\"d-flex gap-4\"><a href=\"/fazenda/" + f.getCodigo() + "\">Visualizar</a><a href=\"/fazenda/update/" + f.getCodigo() + "\">Atualizar</a>";
			lista += "<button class=\"btn btn-outline-danger\" onclick=\"confirmarDeleteFazenda('" + f.getCodigo() + "');\">Deletar</button></td>";
			lista += "</tr>"; 
		}
		
		lista += "</tbody></table></div></section>";
		
		form = form.replaceFirst("<LISTAR-FAZENDA>", lista);
		
		//System.out.println(form);
	}
	
	public Object add(Request req, Response res) {	
		int codigo = Integer.parseInt(req.queryParams("codigo"));
		String fazendeiro = req.queryParams("fazendeiro");
		int vacas = Integer.parseInt(req.queryParams("vacas"));
		int galinhas = Integer.parseInt(req.queryParams("galinhas"));
		int porcos = Integer.parseInt(req.queryParams("porcos"));

		String resp;
		Fazenda fazenda = new Fazenda(codigo, fazendeiro, vacas, porcos, galinhas);
		
		if (fazendaDao.inserir(fazenda)) {
			resp = "Fazenda adicionada!"; 
			res.status(200);
		} else {
			resp = "Não foi possível adicionar a fazenda"; 
			res.status(404);
		}
		
		this.makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
	
	public Object get(Request req, Response res) {
		int codigo = Integer.parseInt(req.params(":codigo"));
		Fazenda fazenda = null;
		
		String resp = "teste";
			
		fazenda = fazendaDao.getFazenda(codigo);
		
		if (fazenda != null) {
			makeForm(FORM_DETAIL, fazenda, FORM_ORDERBY_FAZENDEIRO);
			res.status(200); 
		} else {
			resp = "Fazenda não encontrada";
			res.status(404);
			
			this.makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
		}
		
		return form;
	}
	
	public Object getToUpdate(Request req, Response res) {
		int codigo = Integer.parseInt(req.params(":codigo"));
		Fazenda fazenda = null;
		
		String resp;
	
		fazenda = fazendaDao.getFazenda(codigo);
		
		if (fazenda != null) {
			makeForm(FORM_UPDATE, fazenda, FORM_ORDERBY_FAZENDEIRO);
			res.status(200); 
		} else {
			resp = "Fazenda não encontrada";
			res.status(404);
			
			this.makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
		}
		
		return form;
	}
	
	public Object update(Request req, Response res) {
		int codigo = Integer.parseInt(req.params(":codigo"));
		String resp;

		Fazenda fazenda = null;		
					
		if ((fazenda = fazendaDao.getFazenda(codigo)) != null) {
			fazenda.setFazendeiro(req.queryParams("fazendeiro"));
			fazenda.setGalinhas(Integer.parseInt(req.queryParams("galinhas")));
			fazenda.setPorcos(Integer.parseInt(req.queryParams("porcos")));
			fazenda.setVacas(Integer.parseInt(req.queryParams("vacas")));
			
			
			if (fazendaDao.atualizar(fazenda)) {
				res.status(200);
				
				resp = "Fazenda de código " + codigo + ", atualizada com sucesso!"; 
			} else {
				res.status(502);
				
				resp = "Não foi possível atualizar a fazenda";
			}
		} else {
			res.status(404);
			
			resp = "Não foi possível atualizar a fazenda";
		}
		
		this.makeForm(); 
		
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
	}
	
	public Object remove(Request req, Response res) {
		int codigo = Integer.parseInt(req.params(":codigo"));
		String resp;
	
		if (fazendaDao.excluir(codigo)) {
			res.status(200); 
			resp = "Fazenda deletada com sucesso!";
		} else {
			res.status(500); 
			
			resp = "Não foi possível deletar a fazenda";
		}
		
		this.makeForm(); 
		
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"teste\">"); 
		
	}
	
	public Object getAll(Request req, Response res) {
		int orderBy = Integer.parseInt(req.params(":orderby"));
		makeForm(orderBy);
		
		res.header("Content-Type", "text/html");
	    res.header("Content-Encoding", "UTF-8");
		return form;
	}
}
