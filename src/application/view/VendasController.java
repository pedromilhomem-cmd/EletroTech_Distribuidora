package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

public class VendasController {

	@FXML
	private Label lblTotalVenda, lblTroco;
	@FXML
	private TextField txtDesconto, txtValorPago, txtBuscaProduto;
	@FXML
	private ComboBox<String> cbFormaPagamento;

	private double totalVenda = 0.0;

	@FXML
	public void initialize() {
		cbFormaPagamento.getItems().addAll("Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX");
	}

	@FXML
	private void recalcularPagamento() {
		try {
			double desconto = txtDesconto.getText().isEmpty() ? 0 : Double.parseDouble(txtDesconto.getText());

			// Regra dos 5%
			double limiteDesconto = totalVenda * 0.05;
			if (desconto > limiteDesconto) {
				if (!solicitarSenhaGerente()) {
					txtDesconto.setText("0");
					desconto = 0;
				}
			}

			double valorPago = txtValorPago.getText().isEmpty() ? 0 : Double.parseDouble(txtValorPago.getText());
			double totalComDesconto = totalVenda - desconto;
			double troco = valorPago - totalComDesconto;

			lblTroco.setText(String.format("Troco: R$ %.2f", Math.max(0, troco)));
		} catch (NumberFormatException e) {
			// Silencioso ou log
		}
	}

	private boolean solicitarSenhaGerente() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Autorização de Gerente");
		dialog.setHeaderText("Desconto acima de 5% detectado.");
		dialog.setContentText("Digite a senha do gerente:");

		Optional<String> result = dialog.showAndWait();
		// Aqui você validaria com o banco de dados. Exemplo simples:
		return result.isPresent() && result.get().equals("admin123");
	}

	@FXML
	private void handleFinalizarVenda() {
		// Gerar o "Cupom"
		StringBuilder cupom = new StringBuilder();
		cupom.append("--- ELETROTECH DISTRIBUIDORA ---\n");
		cupom.append("Resumo da Venda\n");
		cupom.append("Total: ").append(lblTotalVenda.getText()).append("\n");
		cupom.append("Pagamento: ").append(cbFormaPagamento.getValue()).append("\n");
		cupom.append("------------------------------\n");
		cupom.append("OBRIGADO PELA PREFERÊNCIA!");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Venda Finalizada");
		alert.setHeaderText("Cupom Não Fiscal");
		alert.setContentText(cupom.toString());
		alert.showAndWait();

		limparTela();
	}

	private void limparTela() {
		totalVenda = 0;
		lblTotalVenda.setText("R$ 0,00");
		txtDesconto.clear();
		txtValorPago.clear();
		lblTroco.setText("Troco: R$ 0,00");
	}
}