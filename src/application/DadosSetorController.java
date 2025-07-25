package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DadosSetorController {

    @FXML
    private TextField nomeSetor;
    
    @FXML
    private Button go;

    private HubController hubController;

    public void setHubController(HubController hubController) {
        this.hubController = hubController;
    }

    @FXML
    private void adicionarSetor() {
        String nome = nomeSetor.getText();
        if (nome.isEmpty()) {
            System.out.println("O nome do setor não pode ser vazio!");
            return;
        }
        //novo setor com valores padrão
        Setor novoSetor = new Setor(nome, 0, 0, 0);

        hubController.adicionarSetor(novoSetor);

        ((Stage) go.getScene().getWindow()).close();
    }
}
