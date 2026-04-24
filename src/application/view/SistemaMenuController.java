package application.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SistemaMenuController {

	@FXML
	private MenuItem itemClientes;
	@FXML
	private MenuItem itemProcessoEstoque;
	@FXML
	private MenuItem itemProdutos;
	@FXML
	private MenuItem itemSair;
	@FXML
	private MenuItem imtemVendas;
	@FXML
	private MenuItem UsuarioNovo;

	@FXML
	private void initiliziar() {
		String tipo = null;
		if (tipo == "Vendedor") {
			UsuarioNovo.setVisible(false);
		} else {
			UsuarioNovo.setVisible(false);
			itemClientes.setVisible(false);
		}
	}

	public void Sair() {
		System.exit(0);
	}

	public void abrirCadastroProduto() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/CadastroProdutos.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void abrirCadastroCliente() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/CadastroCliente.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void abrirNovoUsuario() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/NovoUsuario.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void abrirProcessearEstoque() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/ProcessearEstoque.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void abrirVendas() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Vendas.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
