package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static final String URL = "jdbc:mysql://localhost:3306/sistemas";
	private static final String USER = "root";
	private static final String PASS = "(Pedro123)";

	public static Connection getConnection() {
		try {
			// Opcional para versões recentes, mas bom para garantir compatibilidade
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.err.println("Driver do MySQL não encontrado!");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
			return null;
		}
	}
}