package application.model;

import java.sql.Connection;

import application.Conexao;
import javafx.scene.control.Alert;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoModel {
	private int id;
	private String nome;
	private String descricao;
	private String categoria;
	private double preco;
	private int quantidade;
	private String codigoBarras;

	public ProdutoModel(int id, String nome, String descricao, String categoria, double preco, int quantidade,
			String codigoBarras) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.preco = preco;
		this.quantidade = quantidade;
		this.codigoBarras = codigoBarras;
	}

	public String getCodigo() {
		return this.codigoBarras;
	}

	public void setCodigo(String codigo) {
		this.codigoBarras = codigo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void Salvar() {
		try (Connection conn = Conexao.getConnection();
				PreparedStatement consulta = conn.prepareStatement(
						"insert into produto (nome, descricao, categoria, preco, quantidade, codigoBarras) values (?,?,?,?,?,?)");) {

			// VERIFICA SE EXISTE ID
			if (this.id > 0) { // SE EXISTIR ALTERA, SENÃO CADASTRA
				try {
					PreparedStatement consultaUpdate = conn.prepareStatement(
							"update produto set nome=?,descricao=?,categoria=?,preco=?,quantidade=? where id=?");
					consultaUpdate.setString(1, this.nome);
					consultaUpdate.setString(2, this.descricao);
					consultaUpdate.setString(3, this.categoria);
					consultaUpdate.setDouble(4, this.preco);
					consultaUpdate.setInt(5, this.quantidade);
					consultaUpdate.setInt(6, this.id);
					consultaUpdate.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				consulta.setString(1, this.nome);
				consulta.setString(2, this.descricao);
				consulta.setString(3, this.categoria);
				consulta.setDouble(4, this.preco); // ESSAS DUAS ULTIMAS A GENTE MUDA O SET STRING PARA SET TIPO DA
													// VARIAVEL, JA QUE ESLAS NÃO SÃO STRING
				consulta.setInt(5, this.quantidade);

				consulta.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ListarProdutos(null);
	}

	public void Buscar(String Valor) {

		try (Connection conn = Conexao.getConnection();
				PreparedStatement consulta = conn.prepareStatement(
						"select * from produto where descricao like ? or categoria like ? or nome like ? or id like ?");) {

			// COLOCA INFORMAÇÕES NOS PARAMETROS DA CONSULTA SQL REPRESENTADA POR ?
			consulta.setString(1, "%" + Valor + "%");
			consulta.setString(2, "%" + Valor + "%");
			consulta.setString(3, "%" + Valor + "%");
			consulta.setString(4, "%" + Valor + "%");
			// GUARDA O RESULTADO EM UMA VARIAVEL DO TIPO RESULTSET (TIPO DE DADOS SQL)
			ResultSet resultado = consulta.executeQuery();
			// VERIFICA SE RETORNOU DADOS NA CONSULTA
			if (resultado.next()) {
				this.id = resultado.getInt("id");
				this.nome = resultado.getString("nome");
				this.descricao = resultado.getString("descricao");
				this.categoria = resultado.getString("categoria");
				this.preco = resultado.getDouble("preco");
				this.quantidade = resultado.getInt("quantidade");
			} else {
				// PRODUTO NÃO ENCONTRADO
				Alert mensage = new Alert(Alert.AlertType.ERROR);
				mensage.setTitle("Erro");
				mensage.setHeaderText(null);
				mensage.setContentText("Produto não encontrado!");
				mensage.showAndWait();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void Excluir() {

		try (Connection conn = Conexao.getConnection();
				PreparedStatement consulta = conn.prepareStatement("delete from produto where id=?");) {

			// VERIFICA SE O PRODUTO TEM ID
			if (this.id > 0) {
				consulta.setInt(1, this.id);
				consulta.executeUpdate();

				Alert mensage = new Alert(Alert.AlertType.CONFIRMATION);
				mensage.setTitle("Produto excluído");
				mensage.setHeaderText(null);
				mensage.setContentText("Produto excluído com sucesso!");
				mensage.showAndWait();

			} else {
				Alert mensage = new Alert(Alert.AlertType.ERROR);
				mensage.setTitle("Produto excluído");
				mensage.setHeaderText(null);
				mensage.setContentText("Produto não localizado!");
				mensage.showAndWait();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<ProdutoModel> ListarProdutos(String valor) {

		List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();
		try (Connection conn = Conexao.getConnection();
				PreparedStatement consulta = conn.prepareStatement("select * from produto");
				PreparedStatement consultaWhere = conn.prepareStatement(
						"select * from produto where nome like ? or descricao like ? or categoria like ?");) {

			ResultSet resultado = null;

			if (valor == null) {
				resultado = consulta.executeQuery();
			} else {
				consultaWhere.setString(1, "%" + valor + "%");
				consultaWhere.setString(2, "%" + valor + "%");
				consultaWhere.setString(3, "%" + valor + "%");
				resultado = consultaWhere.executeQuery();
			}

			// resultado=consulta.executeQuery();
			while (resultado.next()) {
				ProdutoModel p = new ProdutoModel(resultado.getInt("id"), resultado.getString("nome"),
						resultado.getString("descricao"), resultado.getString("categoria"),
						resultado.getDouble("preco"), resultado.getInt("quantidade"), resultado.getString("codigo"));

				produtos.add(p);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;

	}

}