package application.model;

import java.util.ArrayList;
import java.util.List;

public class VendaModel {
	private List<ProdutoModel> itens = new ArrayList<>();
	private double total;
	private double desconto;

	public void adicionarItem(ProdutoModel produto) {
		itens.add(produto);
		atualizarTotal();
	}

	private void atualizarTotal() {
		this.total = itens.stream().mapToDouble(p -> p.getPreco()).sum();
	}

	// Getters e Setters
	public double getTotalComDesconto() {
		return total - desconto;
	}

	public double getTotal() {
		return total;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public List<ProdutoModel> getItens() {
		return itens;
	}
}