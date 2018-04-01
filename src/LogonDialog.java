import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * LogonDialog.
 * 
 * @author Gerard Wypych / G.Wypych@stud.elka.pw.edu.pl
 * @version 1.0
 */
public class LogonDialog {

	// Window
	private Dialog<ButtonType> dialog;

	// Choice fields
	private ChoiceBox<Environment> cbxEnv;
	private ComboBox<String> cbxUsers;
	private PasswordField passField;

	// Labels
	private Label lblEnv;
	private Label lblUser;
	private Label lblPass;

	// Buttons
	private ButtonType loginButtonType;
	private ButtonType cancelButtonType;

	private Image icon;

	/**
	 * Constructor without parameters.
	 */
	public LogonDialog() {
		this("", "");
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param title
	 *            title of the window
	 * @param desc
	 *            header of the window
	 */
	public LogonDialog(String title, String desc) {

		dialog = new Dialog<>();

		cbxEnv = new ChoiceBox<>();
		cbxUsers = new ComboBox<>();
		passField = new PasswordField();

		lblEnv = new Label("Środowisko:");
		lblUser = new Label("Użytkownicy:");
		lblPass = new Label("Hasło:");

		loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		cancelButtonType = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);

		// Setting events
		setEvents();

		// Loading data
		loadData();

		// Setting appearance
		setLayout(title, desc);
	}

	/**
	 * Redefinition of Dialog.showAndWait() method.
	 * 
	 * @return Pair of environment and user's name who logged in. Null if login
	 *         failed.
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return Optional.ofNullable(resultConverter(dialog.showAndWait()));
	}

	/**
	 * Converts buttonType to pair of environment and user's name who logged in.
	 * 
	 * @param buttonType
	 *            result of the Dialog.showAndWait()
	 * @return pair of environment and user's name who logged in
	 */
	private Pair<Environment, String> resultConverter(Optional<ButtonType> buttonType) {
		if (buttonType.isPresent() && buttonType.get() == loginButtonType
				&& cbxEnv.getValue().checkUserPassword(cbxUsers.getValue(), passField.getText())) {
			return new Pair<Environment, String>(cbxEnv.getValue(), cbxUsers.getValue());
		}

		return null;
	}

	/**
	 * Sets layout of the window.
	 * 
	 * @param title
	 *            title of the window
	 * @param desc
	 *            header of the window
	 */
	private void setLayout(String title, String desc) {
		// Dialog
		dialog.setTitle(title);
		dialog.setHeaderText(desc);

		// ChoiceBox Environments
		cbxEnv.setPrefWidth(200);

		// ComoboBox Users
		cbxUsers.setEditable(true);
		cbxUsers.setPrefWidth(200);

		// PasswordField
		passField.setPrefWidth(200);

		// Layout
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(lblEnv, 0, 0);
		grid.add(lblUser, 0, 1);
		grid.add(lblPass, 0, 2);
		grid.add(cbxEnv, 1, 0);
		grid.add(cbxUsers, 1, 1);
		grid.add(passField, 1, 2);
		grid.setPadding(new Insets(25, 25, 25, 25));
		dialog.getDialogPane().setContent(grid);

		// Setting icon
		ImageView imageView = new ImageView(icon);
		dialog.setGraphic(imageView);

		// Adding buttons
		dialog.getDialogPane().getButtonTypes().add(cancelButtonType);
		dialog.getDialogPane().getButtonTypes().add(loginButtonType);
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
	}

	/**
	 * Sets events.
	 */
	private void setEvents() {
		cbxEnv.valueProperty().addListener((observable, oldVal, newVal) -> cbxEnv_Changed(newVal));
		cbxUsers.valueProperty().addListener((observable, oldVal, newVal) -> cbxUsers_Changed(newVal));
		passField.textProperty().addListener((observable, oldVal, newVal) -> passField_Changed(newVal));
	}

	/**
	 * Loads data.
	 */
	private void loadData() {
		icon = new Image(ClassLoader.getSystemResourceAsStream("icon.png"));
		loadEnvironments();
	}

	/**
	 * Load environments.
	 */
	private void loadEnvironments() {
		Environment envProd = new Environment("Produkcyjne");
		envProd.addUser("peter.producer", "prod123");
		envProd.addUser("john.smith", "john123");

		Environment envTest = new Environment("Testowe");
		envTest.addUser("tony.tester", "test123");
		envTest.addUser("timmy.ten", "ten123");

		Environment envDev = new Environment("Deweloperskie");
		envDev.addUser("devy.developer", "dev123");
		envDev.addUser("dennis.deep", "deep123");

		cbxEnv.setItems(FXCollections.observableArrayList(envProd, envTest, envDev));
	}

	/**
	 * Handling changes of the password field.
	 * 
	 * @param newVal
	 *            new value of the password field
	 */
	private void passField_Changed(String newVal) {
		passField.setText(newVal);

		updateLoginButtonState();
	}

	/**
	 * Handling changes of the users field.
	 * 
	 * @param newVal
	 *            new value of the user field
	 */
	private void cbxUsers_Changed(String newVal) {
		cbxUsers.setValue(newVal);

		updateLoginButtonState();
	}

	/**
	 * Handling changes of the environment field.
	 * 
	 * @param newVal
	 *            new value of the environment field
	 */
	private void cbxEnv_Changed(Environment newVal) {
		cbxEnv.setValue(newVal);
		cbxUsers.setItems(cbxEnv.getValue().getUsers());

		updateLoginButtonState();
	}

	/**
	 * Updates the login button state (disable or enable).
	 */
	private void updateLoginButtonState() {
		if (cbxEnv.getValue() == null || cbxUsers.getValue() == null || cbxUsers.getValue().isEmpty()
				|| passField.getText().isEmpty()) {
			dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
		} else {
			dialog.getDialogPane().lookupButton(loginButtonType).setDisable(false);
		}
	}
}
