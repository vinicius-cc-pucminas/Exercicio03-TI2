package model;

public class Fazenda {
	private int codigo; 
	private String fazendeiro; 
	private int vacas; 
	private int galinhas; 
	private int porcos;
	
	public Fazenda() {
		this.codigo = -1;
		this.fazendeiro = "Teste Fazendeiro";
		this.vacas = 100;
		this.galinhas = 100;
		this.porcos = 100;
	}
	
	public Fazenda(int codigo, String fazendeiro, int vacas, int porcos, int galinhas) {
		this.codigo = codigo;
		this.fazendeiro = fazendeiro;
		this.vacas = vacas;
		this.galinhas = galinhas;
		this.porcos = vacas;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getFazendeiro() {
		return fazendeiro;
	}
	
	public void setFazendeiro(String fazendeiro) {
		this.fazendeiro = fazendeiro;
	}
	
	public int getVacas() {
		return vacas;
	}
	
	public void setVacas(int vacas) {
		this.vacas = vacas;
	}

	public int getGalinhas() {
		return galinhas;
	}

	public void setGalinhas(int galinhas) {
		this.galinhas = galinhas;
	}

	public int getPorcos() {
		return this.porcos;
	}

	public void setPorcos(int porcos) {
		this.porcos = porcos;
	}
	
	public String listar() {
		return "[" + this.codigo + "] Fazenda de " + this.fazendeiro + " tem " + this.vacas + " vacas, " + this.galinhas + " galinhas e " + this.porcos + " porcos";
	}
	
}