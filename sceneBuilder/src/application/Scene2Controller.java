package application;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class Scene2Controller {
	@FXML
	private Label wrongUpdateInfo, addressInfo, balanceInfo, cityInfo, wrongPayment, nameInfo, pincodeInfo, jmbg,
			wrongTransaction, wrongInput1;
	@FXML
	// exchangeOffice
	private Label imePlatioca, iznosPrimaoca, kursPlatioca, racunaPlatioca, valutaPlatilac, imePrimaoca, racunPrimaoca,
			valutaPrimaoca, currencyMsg;
	@FXML
	private Button pay, sendPay, transaction, changeInfo;
	@FXML
	private Button currencySend;
	@FXML
	private TextField changeAddress, changeName, changeCity, brojPrimaoca, iznosPlatioca;
	@FXML
	private PasswordField pwLogin;
	@FXML
	private TextField paymentAmount, transactionJMBG, transactionName, transactionAmount;
	@FXML
	private Polyline polyLine;
	@FXML
	private Button backBtn;
	@FXML
	private AnchorPane contactDiv;
	@FXML
	private Label contact;
	String pin = "";
	public String backWindow = "";
	Parent root;
	Stage stage;
	Scene scene;
	float ukupanIznos;
	String iznosPrim;
	String iznosP;
	String windowOpen = "";
	@FXML
	private CheckBox pwShow;
	ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
	@FXML
	private ChoiceBox<String> comb;
	@FXML
	void backBtn(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		SampleController controller = new SampleController();
		String windowBack = controller.getBackBtn();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(windowBack + ".fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayInfo();
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	void sendPayment(ActionEvent event) throws Exception {
		Connection conn = getController();
		SampleController pin = new SampleController();
		String pw = pin.getR();
		String payAmount = paymentAmount.getText().toString();
		if (!payAmount.isEmpty()) {
			PreparedStatement stm = conn.prepareStatement("UPDATE userBank SET financialBill "
					+ "= financialBill + '"
					+ payAmount + "' WHERE password ='" + pw + "'");
			int updateSuccess = stm.executeUpdate();
			if (updateSuccess > 0) {
				windowOpen = "MainInterface";
				displayWindow(event);
				
			}
		} else {
			wrongPayment.setText("Please fill amount field!");
		}
	}
	@FXML
	void logOut(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	@FXML
	void displayWindow(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(windowOpen + ".fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayInfo();
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void displayRegularWindow(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(windowOpen + ".fxml"));
		root = loader.load();
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void displayInfo() throws ClassNotFoundException, SQLException, IOException {
		Connection conn = getController();
		SampleController pincode = new SampleController();
		String pw = pincode.getR();
		PreparedStatement stm = conn.prepareStatement("SELECT * FROM userBank WHERE "
				+ "password='" + pw + "'");
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			nameInfo.setText(rs.getString("fullName"));
			addressInfo.setText(rs.getString("address"));
			cityInfo.setText(rs.getString("city"));
			if (pwShow.isSelected()) {
				pwShow.setText("Hide pincode");
				pincodeInfo.setText(rs.getString("password"));
			} else {
				pwShow.setText("Show pincode");
				pincodeInfo.setText("*****");
			}
			balanceInfo.setText(rs.getString("financialBill") + "0" + " rsd");
		}

	}

	public Connection getController() throws ClassNotFoundException, SQLException, IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
		root = loader.load();
		SampleController sampleController = loader.getController();
		Connection conn = sampleController.DBConnection();
		return conn;
	}

	@FXML
	void payMent(ActionEvent event) throws Exception {
		SampleController controller = new SampleController();
		controller.setBackBtn("MainInterface");
		windowOpen = "payment";
		displayRegularWindow(event);
	}


	@FXML
	void transaction(ActionEvent event) throws Exception {
		String amount = transactionAmount.getText();
		Integer.valueOf(amount);
		SampleController controller = new SampleController();
		String pincode = controller.getR();
		String name = transactionName.getText();
		String jmbg = transactionJMBG.getText();
		if (amount.isEmpty() || name.isEmpty() || jmbg.isEmpty()) {
			wrongTransaction.setText("Fields can\'t be empty!");
			wrongTransaction.setTextFill(Color.rgb(255, 0, 0));
		} else {
			Connection conn = getController();
			PreparedStatement updateUserBill = conn
					.prepareStatement("SELECT * FROM userBank WHERE password=" + pincode + "");
			ResultSet rs = updateUserBill.executeQuery();
			if (rs.next()) {
				double amountInput = Double.parseDouble(amount);
				if (rs.getInt("financialBill") > amountInput) {
					PreparedStatement updateAmount = conn
							.prepareStatement("UPDATE userBank SET financialBill= financialBill-" + amountInput
									+ " WHERE password=" + pincode + "");
					updateAmount.executeUpdate();
					PreparedStatement transactionStm = conn
							.prepareStatement("UPDATE userBank SET financialBill = financialBill + '" + amountInput
									+ "'" + " WHERE fullName = '" + name + "' AND brojRacuna='" + jmbg + "'");
					int checkSuccess = transactionStm.executeUpdate();
					if (checkSuccess == 0) {
						wrongTransaction.setText("Invalid credentials. Try again!");
						wrongTransaction.setTextFill(Color.rgb(255, 0, 0));
					} else {
						windowOpen = "MainInterface";
						displayWindow(event);
					}
				} else {
					wrongTransaction.setText("Insuffucient funds");
				}
			}
		}
	}

	@FXML
	void transactionWindow(ActionEvent event) throws Exception {
		SampleController controller = new SampleController();
		controller.setBackBtn("MainInterface");
		windowOpen = "transaction";
		displayRegularWindow(event);
	}
	@FXML
	void changeInfoWindow(ActionEvent event) throws Exception {
		SampleController controller = new SampleController();
		controller.setBackBtn("MainInterface");
		windowOpen = "informations";
		displayRegularWindow(event);
	}

	@FXML
	void updateInfo(ActionEvent event) throws Exception {
		SampleController controller = new SampleController();
		Integer pincode = Integer.valueOf(controller.getR());
		// Connection
		Connection conn = getController();
		String name = changeName.getText().toString();
		String address = changeAddress.getText().toString();
		String city = changeCity.getText().toString();
		String queryName = "";
		String queryCity = "";
		String queryAddress = "";
		if (name.matches("[0-9]+") || address.matches("[0-9]") || city.matches("[0-9]+")) {
			wrongUpdateInfo.setText("Inputs can not contain numbers!");
		} else {
			if (!name.isEmpty()) {
				queryName = name;
				String sql = "Update userBank " + "set fullName = ? " + "where password = ?";
				PreparedStatement upd = conn.prepareStatement(sql);
				upd.setString(1, queryName);
				upd.setInt(2, pincode);
				upd.executeUpdate();
			}
			if (!address.isEmpty()) {
				String sql = "Update userBank " + "set address = ? " + "where password = ?";
				PreparedStatement upd = conn.prepareStatement(sql);
				upd.setString(1, queryAddress);
				upd.setInt(2, pincode);
				upd.executeUpdate();
			}
			if (!city.isEmpty()) {
				String sql = "Update userBank " + "set city = ? " + "where password = ?";
				PreparedStatement upd = conn.prepareStatement(sql);
				upd.setString(1, queryCity);
				upd.setInt(2, pincode);
				upd.executeUpdate();
			}
			windowOpen = "MainInterface";
			displayWindow(event);
		}
	}

	@FXML
	void deleteAccount(ActionEvent event) throws Exception {
		SampleController controller = new SampleController();
		String pincode = controller.getR();
		int valueDeletePrompt = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm delete",
				JOptionPane.YES_NO_OPTION);
		if (valueDeletePrompt == 0) {
			Connection conn = getController();
			String query = "DELETE FROM userBank WHERE password = ?";
			PreparedStatement deleteQuery = conn.prepareStatement(query);
			deleteQuery.setString(1, pincode);

			// execute the preparedstatement
			int successQuery = deleteQuery.executeUpdate();
			if (successQuery == 1) {
				System.out.println("Deleted");
				windowOpen = "Scene1";
				backBtn(event);
			}
			conn.close();
		}

	}

	@FXML
	void contactDisplay(MouseEvent event) {
		contactDiv.setVisible(true);
		polyLine.setVisible(true);
	}

	@FXML
	void contactHide(MouseEvent event) {
		contactDiv.setVisible(false);
		polyLine.setVisible(false);
	}

	@FXML
	void recipientInfo(KeyEvent event) throws ClassNotFoundException, SQLException, IOException {
		if (!brojPrimaoca.getText().isEmpty()) {
			Connection conn = getController();
			SampleController controller = new SampleController();
			PreparedStatement recipientQuery = conn
					.prepareStatement("SELECT * FROM userBank WHERE brojRacuna LIKE '" + brojPrimaoca.getText() + "%'");
			ResultSet rs = recipientQuery.executeQuery();
			while (rs.next()) {
				imePrimaoca.setText(rs.getString("fullName").toUpperCase());
			}
		}
	}

	@FXML
	void displayPayer() throws ClassNotFoundException, SQLException, IOException {
		Connection conn = getController();
		SampleController controller = new SampleController();
		String pw = controller.getR();
		PreparedStatement selectQuery = conn.prepareStatement("SELECT * FROM userBank WHERE password='" + pw + "'");
		ResultSet rs = selectQuery.executeQuery();
		while (rs.next()) {
			imePlatioca.setText(rs.getString("fullName").toUpperCase());
		}

	}

	private void loadData() {
		options.removeAll(options);
		String a = "Izaberi racun";
		String b = "Devizni racun";
		String c = "Tekuci racun";
		options.addAll(a, b, c);
		comb.getItems().addAll(options);
	}

	@FXML
	void folderByCategory() throws Exception {
		String a = "Devizni racun";
		String b = "Tekuci racun";
		System.out.println("You chose " + comb.getValue());
		if (a == comb.getValue()) {
			valutaPlatilac.setText("EUR");
			racunPrimaoca.setText("Tekuci racun");
			kursPlatioca.setText("117.18");
			valutaPrimaoca.setText("RSD");
			konacanIznosPrimaoca(null);
		} else if (b == comb.getValue()) {
			valutaPlatilac.setText("RSD");
			racunPrimaoca.setText("Devizni racun");
			valutaPrimaoca.setText("EUR");
			konacanIznosPrimaoca(null);

		}
	}

	@FXML
	void konacanIznosPrimaoca(KeyEvent event) throws Exception {
		iznosP = iznosPlatioca.getText().toString();
		if (!iznosP.isEmpty() && valutaPlatilac.getText().equals("EUR")) {
			ukupanIznos = (float) (Integer.valueOf(iznosP) * 117.18);
			iznosPrim = String.valueOf(ukupanIznos);
			iznosPrimaoca.setText(iznosPrim);
		} else if (!iznosP.isEmpty() && valutaPlatilac.getText().equals("RSD")) {
			ukupanIznos = Integer.valueOf(iznosP);
			ukupanIznos = (float) ((float) ukupanIznos / 117.18);
			String ukupanIznosFormat = String.format("%.02f", ukupanIznos);
			iznosPrim = String.valueOf(ukupanIznosFormat);
			iznosPrimaoca.setText(ukupanIznosFormat);
		}
	}

	boolean deleteAmount() throws ClassNotFoundException, SQLException, IOException {
		Connection conn = getController();
		SampleController controller = new SampleController();
		String pw = controller.getR();
		boolean statement = false;
		int iznos = Integer.valueOf(iznosPlatioca.getText());
		PreparedStatement stmSelect = conn.prepareStatement("SELECT * FROM userBank WHERE password='" + pw + "'");
		ResultSet rs = stmSelect.executeQuery();
		while (rs.next()) {
			double iznosPlat = Double.parseDouble(iznosPlatioca.getText());
			if (valutaPlatilac.getText().contentEquals("EUR")) {
				iznosPlat = (float) iznosPlat * 117.08;
			}
			if (rs.getInt("financialBill") > iznosPlat) {

				PreparedStatement stmDelete = conn
						.prepareStatement("UPDATE  userBank SET financialBill = financialBill - '" + iznosPlat
								+ "' WHERE password='" + pw + "'");
				stmDelete.executeUpdate();
				statement = true;
			} else {
				currencyMsg.setText("Nemate dovoljno sredstava na racunu!");
				statement = false;
			}
		}
		return statement;
	}

	@FXML
	void currency(ActionEvent event) throws Exception {
		String iznos = iznosPrimaoca.getText();
		String iznosPlat = iznosPlatioca.getText();
		Connection conn = getController();
		SampleController controller = new SampleController();
		controller.getR();
		if (deleteAmount() == true) {
			if (!iznos.isEmpty() && valutaPlatilac.getText().equals("EUR") && !brojPrimaoca.getText().isEmpty()) {
				PreparedStatement stmUpdate = conn
						.prepareStatement("UPDATE userBank SET financialBill = financialBill + '" + iznos
								+ "' WHERE brojRacuna='" + brojPrimaoca.getText() + "'");
				stmUpdate.executeUpdate();
				windowOpen = "MainInterface";
				displayWindow(event);
			} else if (!iznos.isEmpty() && valutaPlatilac.getText().equals("RSD")
					&& !brojPrimaoca.getText().isEmpty()) {
				PreparedStatement stmUpdate = conn
						.prepareStatement("UPDATE userBank SET financialBill = financialBill + '" + iznosPlat
								+ "' WHERE brojRacuna='" + brojPrimaoca.getText() + "'");
				stmUpdate.executeUpdate();
				windowOpen = "MainInterface";
				displayWindow(event);
			} else {
				currencyMsg.setText("Iznos i broj racuna se moraju popuniti!");
			}
		}

	}

	@FXML
	void currencyWindow(ActionEvent event) throws Exception {
		SampleController controller = new SampleController();
		controller.setBackBtn("MainInterface");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("exchangeOffice.fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayPayer();
		scene2Controller.loadData();
		scene2Controller.folderByCategory();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}