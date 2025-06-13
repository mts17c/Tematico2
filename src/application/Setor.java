package application;

public class Setor {
	private String nome;
	private double energia;
	private double carb;
	private int rank;
	
	public Setor() {
		this.nome="";
		this.carb=0;
		this.energia=0;
		this.rank=0;
	}
	
	public Setor(String nome, double energia, double carb, int rank) {
		this.nome=nome;
		this.carb=carb;
		this.energia=energia;
		this.rank=rank;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEnergia() {
		return (int) energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public int getCarb() {
		return (int) carb;
	}

	public void setCarb(int carb) {
		this.carb = carb;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
}
