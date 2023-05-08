module QLCHTT {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.sql;
	exports view;
	exports model;
	exports controller;
	exports test;
	
	opens controller to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	opens model to javafx.graphics, javafx.fxml;
	opens test to javafx.graphics, javafx.fxml;
}
