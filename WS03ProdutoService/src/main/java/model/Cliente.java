package model;

public class Cliente {
	private int id;
	private String email;
	private boolean vip;
	private String nome;	
	
	public Cliente() {
		id = -1;
		email = "";
		vip = false;
		nome = new String();
		
	}

	public Cliente(int id, String email, boolean vip, String nome) {
		setId(id);
		setemail(email);
		setvip(vip);
		setnome(nome);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public boolean getvip() {
		return vip;
	}

	public void setvip(boolean vip) {
		this.vip = vip;
	}

	public String getnome() {
		return nome;
	}
	
	public void setnome(String nome)
	{
		this.nome = nome;
	}



	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Email: " + email + "Premium: " + vip + "Nome: " + nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Cliente) obj).getID());
	}	
}