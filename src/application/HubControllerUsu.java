package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HubControllerUsu {
	
    @FXML
    private Button sair;
    
    
    SceneController s = new SceneController();
    
    public void Sair(javafx.event.ActionEvent event) throws IOException {
    	s.escolha(event);
    } 
}
