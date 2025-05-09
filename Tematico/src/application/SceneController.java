package application;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	static int jaexiste;
	int aux=0;
	
	@FXML
	private TextField login_usu;
	@FXML
	private TextField senha_usu;
	@FXML
	private Label label_usu;
	@FXML
	private TextField login_emp;
	@FXML
	private TextField senha_emp;
	@FXML
	private Label label_emp;
	@FXML
	private TextField registro_nome_usu;
	@FXML
	private TextField registro_senha_usu;
	@FXML
	private TextField registro_email_usu;
	@FXML
	private TextField registro_nome_emp;
	@FXML
	private TextField registro_senha_emp;
	@FXML
	private TextField registro_cnpj_emp;	
	@FXML
	private Label label_reg_emp;
	@FXML
	private Label label_reg_usu;
	@FXML
	private Label campo_nome_usu;	
    
	public void escolha_para_login_usuario(javafx.event.ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void escolha_para_login_empresa(javafx.event.ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("login_empresa.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void login_para_registro_usuario(javafx.event.ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("registro.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		jaexiste=1;
	}
	
	public void login_para_registro_empresa(javafx.event.ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("registro_empresa.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		jaexiste=2;
	}
	
	public void registro_para_sucesso(javafx.event.ActionEvent event) throws IOException {
		if(jaexiste==1) {
			String nome_usu = registro_nome_usu.getText();
			String senha_usu = registro_senha_usu.getText();
			String email_usu = registro_email_usu.getText();
			if(nome_usu.isEmpty() || senha_usu.isEmpty() || email_usu.isEmpty()) {
				aux=1;
			}
		    if (verifica_registro_usu(nome_usu) && aux==0) {
		        root = FXMLLoader.load(getClass().getClassLoader().getResource("registro_sucesso.fxml"));
		        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
		    } else {
		    	label_reg_usu.setText("Invalido!");
		    	aux=0;
		    }
		}
		if(jaexiste==2) {
			String nome_emp = registro_nome_emp.getText();
			String cnpj_emp = registro_cnpj_emp.getText();
			String senha_emp = registro_senha_emp.getText();
			if(nome_emp.isEmpty() || cnpj_emp.isEmpty() || senha_emp.isEmpty()) {
				aux=1;
			}
		    if (verifica_registro_emp(nome_emp, cnpj_emp, senha_emp) && aux==0) {
		        root = FXMLLoader.load(getClass().getClassLoader().getResource("registro_sucesso.fxml"));
		        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		        scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
		    } else {
		    	label_reg_emp.setText("Invalido!");
		    	aux=0;
		    }
		}	
	}

	public void escolha_para_registro(javafx.event.ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("registro.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void sucesso_para_escolha(javafx.event.ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("escolha.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void login_para_hub(javafx.event.ActionEvent event) throws IOException, URISyntaxException {
	    String login = login_usu.getText();
	    String senha = senha_usu.getText();
	    
	    if (verificarLogin_Usu(login, senha)) {
	        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Hub.fxml"));
	        Parent root = loader.load();
	        
	        HubController hubController = loader.getController();
	        hubController.setNomeUsuario(login);
	        
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } else {
	    	label_usu.setText("Login ou senha inválidos");
	    }
	}
	
	public void loginemp_para_hub(javafx.event.ActionEvent event) throws IOException {
	    String login = login_emp.getText();
	    String senha = senha_emp.getText();

	    if (verificarLogin_Emp(login, senha)) {
	        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Hub_emp.fxml"));
	        Parent root = loader.load();
	        
	        HubController hubController = loader.getController();
	        hubController.setNomeUsuario(login);
	        
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();    
	    } else {
	    	label_emp.setText("Login ou senha inválidos");
	    }
	}
	
	private boolean verificarLogin_Usu(String login, String senha) throws URISyntaxException{
		try(InputStream is = getClass().getResourceAsStream("/usuarios.txt"); 
				BufferedReader br = new BufferedReader(new InputStreamReader(is))){
				String linha;
				while((linha = br.readLine()) != null) {
	                String[] dados = linha.split(",");
	                String nome = dados[0];
	                String senha1 = dados[1];
	                String email = dados[2];
	                if (nome.equals(login) && senha1.equals(senha)) {
	                    return true; 
	                }
				}
			}catch (Exception e) {
			}
			return false;
    }

	private boolean verificarLogin_Emp(String login, String senha) throws IOException{
		try(InputStream is = getClass().getResourceAsStream("/empresas.txt"); 
			BufferedReader br = new BufferedReader(new InputStreamReader(is))){
			String linha;
			while((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String nome = dados[0];
                String cnpj = dados[1];
                String senha1 = dados[2];  
                if (nome.equals(login) && senha1.equals(senha)) {
                    return true; 
                }
			}
		}catch (Exception e) {
		}
		return false;
    }
	
	private boolean verifica_registro_usu(String nome1) {
		try(InputStream is = getClass().getResourceAsStream("/usuarios.txt"); 
				BufferedReader br = new BufferedReader(new InputStreamReader(is))){
				String linha;
				while((linha = br.readLine()) != null) {
	                String[] dados = linha.split(",");
	                String nome = dados[0];
	                if (nome.equals(nome1)) {
	                    return false; 
	                }
				}
			}catch (Exception e) {
			}
			return true;		
	}

	private boolean verifica_registro_emp(String nome1, String cnpj, String senha) {
	    File file = new File("empresas.txt");
	    boolean empresaExiste = false;
	    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	        String linha;
	        while ((linha = br.readLine()) != null) {
	            String[] dados = linha.split(",");
	            if (dados.length >= 2 && dados[0].equals(nome1) && dados[1].equals(cnpj)) {
	                empresaExiste = true;
	                break;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    if (!empresaExiste) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
	            bw.write(nome1 + "," + cnpj + "," + senha);
	            bw.newLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return !empresaExiste; 
	}

	

}

