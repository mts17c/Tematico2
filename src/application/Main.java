package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	@Override
	public void start(Stage janela) {
		try{
			//janela_principal
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("escolha.fxml"));
			Scene scene = new Scene(root);
			janela.setResizable(false);
			janela.setWidth(950);
			janela.setHeight(520);
			Image icon = new Image("/icon.png");
			janela.getIcons().add(icon);
			janela.setTitle("GreenPulse");
			janela.setScene(scene);
			janela.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
