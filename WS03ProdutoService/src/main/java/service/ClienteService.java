package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ClienteDAO;
import model.Cliente;
import spark.Request;
import spark.Response;


public class ClienteService {

	private ClienteDAO clienteDAO = new ClienteDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_email = 2;
	private final int FORM_ORDERBY_nome = 3;
	
	
	public ClienteService
() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Cliente(), FORM_ORDERBY_email);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Cliente(), orderBy);
	}

	
	public void makeForm(int tipo, Cliente cliente, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umcliente = "";
		if(tipo != FORM_INSERT) {
			umcliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/cliente/list/1\">Novo cliente</a></b></font></td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t</table>";
			umcliente += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/cliente/";
			String name, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir cliente";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + cliente.getID();
				name = "Atualizar cliente (ID " + cliente.getID() + ")";
				buttonLabel = "Atualizar";
			}
			umcliente += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umcliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ cliente.getemail() +"\"></td>";
			umcliente += "\t\t\t<td>vip: <input class=\"input--register\" type=\"text\" name=\"vip\" value=\""+ cliente.getvip() +"\"></td>";
			umcliente += "\t\t\t<td>nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ cliente.getnome() +"\"></td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t</table>";
			umcliente += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umcliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar cliente (ID " + cliente.getID() + ")</b></font></td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td>&nbsp;Descrição: "+ cliente.getemail() +"</td>";
			umcliente += "\t\t\t<td>vip: "+ cliente.getvip() +"</td>";
			umcliente += "\t\t\t<td>nome: "+ cliente.getnome() +"</td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t\t<tr>";
			umcliente += "\t\t\t<td>&nbsp;</td>";
			umcliente += "\t\t</tr>";
			umcliente += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-cliente>", umcliente);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de clientes</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_email + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_nome + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Cliente> clientes;
		if (orderBy == FORM_ORDERBY_ID) {                 	clientes = clienteDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_email) {		clientes = clienteDAO.getOrderByemail();
		} else if (orderBy == FORM_ORDERBY_nome) {			clientes = clienteDAO.getOrderBynome();
		} else {											clientes = clienteDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Cliente p : clientes) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getemail() + "</td>\n" +
            		  "\t<td>" + p.getvip() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/cliente/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/cliente/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletecliente('" + p.getID() + "', '" + p.getemail() + "', '" + p.getvip() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-cliente>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String email = request.queryParams("email");
		Boolean vip = Boolean.parseBoolean(request.queryParams("vip"));
		String nome = (request.queryParams("nome"));
		
		String resp = "";
		
		Cliente cliente = new Cliente(-1, email, vip, nome);
		
		if(clienteDAO.insert(cliente) == true) {
            resp = "cliente (" + email + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "cliente (" + email + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Cliente cliente = (Cliente) clienteDAO.get(id);
		
		if (cliente != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, cliente, FORM_ORDERBY_email);
        } else {
            response.status(404); // 404 Not found
            String resp = "cliente " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Cliente cliente = (Cliente) clienteDAO.get(id);
		
		if (cliente != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, cliente, FORM_ORDERBY_email);
        } else {
            response.status(404); // 404 Not found
            String resp = "cliente " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Cliente cliente = clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
        	cliente.setemail(request.queryParams("email"));
        	cliente.setvip(Boolean.parseBoolean(request.queryParams("vip")));
        	cliente.setnome((request.queryParams("nome")));
        	clienteDAO.update(cliente);
        	response.status(200); // success
            resp = "cliente (ID " + cliente.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "cliente (ID \" + cliente.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Cliente cliente = clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
            clienteDAO.delete(id);
            response.status(200); // success
            resp = "cliente (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "cliente (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}