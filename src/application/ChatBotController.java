package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBotController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInput;

    @FXML
    private void handleSend() {
        String message = userInput.getText();
        if (message == null || message.isEmpty()) return;

        chatArea.appendText("Você: " + message + "\n");

        String response = GeminiClient.askGemini(message);
        chatArea.appendText("IA: " + response + "\n");

        userInput.clear();
    }
}
