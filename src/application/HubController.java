package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private TableColumn<Setor, Integer> cod;
    @FXML
    private TableColumn<Setor, Integer> carb;
    @FXML
    private TableColumn<Setor, Integer> energia;
    @FXML
    private TableColumn<Setor, Integer> rank;
    
    private ObservableList<Setor> setores = FXCollections.observableArrayList();
    
    @FXML
    private Button adicionar;
    @FXML
    private Button remover;
    @FXML
    private Button sair;
    
    static String x;
    
    SceneController s = new SceneController();
    
    public void setNomeUsuario(String nome) {
        campo_nome_usu1.setText(nome);
    }
    
    public void setNomeUsuarioEmp(String nome) {
        campo_nome_usu.setText(nome);
        x=nome;
        carregarSetoresDeArquivo(x);
    }  
    
    public void Sair(javafx.event.ActionEvent event) throws IOException {
    	s.escolha(event);
    }   

    public void initialize() {
    	nome.setCellValueFactory(new PropertyValueFactory<Setor, String>("nome"));
    	cod.setCellValueFactory(new PropertyValueFactory<Setor, Integer>("cod"));
    	energia.setCellValueFactory(new PropertyValueFactory<Setor, Integer>("energia"));
    	carb.setCellValueFactory(new PropertyValueFactory<Setor, Integer>("carb"));
    	rank.setCellValueFactory(new PropertyValueFactory<Setor, Integer>("rank"));
    	table.setItems(setores);
    	adicionar.setOnAction(event -> abrirTelaAdicionarSetor());
    	remover.setOnAction(event -> removerSetor());
    }
    
    private void abrirTelaAdicionarSetor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("dados_setor.fxml"));
            Parent root = loader.load();
            
            DadosSetorController dadosController = loader.getController();
            dadosController.setHubController(this);
            
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Adicionar setor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarSetor(Setor setor) {
        setores.add(setor);
        salvarSetoresEmArquivo(x);
    }
    
    @FXML
    public void removerSetor() {
        Setor setorSelecionado = table.getSelectionModel().getSelectedItem();
        if (setorSelecionado != null) {
            setores.remove(setorSelecionado);
            salvarSetoresEmArquivo(x);
        }
    }

    public Setor getSetorSelecionado() {
        return table.getSelectionModel().getSelectedItem();
    }
    
    public void salvarSetoresEmArquivo(String nomeEmpresa) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("setores.txt"))) {
            writer.write("EMPRESA: " + nomeEmpresa);
            writer.newLine();
            for (Setor setor : setores) {
                writer.write(setor.getNome() + "," + setor.getCod() + "," + setor.getEnergia() + "," + setor.getCarb() + "," + setor.getRank());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                    if (dados.length == 5) {
                        Setor setor = new Setor(dados[0], Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), Integer.parseInt(dados[3]), Integer.parseInt(dados[4]));
                        setores.add(setor);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
