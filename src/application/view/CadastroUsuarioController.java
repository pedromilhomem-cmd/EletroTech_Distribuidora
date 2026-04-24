package application.view;

import application.model.NovoUsuarioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class CadastroUsuarioController {

	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtLogin;
	@FXML
	private PasswordField txtSenha;
	@FXML
	private PasswordField txtConfirmaSenha;
	@FXML
	private ComboBox<String> cbPerfil;

	// A TableView e as TableColumn devem usar NovoUsuarioModel
	@FXML
	private TableView<NovoUsuarioModel> tblUsuarios;
	@FXML
	private TableColumn<NovoUsuarioModel, Integer> colId;
	@FXML
	private TableColumn<NovoUsuarioModel, String> colNome;
	@FXML
	private TableColumn<NovoUsuarioModel, String> colLogin;
	@FXML
	private TableColumn<NovoUsuarioModel, String> colPerfil;
	@FXML
	private TableColumn<NovoUsuarioModel, String> colUltimoAcesso;

	// A lista também deve ser do tipo NovoUsuarioModel
	private ObservableList<NovoUsuarioModel> listaUsuarios = FXCollections.observableArrayList();
	private int proximoId = 1;

	@FXML
	public void initialize() {
		// Vinculação das colunas com as propriedades do NovoUsuarioModel
		colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colLogin.setCellValueFactory(cellData -> cellData.getValue().loginProperty());
		colPerfil.setCellValueFactory(cellData -> cellData.getValue().perfilProperty());
		colUltimoAcesso.setCellValueFactory(cellData -> cellData.getValue().ultimoAcessoProperty());

		tblUsuarios.setItems(listaUsuarios);
		cbPerfil.setItems(FXCollections.observableArrayList("Administrador", "Operador", "Relatórios"));
	}

	@FXML
	private void handleSalvarUsuario() {
		if (validarCampos()) {
			String nome = txtNome.getText();
			String login = txtLogin.getText();
			String perfil = cbPerfil.getValue();
			String senha = txtSenha.getText();

			// Instanciando com o novo nome da classe
			NovoUsuarioModel novoUsuario = new NovoUsuarioModel(proximoId++, nome, login, perfil, senha);
			listaUsuarios.add(novoUsuario);

			limparCampos();
			exibirAlerta(AlertType.INFORMATION, "Sucesso", "Usuário salvo com sucesso!");
		}
	}

	@FXML
	private void handleExcluirUsuario() {
		// Tipo do objeto selecionado atualizado
		NovoUsuarioModel selecionado = tblUsuarios.getSelectionModel().getSelectedItem();

		if (selecionado != null) {
			listaUsuarios.remove(selecionado);
			exibirAlerta(AlertType.INFORMATION, "Excluído", "Usuário removido da lista.");
		} else {
			exibirAlerta(AlertType.WARNING, "Atenção", "Selecione um usuário na tabela para excluir.");
		}
	}

	private boolean validarCampos() {
		String msgErro = "";

		if (txtNome.getText().isEmpty())
			msgErro += "Nome inválido!\n";
		if (txtLogin.getText().isEmpty())
			msgErro += "Login inválido!\n";
		if (cbPerfil.getValue() == null)
			msgErro += "Selecione um perfil!\n";
		if (txtSenha.getText().isEmpty())
			msgErro += "Senha vazia!\n";
		if (!txtSenha.getText().equals(txtConfirmaSenha.getText()))
			msgErro += "As senhas não conferem!\n";

		if (msgErro.length() == 0) {
			return true;
		} else {
			exibirAlerta(AlertType.ERROR, "Campos Inválidos", msgErro);
			return false;
		}
	}

	private void limparCampos() {
		txtNome.clear();
		txtLogin.clear();
		txtSenha.clear();
		txtConfirmaSenha.clear();
		cbPerfil.getSelectionModel().clearSelection();
	}

	private void exibirAlerta(AlertType tipo, String titulo, String mensagem) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}