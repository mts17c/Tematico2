package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioMaquinasController {

    @FXML private TableView<Maquina> table;
    @FXML private TableColumn<Maquina, String> nomeCol;
    @FXML private TableColumn<Maquina, Double> energiaCol;
    @FXML private TableColumn<Maquina, Double> gasolinaCol;
    @FXML private TableColumn<Maquina, Double> emissaoCol;
    @FXML private Label campo_nome_usu;
    @FXML private Button sugestoes;
    
    private String empresa;
    private String setor;
    
    private ObservableList<Maquina> lista = FXCollections.observableArrayList();
    private final String ARQUIVO = "maquinas.txt";
  
    @FXML
    public void initialize() throws IOException {
        nomeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNome()));
        energiaCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getEnergia()).asObject());
        gasolinaCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getGasolina()).asObject());
        emissaoCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getEmissao()).asObject());
        table.setItems(lista);
    }
    
    public void carregarDados(String empresa, String setor) throws IOException {
        setEmpresa(empresa);
        setSetor(setor);
        campo_nome_usu.setText(setor);
        carregarMaquinas();
    }

    
    @FXML
    void adicionarMaquina(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Adicionar Máquina");
        dialog.setContentText("Nome da máquina:");
        dialog.showAndWait().ifPresent(nome -> {
            TextInputDialog energiaDialog = new TextInputDialog("0");
            energiaDialog.setHeaderText("Consumo de Energia (kWh)");
            energiaDialog.showAndWait().ifPresent(energiaStr -> {
                TextInputDialog gasDialog = new TextInputDialog("0");
                gasDialog.setHeaderText("Consumo de Gasolina (L)");
                gasDialog.showAndWait().ifPresent(gasStr -> {
                    try {
                        double energia = Double.parseDouble(energiaStr);
                        double gasolina = Double.parseDouble(gasStr);
                        Maquina m = new Maquina(nome, energia, gasolina);
                        lista.add(m);
                        salvarMaquinas();
                    } catch (NumberFormatException e) {
                        alert("Erro", "Valores inválidos.");
                    } catch (IOException e) {
						e.printStackTrace();
					}
                });
            });
        });
    }

    @FXML
    void removerMaquina(ActionEvent event) throws IOException {
        Maquina m = table.getSelectionModel().getSelectedItem();
        if (m != null) {
            lista.remove(m);
            salvarMaquinas();
        } else {
            alert("Erro", "Selecione uma máquina.");
        }
    }
    
    SceneController s = new SceneController();
    
    @FXML
    void Sair(ActionEvent event) throws IOException {
    	s.escolha(event);
    }

    private void alert(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void salvarMaquinas() throws IOException {
        Map<String, List<Maquina>> todasMaquinas = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("EMPRESA:,")) {
                    String[] dados = linha.split(",");
                    if (dados.length >= 6) {
                        String emp = dados[1].trim();
                        String set = dados[2].trim();
                        String key = emp + "#" + set;

                        Maquina maquina = new Maquina(dados[3].trim(), Double.parseDouble(dados[4].trim()), Double.parseDouble(dados[5].trim()));

                        todasMaquinas.putIfAbsent(key, new ArrayList<>());
                        todasMaquinas.get(key).add(maquina);
                    }
                }
            }
        } catch (IOException e) {
        }

        String chaveAtual = getEmpresa() + "#" + getSetor();
        todasMaquinas.put(chaveAtual, new ArrayList<>(lista));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Map.Entry<String, List<Maquina>> entry : todasMaquinas.entrySet()) {
                String[] chave = entry.getKey().split("#");
                String empresa = chave[0];
                String setor = chave[1];

                for (Maquina m : entry.getValue()) {
                    bw.write("EMPRESA:," + empresa + "," + setor + "," + m.getNome() + "," + m.getEnergia() + "," + m.getGasolina());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            alert("Erro", "Erro ao salvar.");
        }
        Salvar();
    }


    public boolean carregarMaquinas() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("maquinas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("EMPRESA:,")) {
                    String[] dadosEmpresa = linha.split(",");
                    String nomeMaquina = dadosEmpresa[3].trim();
                    double energia = Double.parseDouble(dadosEmpresa[4].trim());
                    double gasolina = Double.parseDouble(dadosEmpresa[5].trim());                    
                    if (dadosEmpresa.length >= 6 && dadosEmpresa[1].equals(getEmpresa()) && dadosEmpresa[2].equals(getSetor())) {
                    	lista.add(new Maquina(nomeMaquina, energia, gasolina));
                    }
                }
            }
            return true;
        }  	
    }
    
    public void Salvar() {
        Map<String, List<String>> dadosEmpresas = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("setores.txt"))) {
            String linha = null;
            String empresaAtual = null;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("EMPRESA:")) {
                    empresaAtual = linha.substring(8).trim();
                    dadosEmpresas.put(empresaAtual, new ArrayList<>());
                } else if (empresaAtual != null && !linha.isEmpty()) {
                    dadosEmpresas.get(empresaAtual).add(linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        double energiaTotal = 0;
        double carbonoTotal = 0;

        for (Maquina m : lista) {
            energiaTotal += m.getEnergia();
            carbonoTotal += m.getEmissao();
        }
        String setorAtual = getSetor();
        List<String> setoresDaEmpresa = dadosEmpresas.get(getEmpresa());
        if (setoresDaEmpresa != null) {
            for (int i = 0; i < setoresDaEmpresa.size(); i++) {
                String[] dadosSetor = setoresDaEmpresa.get(i).split(",");
                if (dadosSetor[0].equals(setorAtual)) {
                    setoresDaEmpresa.set(i, setorAtual + "," + energiaTotal + "," + carbonoTotal + ",0"); // rank = 0 por padrão
                    break;
                }
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("setores.txt"))) {
            for (Map.Entry<String, List<String>> entry : dadosEmpresas.entrySet()) {
                writer.write("EMPRESA: " + entry.getKey());
                writer.newLine();
                for (String setor : entry.getValue()) {
                    writer.write(setor);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sugestoes(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("chatbot.fxml"));
            Parent root = loader.load();
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Chatbot");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();	
        }
    }

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}
    
    
    
    
}
