package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HubController {
    @FXML
    private Label campo_nome_usu;

    public void setNomeUsuario(String nome) {
        campo_nome_usu.setText(nome);
    }
}
