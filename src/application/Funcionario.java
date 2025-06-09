package application;

public class Funcionario {
    private String nome;
    private String setor;

    public Funcionario(String nome, String setor) {
        this.nome = nome;
        this.setor = setor;
    }

    public String getNome() {
        return nome;
    }

    public String getSetor() {
        return setor;
    }

	public void setSetor(String setor) {
		this.setor = setor;
	}

    

}
