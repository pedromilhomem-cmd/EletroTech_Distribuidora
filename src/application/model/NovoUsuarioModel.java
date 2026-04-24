package application.model;

import javafx.beans.property.*;

public class NovoUsuarioModel {
	private final IntegerProperty id;
	private final StringProperty nome;
	private final StringProperty login;
	private final StringProperty perfil;
	private final StringProperty ultimoAcesso;
	private String senha;

	public NovoUsuarioModel(Integer id, String nome, String login, String perfil, String senha) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.login = new SimpleStringProperty(login);
		this.perfil = new SimpleStringProperty(perfil);
		this.ultimoAcesso = new SimpleStringProperty("Nuncar"); // Padrão para novos
		this.senha = senha;
	}

	// Getters para as Properties (necessário para a TableView)
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty loginProperty() {
		return login;
	}

	public StringProperty perfilProperty() {
		return perfil;
	}

	public StringProperty ultimoAcessoProperty() {
		return ultimoAcesso;
	}

	public String getSenha() {
		return senha;
	}
}