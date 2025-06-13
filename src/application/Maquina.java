package application;

public class Maquina {
    private String nome;
    private double energia;
    private double gasolina;
    private double emissao;

    public Maquina(String nome, double energia, double gasolina) {
        this.nome = nome;
        this.energia = energia;
        this.gasolina = gasolina;
        this.emissao = calcularEmissao();
    }

    public String getNome() { return nome; }
    public double getEnergia() { return energia; }
    public double getGasolina() { return gasolina; }
    public double getEmissao() { return emissao; }

    private double calcularEmissao() {
        double emissaoGasolina = gasolina * 2.31; // kg CO₂ por litro
        double emissaoEnergia = energia * 0.084;  // kg CO₂ por kWh (média Brasil)
        return emissaoGasolina + emissaoEnergia;
    }
}
