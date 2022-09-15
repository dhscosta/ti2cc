package dao;

import model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO extends DAO {	
	public ClienteDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Cliente cliente) {
		boolean status = false;
		try {
			String sql = "INSERT INTO cliente (email, premium , nome) "
		               + "VALUES ('" + cliente.getemail() + "', "
		               + cliente.getvip() + ", " + cliente.getnome();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Cliente get(int id) {
		Cliente cliente = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 cliente = new Cliente(rs.getInt("id"), rs.getString("email"), rs.getBoolean("vip"), rs.getString("nome"))  ;
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return cliente;
	}
	
	
	public List<Cliente> get() {
		return get("");
	}

	
	public List<Cliente> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Cliente> getOrderByemail() {
		return get("email");		
	}
	
	
	public List<Cliente> getOrderBynome() {
		return get("nome");		
	}
	
	
	private List<Cliente> get(String orderBy) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Cliente p = new Cliente(rs.getInt("id"), rs.getString("email"), rs.getBoolean("vip"), rs.getString("nome"))  ;
	            clientes.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return clientes;
	}
	
	
	public boolean update(Cliente cliente) {
		boolean status = false;
		try {  
			String sql = "UPDATE cliente SET vip = '" + cliente.getvip() + "', "
					   + "nome = " + cliente.getnome() + ", " 
					   + "email = " + cliente.getemail() + ","
					   + "datafabricacao = ?, " 
					   + "datavalidade = ? WHERE id = " + cliente.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM cliente WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}