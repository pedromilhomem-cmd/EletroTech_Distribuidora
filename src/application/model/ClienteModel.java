package application.model;

import java.util.ArrayList;
import java.util.List;

import application.Conexao;
import javafx.beans.property.*;

public class ClienteModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty nome = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty documento = new SimpleStringProperty();

	public ClienteModel(int id, String nome, String email, String documento) {
		this.id.set(id);
		this.nome.set(nome);
		this.email.set(email);
		this.documento.set(documento);
	}

	// Getters e Property (necessários para a TableView)
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty emailProperty() {
		return email;
	}

	public StringProperty documentoProperty() {
		return documento;
	}

	public static List<ClienteModel> ListarClientes(String valor) {
		List<ClienteModel> lista = new ArrayList<>();
		// Substitua 'Conexao.conectar()' pelo seu método real de conexão
		String sql = "SELECT * FROM clientes WHERE nome LIKE ? OR documento LIKE ?";

		try (java.sql.Connection conn = Conexao.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

			// Define o filtro de busca (ex: %joao%)
			String busca = "%" + valor + "%";
			stmt.setString(1, busca);
			stmt.setString(2, busca);

			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.add(new ClienteModel(rs.getInt("id"), rs.getString("nome"), rs.getString("email"),
						rs.getString("documento")));
			}

		} catch (Exception e) {
			System.err.println("Erro ao listar clientes: " + e.getMessage());
			e.printStackTrace();
		}
		return lista;
	}
}