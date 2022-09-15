package app;

import static spark.Spark.*;
import service.ClienteService;


public class Aplicacao {
	
	private static ClienteService ClienteService = new ClienteService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/cliente/insert", (request, response) -> ClienteService.insert(request, response));

        get("/cliente/:id", (request, response) -> ClienteService.get(request, response));
        
        get("/cliente/list/:orderby", (request, response) -> ClienteService.getAll(request, response));

        get("/cliente/update/:id", (request, response) -> ClienteService.getToUpdate(request, response));
        
        post("/cliente/update/:id", (request, response) -> ClienteService.update(request, response));
           
        get("/cliente/delete/:id", (request, response) -> ClienteService.delete(request, response));

             
    }
}