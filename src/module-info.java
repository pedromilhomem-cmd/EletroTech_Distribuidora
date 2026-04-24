module EletroTech__Distribuidora {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base; // Necessário para as propriedades das tabelas
	requires java.sql; // Para sua futura conexão MySQL

	// Abre o pacote de visualização para o FXML injetar os componentes
	opens application.view to javafx.fxml, javafx.graphics;

	exports application.view;

	// IMPORTANTE: Abre o pacote model para o JavaFX conseguir ler os dados da sua
	// classe Usuario
	// Sem isso, a TableView ficará vazia ou dará erro de IllegalAccessException
	opens application.model to javafx.base;

	exports application.model;

	// Abre o pacote principal onde está a classe Main (que estende Application)
	opens application to javafx.fxml, javafx.graphics;

	exports application;
}