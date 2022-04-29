package app;

import static spark.Spark.*;
import service.FazendaService;

public class App {
	private static FazendaService fazenda = new FazendaService(); 
	
	public static void main(String[] args) {
		port(6789); 
		
		staticFiles.location("/public");
		
		post("/fazenda/insert", (request, response) -> fazenda.add(request, response));
		
		get("/fazenda/:codigo", (request, response) -> fazenda.get(request, response));
		
		get("/fazenda/list/:orderby", (request, response) -> fazenda.getAll(request, response)); 
		
		get("/fazenda/update/:codigo", (request, response) -> fazenda.getToUpdate(request, response));
		
		post("/fazenda/update/:codigo", (request, response) -> fazenda.update(request, response));
		
		get("/fazenda/delete/:codigo", (request, response) -> fazenda.remove(request, response));

	}
}
