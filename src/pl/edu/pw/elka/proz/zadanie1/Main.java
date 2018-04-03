package pl.edu.pw.elka.proz.zadanie1;

import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Main class.
 * 
 * @author Gerard Wypych / G.Wypych@stud.elka.pw.edu.pl
 * @version 1.0
 */
public class Main extends Application {

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Optional<Pair<Environment, String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu XYZ"))
				.showAndWait();

		if (result.isPresent()) {
			System.out.println(result.get().getValue() + " from " + result.get().getKey() + " logged in successfully.");
		} else
			System.out.println("Login failed.");
	}
}
