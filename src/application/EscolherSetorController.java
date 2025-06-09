package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EscolherSetorController {

    @FXML
    private ComboBox<String> comboSetores;
    @FXML
    private Button confirmarBtn;

    private Funcionario funcionario; 
    private String nomeEmpresa;
    private HubControllerFunc hubControllerFunc; 

    public void initialize() {
        comboSetores.setItems(FXCollections.observableArrayList());
    }

    public void setFuncionario(Funcionario funcionario, String nomeEmpresa, HubControllerFunc hubControllerFunc) {
        this.funcionario = funcionario;
        this.nomeEmpresa = nomeEmpresa;
        this.hubControllerFunc = hubControllerFunc;
        carregarSetoresDoArquivo();
    }

    private void carregarSetoresDoArquivo() {
        ObservableList<String> setores = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("setores.txt"))) {
            String linha;
            boolean empresaEncontrada = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("EMPRESA:")) {
                    empresaEncontrada = linha.equals("EMPRESA: " + nomeEmpresa);
                } else if (empresaEncontrada && !linha.trim().isEmpty()) {
                    String[] dados = linha.split(",");
                    if (dados.length >= 1) {
                        setores.add(dados[0]);
                    }
                }
            }

            comboSetores.setItems(setores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void confirmarSetor() {
        String setorSelecionado = comboSetores.getValue();
        if (setorSelecionado != null && funcionario != null) {
            funcionario.setSetor(setorSelecionado); 
            if (hubControllerFunc != null) {
                hubControllerFunc.atualizarTabela();
            }
            atualizarSetorDoFuncionario(funcionario.getNome(), setorSelecionado);
            Stage stage = (Stage) confirmarBtn.getScene().getWindow();
            stage.close();
        }
    }

    private void atualizarSetorDoFuncionario(String nomeUsuario, String novoSetor) {
        try {
            File arquivo = new File("usuarios.txt");
            List<String> linhas = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    if (linha.startsWith("Usuario:")) {
                        String[] partes = linha.split(",", -1); // -1 campos vazios

                        if (partes.length > 6 && partes[6].equals(nomeUsuario)) {
                            partes[5] = novoSetor; // posição 5: setor
                            linha = String.join(",", partes);
                        }
                    }
                    linhas.add(linha);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                for (String l : linhas) {
                    writer.write(l);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
