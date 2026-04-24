package application.view;

import application.model.ClienteModel; // Importação atualizada
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ClienteController {

	@FXML
	private Button btnBuscar, btnExcluir, btnSalvar;
	@FXML
	private TextField txtBusca, txtDocumento, txtEmail, txtNome, txtTelefone;
	@FXML
	private ComboBox<String> cbStatus;

	// Configuração da Tabela usando ClienteModel
	@FXML
	private TableView<ClienteModel> tblClientes;
	@FXML
	private TableColumn<ClienteModel, Integer> colId;
	@FXML
	private TableColumn<ClienteModel, String> colNome;
	@FXML
	private TableColumn<ClienteModel, String> colEmail;
	@FXML
	private TableColumn<ClienteModel, String> colDocumento;

	private ObservableList<ClienteModel> listaClientes = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		// Vincula as colunas aos atributos da ClienteModel
		colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
		colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
		colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		colDocumento.setCellValueFactory(cellData -> cellData.getValue().documentoProperty());

		// Popula o ComboBox de Status
		cbStatus.setItems(FXCollections.observableArrayList("Ativo", "Inativo"));

		// Vincula a lista observável à TableView
		tblClientes.setItems(listaClientes);
	}

	@FXML
	void handleSalvar(ActionEvent event) {
		if (txtNome.getText().isEmpty() || txtDocumento.getText().isEmpty()) {
			exibirAlerta("Erro de Validação", "Nome e Documento são obrigatórios!");
			return;
		}

		// Instancia a nova classe ClienteModel
		ClienteModel novoCliente = new ClienteModel(listaClientes.size() + 1, txtNome.getText(), txtEmail.getText(),
				txtDocumento.getText());

		listaClientes.add(novoCliente);
		limparCampos();
		exibirAlerta("Sucesso", "Cliente cadastrado com sucesso!");
	}

	@FXML
	void handleExcluir(ActionEvent event) {
		ClienteModel selecionado = tblClientes.getSelectionModel().getSelectedItem();
		if (selecionado != null) {
			listaClientes.remove(selecionado);
		} else {
			exibirAlerta("Seleção Necessária", "Por favor, selecione um cliente na tabela para excluir.");
		}
	}

	@FXML
	void handleBuscar(ActionEvent event) {
		String termo = txtBusca.getText().toLowerCase();
		if (termo.isEmpty()) {
			tblClientes.setItems(listaClientes);
		} else {
			ObservableList<ClienteModel> filtrados = listaClientes
					.filtered(c -> c.nomeProperty().get().toLowerCase().contains(termo)
							|| c.documentoProperty().get().contains(termo));
			tblClientes.setItems(filtrados);
		}
	}

	private void limparCampos() {
		txtNome.clear();
		txtEmail.clear();
		txtDocumento.clear();
		txtTelefone.clear();
		cbStatus.getSelectionModel().clearSelection();
	}

	private void exibirAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}