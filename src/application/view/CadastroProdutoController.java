package application.view;

import java.util.List;

import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadastroProdutoController {

	@FXML
	private Button btnSalvar;
	@FXML
	private TextField txtCategoria;
	@FXML
	private TextField txtDescricao;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtPreco;
	@FXML
	private TextField txtQuantidade;
	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnExcluir;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtCodigoBarras;

	@FXML
	private TableColumn<ProdutoModel, String> colCategoria;
	@FXML
	private TableColumn<ProdutoModel, String> colDescricao;
	@FXML
	private TableColumn<ProdutoModel, Integer> colID;
	@FXML
	private TableColumn<ProdutoModel, String> colNome;
	@FXML
	private TableColumn<ProdutoModel, Double> colPreco;
	@FXML
	private TableColumn<ProdutoModel, Integer> colQuantidade;
	@FXML
	private TableColumn<ProdutoModel, String> colCodigoBarras;

	@FXML
	private TableView<ProdutoModel> tabProduto;

	private ObservableList<ProdutoModel> listaProdutos;

	ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0, null);

	// =========================
	// SALVAR
	// =========================
	public void Salvar() {
		Alert mensage;

		if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtCategoria.getText().isEmpty()
				|| txtPreco.getText().isEmpty()) {

			String erro = "";
			if (txtNome.getText().isEmpty())
				erro += "\nCampo Nome em Branco!";
			if (txtDescricao.getText().isEmpty())
				erro += "\nCampo Descrição em Branco!";
			if (txtCategoria.getText().isEmpty())
				erro += "\nCampo Categoria em Branco!";
			if (txtPreco.getText().isEmpty())
				erro += "\nCampo Preço em Branco!";

			mensage = new Alert(Alert.AlertType.ERROR);
			mensage.setTitle("ERRO");
			mensage.setContentText("Preencha os campos:" + erro);
			mensage.show();

		} else {

			produto.setNome(txtNome.getText());
			produto.setDescricao(txtDescricao.getText());
			produto.setCategoria(txtCategoria.getText());

			// preço com vírgula
			produto.setPreco(Double.parseDouble(txtPreco.getText().replace(",", ".")));

			// quantidade sempre 0
			produto.setQuantidade(0);

			produto.setCodigo(txtCodigoBarras.getText());

			produto.Salvar();

			mensage = new Alert(Alert.AlertType.CONFIRMATION);
			mensage.setTitle("Confirmação");
			mensage.setContentText("Produto cadastrado com sucesso");
			mensage.show();

			LimparCampos();
			ListarProdutosTab(null);
		}
	}

	// =========================
	// BUSCAR (ENTER FUNCIONA AQUI)
	// =========================
	public void Pesquisar() {

		if (!txtBuscar.getText().isEmpty()) {

			produto.Buscar(txtBuscar.getText());
			ListarProdutosTab(txtBuscar.getText());

			PreencherCampos(produto);

		} else {
			Alert mensage = new Alert(Alert.AlertType.ERROR);
			mensage.setContentText("Preencha o campo de buscar");
			mensage.showAndWait();
			ListarProdutosTab(null);
		}
	}

	// =========================
	// EXCLUIR
	// =========================
	public void Excluir() {
		produto.Excluir();
		LimparCampos();
		ListarProdutosTab(null);
	}

	// =========================
	// INICIALIZAÇÃO
	// =========================
	@FXML
	public void initialize() {

		// ENTER na busca
		txtBuscar.setOnAction(e -> Pesquisar());

		// tabela clicável
		tabProduto.setOnMouseClicked(event -> {
			ProdutoModel p = tabProduto.getSelectionModel().getSelectedItem();
			if (p != null) {
				PreencherCampos(p);
			}
		});

		// colunas
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		colCodigoBarras.setCellValueFactory(new PropertyValueFactory<>("Codigo"));

		// formato ID
		colID.setCellFactory(column -> new TableCell<>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				setText((empty || item == null) ? null : String.format("%06d", item));
			}
		});

		// formato preço
		colPreco.setCellFactory(column -> new TableCell<>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.format("R$ %.2f", item).replace(".", ","));
				}
			}
		});

		// quantidade desativada
		txtQuantidade.setDisable(true);
		txtQuantidade.setText("0");

		ListarProdutosTab(null);
	}

	// =========================
	// LISTAR
	// =========================
	public void ListarProdutosTab(String valor) {
		List<ProdutoModel> produtos = produto.ListarProdutos(valor);
		listaProdutos = FXCollections.observableArrayList(produtos);
		tabProduto.setItems(listaProdutos);
	}

	// =========================
	// PREENCHER CAMPOS
	// =========================
	private void PreencherCampos(ProdutoModel p) {
		txtNome.setText(p.getNome());
		txtDescricao.setText(p.getDescricao());
		txtCategoria.setText(p.getCategoria());

		txtPreco.setText(String.format("%.2f", p.getPreco()).replace(".", ","));

		txtQuantidade.setText(String.valueOf(p.getQuantidade()));

		txtId.setText(String.format("%06d", p.getId()));

		txtCodigoBarras.setText(p.getCodigo());
	}

	// =========================
	// LIMPAR CAMPOS
	// =========================
	private void LimparCampos() {
		txtNome.clear();
		txtDescricao.clear();
		txtCategoria.clear();
		txtPreco.clear();
		txtQuantidade.setText("0");
		txtId.clear();
		txtCodigoBarras.clear();
	}
}