package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    private ObservableList<Funcionario> func = FXCollections.observableArrayList();
    
    public void controla(String nome1) {
    	HubControllerFunc.x=nome1;
    }
    
    public void initialize() throws FileNotFoundException, IOException {
		nome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
    	setor.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("setor"));
    	carregarFuncionariosDeArquivo(x);
    	table.setItems(func);
    }
    
    public void carregarFuncionariosDeArquivo(String nomeEmpresa) throws FileNotFoundException, IOException {
        func.clear(); // Limpa a lista antes de adicionar novos dados
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Usuario:,")) {
                    String[] dadosEmpresa = linha.split(",");
                    // Verifica se a linha tem pelo menos 6 campos
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


}

