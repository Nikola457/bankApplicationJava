package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
			Scene scene = new Scene(root,930,730);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main (String[]args) throws ClassNotFoundException, SQLException{
	
		launch(args);
		
				
	}
	
}