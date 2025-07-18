package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HubControllerFunc {

    @FXML
    private Label campo_nome_usu;
    static String x;
    @FXML
    private TableView<Funcionario> table;
    @FXML
    private TableColumn<Funcionario, String> nome;
    @FXML
    private TableColumn<Funcionario, String> setor;
    @FXML
    private javafx.scene.control.Button Voltar;
    @FXML
    private javafx.scene.control.Button editar; 
    
    private ObservableList<Funcionario> func = FXCollections.observableArrayList();
    
    SceneController s = new SceneController();
    
    public void controla(String nome1) {
    	HubControllerFunc.x=nome1;
    }
    
    public void initialize() throws FileNotFoundException, IOException {
		nome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
    	setor.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("setor"));
    	carregarFuncionariosDeArquivo(x);
    	table.setOnMouseClicked(event -> {
    	    if (event.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null) {
    	        try {
					editarFuncionario();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	    }
    	});
    	table.setItems(func);
    }
    
    public void carregarFuncionariosDeArquivo(String nomeEmpresa) throws FileNotFoundException, IOException {
        func.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Usuario:,")) {
                    String[] dadosEmpresa = linha.split(",");
                    if (dadosEmpresa.length >= 5 && dadosEmpresa[4].equals(nomeEmpresa)) {
                        String nome = dadosEmpresa[1].trim();
                        String setor = dadosEmpresa[5].trim();
                        Funcionario f = new Funcionario(nome, setor);
                        func.add(f);
                    }
                }
            }
        }
    }
    
    HubControllerUsu h = new HubControllerUsu();
    
    public void Sair(javafx.event.ActionEvent event) throws IOException {
    	h.Sair1(event);
    }
    
    public void editarFuncionario() throws IOException {
        Funcionario selecionado = table.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
        	    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("setores.fxml"));
                Parent root = loader.load();
                EscolherSetorController controller = loader.getController();
                controller.setFuncionario(selecionado, HubController.x, this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Escolher Setor");
                stage.show();
        }
    }
    
    public void atualizarTabela() {
        table.refresh();
    }

    
    


}

