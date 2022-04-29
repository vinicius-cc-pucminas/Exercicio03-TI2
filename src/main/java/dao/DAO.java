package dao;

import java.sql.*;

public class DAO {
	protected Connection conexao; 
	
	public DAO() {
		conexao = null; 
	}
	
	public boolean conectar() {
		String driver = "org.postgresql.Driver"; 
		String server = "localhost";
		String db = "teste"; 
		int porta = 5432; 
		String url = "jdbc:postgresql://" + server + ":" + porta +"/" + db;

		String user = "usuario"; // usuário do bd
		String pass = "senha"; // senha do bd 
		
		boolean status = false; 
		
		try {
			Class.forName(driver); 
			conexao = DriverManager.getConnection(url, user, pass); 
			status = (conexao == null); 
			System.out.println("Aplicação está conectada ao banco!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Erro ao conectar com o banco (Driver não foi encontrado)" + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Erro ao conectar com o banco" + e.getMessage());
		}
		
		return status;
	}
	
	public boolean close() {
		boolean status = false; 
		
		try {
			conexao.close();
			status = true; 
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return status;
	}
	
}