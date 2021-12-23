module sceneBuilder {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	requires javax.servlet.api;
	requires javafx.base;

	opens application to javafx.graphics, javafx.fxml;
}
