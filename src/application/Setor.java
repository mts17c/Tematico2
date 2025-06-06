package application;

public class Setor {
	private String nome;
	private int cod;
	private int energia;
	private int carb;
	private int rank;
	
	public Setor() {
		this.nome="";
		this.cod=0;
		this.carb=0;
		this.energia=0;
		this.rank=0;
	}
	
	public Setor(String nome, int cod, int energia, int carb, int rank) {
		this.nome=nome;
		this.cod=0;
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

	public int getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = 0;
	}

	public int getEnergia() {
		return energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}

	public int getCarb() {
		return carb;
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
