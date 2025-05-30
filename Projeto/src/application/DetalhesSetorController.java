package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetalhesSetorController {
    @FXML
    private Label nomeLabel;
    @FXML
    private TextField nomeField;
    @FXML
    private Label codLabel;
    @FXML
    private TextField codField;
    @FXML
    private Label energiaLabel;
    @FXML
    private TextField energiaField;
    @FXML
    private Label carbLabel;
    @FXML
    private TextField carbField;
    @FXML
    private Label rankLabel;
    @FXML
    private TextField rankField;
    @FXML
    private Button salvarButton;
    @FXML
    private Button fecharButton;
    
    private Setor setor;

    public void setSetor(Setor setor) {
        this.setor = setor;
        javafx.application.Platform.runLater(() -> {
            nomeField.setText(setor.getNome());
            codField.setText(String.valueOf(setor.getCod()));
            energiaField.setText(String.valueOf(setor.getEnergia()));
            carbField.setText(String.valueOf(setor.getCarb()));
            rankField.setText(String.valueOf(setor.getRank()));
        });
    }


    @FXML
    private void salvarAlteracoes() {
        setor.setNome(nomeField.getText());
        setor.setCod(codField.getText());
        setor.setEnergia(Integer.parseInt(energiaField.getText()));
        setor.setCarb(Integer.parseInt(carbField.getText()));
        setor.setRank(Integer.parseInt(rankField.getText()));
        fechar();
    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) fecharButton.getScene().getWindow();
        stage.close();
    }
}
