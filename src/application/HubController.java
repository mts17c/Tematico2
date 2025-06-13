package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HubController {
	
    @FXML
    private Label campo_nome_usu;
    @FXML
    private Label campo_nome_usu1;
    @FXML
    private TableView<Setor> table;
    @FXML
    private TableColumn<Setor, String> nome;
    @FXML
    private TableColumn<Setor, Double> carb;
    @FXML
    private TableColumn<Setor, Double> energia;
    @FXML
    private TableColumn<Setor, Integer> rank;
    
    private ObservableList<Setor> setores = FXCollections.observableArrayList();
    
    @FXML
    private Button adicionar;
    @FXML
    private Button remover;
    @FXML
    private Button sair;
    @FXML
    private Button funcionarios;
    
    static String x;
    
    SceneController s = new SceneController();
    HubControllerFunc h = new HubControllerFunc();
    
    public void setNomeUsuarioEmp(String nome) {
        campo_nome_usu.setText(nome);
        x=nome;
        carregarSetoresDeArquivo(x);
    }
    
    public void Sair(javafx.event.ActionEvent event) throws IOException {
    	s.escolha(event);
    }  
    
    public void Funcionarios(javafx.event.ActionEvent event) throws IOException {
    	h.controla(x);
    	s.Funcionarios(event);
    }

    public void initialize() {
    	nome.setCellValueFactory(new PropertyValueFactory<Setor, String>("nome"));
    	energia.setCellValueFactory(new PropertyValueFactory<Setor, Double>("energia"));
    	carb.setCellValueFactory(new PropertyValueFactory<Setor, Double>("carb"));
    	rank.setCellValueFactory(new PropertyValueFactory<Setor, Integer>("rank"));
    	table.setItems(setores);
    	adicionar.setOnAction(event -> abrirTelaAdicionarSetor());
    	remover.setOnAction(event -> {
			try {
				removerSetor();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
    }
    
    private void abrirTelaAdicionarSetor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("dados_setor.fxml"));
            Parent root = loader.load();
            
            DadosSetorController dadosController = loader.getController();
            dadosController.setHubController(this);
            
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarSetor(Setor setor) {
        setores.add(setor);
        atualizarRankings();
        salvarSetoresEmArquivo(x);
        table.refresh();
    }
    
    @FXML
    public void removerSetor() throws IOException {
        Setor setorSelecionado = table.getSelectionModel().getSelectedItem();
        if (setorSelecionado != null && verifica(setorSelecionado.getNome())) {
            setores.remove(setorSelecionado);
            atualizarRankings(); 
            salvarSetoresEmArquivo(x);
            table.refresh();
        }
    }

    public Setor getSetorSelecionado() {
        return table.getSelectionModel().getSelectedItem();
    }
    
    public void salvarSetoresEmArquivo(String nomeEmpresa) {
        Map<String, List<Setor>> todasEmpresas = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("setores.txt"))) {
            String linha;
            String empresaAtual = null;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("EMPRESA:")) {
                    empresaAtual = linha.substring(8).trim();
                    todasEmpresas.putIfAbsent(empresaAtual, new ArrayList<>());
                } else if (empresaAtual != null && !linha.isEmpty()) {
                    String[] dados = linha.split(",");
                    if (dados.length == 4) {
                    	Setor setor = new Setor(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]), Integer.parseInt(dados[3]));
                        todasEmpresas.get(empresaAtual).add(setor);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        todasEmpresas.put(nomeEmpresa, new ArrayList<>(setores));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("setores.txt"))) {
            for (Map.Entry<String, List<Setor>> entry : todasEmpresas.entrySet()) {
                writer.write("EMPRESA: " + entry.getKey());
                writer.newLine();
                for (Setor setor : entry.getValue()) {
                    writer.write(setor.getNome() + "," + setor.getEnergia() + "," + setor.getCarb() + "," + setor.getRank());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void atualizarRankings() {
        setores.sort((s1, s2) -> Double.compare(s1.getCarb(), s2.getCarb()));
        for (int i = 0; i < setores.size(); i++) {
            setores.get(i).setRank(i + 1);
        }
    }


    
    public void carregarSetoresDeArquivo(String nomeEmpresa) {
        setores.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("setores.txt"))) {
            String linha;
            boolean empresaEncontrada = false;
            
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("EMPRESA:")) {
                    empresaEncontrada = linha.equals("EMPRESA: " + nomeEmpresa);
                } else if (empresaEncontrada && !linha.isEmpty()) {
                    String[] dados = linha.split(",");
                    if (dados.length == 4) {
                        Setor setor = new Setor(dados[0], Double.parseDouble(dados[1]), Double.parseDouble(dados[2]), Integer.parseInt(dados[3]));
                        setores.add(setor);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        atualizarRankings();
        table.refresh();
    }
    
    public boolean verifica(String setor) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Usuario:,")) {
                    String[] dadosEmpresa = linha.split(",");
                    if (dadosEmpresa.length >= 5 && dadosEmpresa[5].equals(setor)) {
                    	return false;
                    }
                }
            }
            return true;
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

}
