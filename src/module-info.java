module QLCHTT {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.sql;
	requires com.jfoenix;
	requires org.controlsfx.controls;

	exports view;
	exports model;
	exports controller;
	exports test;
	exports db;

	opens controller to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	opens model to javafx.graphics, javafx.fxml;
	opens test to javafx.graphics, javafx.fxml;
	opens db to javafx.graphics, javafx.fxml;
	opens Main to javafx.graphics, javafx.fxml;
}
