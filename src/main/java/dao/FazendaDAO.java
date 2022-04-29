package dao;

import java.sql.*;

import model.Fazenda;

public class FazendaDAO extends DAO {
	
	public FazendaDAO() {
		super();
		conectar();
	}
	
	public void finalizar() {
		close();
	}
	
	public boolean inserir(Fazenda fazenda) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO fazenda (codigo, fazendeiro, vacas, galinhas, porcos) "
					       + "VALUES ("+fazenda.getCodigo()+ ", '" + fazenda.getFazendeiro() + "', "  
					       + fazenda.getVacas() + ", " + fazenda.getGalinhas() + " , " + fazenda.getPorcos() + ");");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean atualizar(Fazenda fazenda) {
		boolean status = false;
		
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE fazenda SET fazendeiro = '" + fazenda.getFazendeiro() + "', vacas = "  
				       + fazenda.getVacas() + ", porcos = " + fazenda.getPorcos() + ", galinhas = " + fazenda.getGalinhas() + " WHERE codigo = " + fazenda.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean excluir(int codigo) {
		boolean status = false;
		
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM fazenda WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean checkFazenda(int codigo) {
		boolean exists = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM fazenda WHERE codigo = " + codigo);		
	         if(rs.next() && rs.getRow() > 0){
	             exists = true;
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return exists; 
	}
	
	public Fazenda getFazenda(int codigo) {
		Fazenda fazenda = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM fazenda WHERE codigo = " + codigo);		
	         if(rs.next() && rs.getRow() > 0){
	        	 fazenda = new Fazenda(rs.getInt("codigo"), rs.getString("fazendeiro"), rs.getInt("vacas"),  rs.getInt("porcos"),  rs.getInt("galinhas"));
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return fazenda; 
	}
	
	public Fazenda[] getFazendas(String orderBy) {
		Fazenda[] fazendas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM fazenda order by " + orderBy);		
	         if(rs.next()){
	             rs.last();
	             fazendas = new Fazenda[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                fazendas[i] = new Fazenda(rs.getInt("codigo"), rs.getString("fazendeiro"), rs.getInt("vacas"),  rs.getInt("porcos"),  rs.getInt("galinhas"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return fazendas;
	}
	
	public Fazenda[] getFazendas() {
		Fazenda[] fazendas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM fazenda");		
	         if(rs.next()){
	             rs.last();
	             fazendas = new Fazenda[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                fazendas[i] = new Fazenda(rs.getInt("codigo"), rs.getString("fazendeiro"), rs.getInt("vacas"),  rs.getInt("porcos"),  rs.getInt("galinhas"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return fazendas;
	}
	
	public int getMaxId() {
		int id = 0;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT codigo FROM fazenda ORDER BY codigo LIMIT 1");
			
			if (rs.next() && rs.getRow() > 0) {
				id = rs.getInt("codigo");
			}
			
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return id;
	}
}
