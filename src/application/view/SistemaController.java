package application.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class SistemaController {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button btnTema;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtSenha;

	private boolean isDarkMode = true;

	@FXML
	void trocarTema(ActionEvent event) {
		if (isDarkMode) {
			rootPane.getStyleClass().add("light-mode");
			btnTema.setText("Modo Escuro");
			isDarkMode = false;
		} else {
			rootPane.getStyleClass().remove("light-mode");
			btnTema.setText("Modo Claro");
			isDarkMode = true;
		}
	}

	@FXML // <--- Isso aqui é o que liga o código ao FXML
	void handleAcessar(ActionEvent event) {
		String usuario = txtUsuario.getText();
		String senha = txtSenha.getText();

		if ((usuario.equals("admin") && senha.equals("1234")) || (usuario.equals("vendedor") && senha.equals("4321"))) {

			abrirTela("/application/view/Menu.fxml", "Menu Principal");

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro de Login");
			alert.setHeaderText(null);
			alert.setContentText("Usuário ou senha incorretos!");
			alert.showAndWait();
		}
	}

	// Método auxiliar para trocar de tela
	private void abrirTela(String fxmlPath, String titulo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();

			Stage stage = (Stage) rootPane.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle(titulo);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erro ao carregar a tela: " + fxmlPath);
		}
	}
}
