package application;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SampleController{

	private Stage stage;
	private Scene scene;
	private Parent root;
	int totalAttempts = 3;
	int i = 60;
	@FXML
    private Label addressInfo, balanceInfo, CityInfo, nameInfo, pincodeInfo;
    @FXML
    private Button loginBtn, register;
    @FXML
    public  PasswordField pwLogin;
    @FXML 
    private Label wrongInput, wrongRegistration;
    @FXML
    public Label wrongInput1;
    @FXML
    private Hyperlink registerLink, loginLink;
    @FXML
    private TextField billAccount, cityForm, address, dateOfBirth,fullName, cityFrom;
    @FXML
    public static String pw = "";
    @FXML
    private AnchorPane showIconLogin;
    @FXML
    public static String window = "";
    @FXML
    void register(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		Stage window = (Stage) registerLink.getScene().getWindow();
		window.setScene(new Scene(root, 972, 730));
    }
    public void setR(String s) {
    	this.pw = s;
    }
    @FXML
    public String getR() {
    	return pw;
    }
    @FXML
   public void setBackBtn(String s) {
    	SampleController.window = s;
    }
    @FXML
    public String getBackBtn() {
    	return window;
    }
    @FXML
    void login(ActionEvent event) throws Exception {
    	if(!pwLogin.getText().toString().isEmpty()) {
    	Connection conn = DBConnection();
    	String password = pwLogin.getText().toString();
    	PreparedStatement stm = conn.prepareStatement("SELECT * FROM userBank WHERE password "
    			+ "= ?");	
    	stm.setString(1, password);
    	ResultSet rs = stm.executeQuery(); //executeUpdate(); Update delete 
    	if(rs.next()) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));	
    		root = loader.load();	
    		setR(password);
    		setBackBtn("Scene1");
    		Scene2Controller scene2Controller = loader.getController();
    		scene2Controller.displayInfo();
    		//root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    	else {
    		totalAttempts = totalAttempts - 1;
    		wrongInput.setText("You got  " + totalAttempts + " attempt left.");
    		
    			if(totalAttempts == 1) {
        		wrongInput.setText("You got only " + totalAttempts + " attempt left.");
    			}
    			if(totalAttempts == 0) {
    			i = 60;
    			Timeline timeline = new Timeline();
    					KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), event1 ->
    					{
    						pwLogin.setDisable(true);
    						loginBtn.setDisable(true);
    						i--;
			    			showIconLogin.setVisible(true);
    						wrongInput.setText("Too many incorrent attempts" + " " + i + "s");
    						if(i == 0) {
    							wrongInput.setText("");
    			    			pwLogin.setDisable(false);
    			    			loginBtn.setDisable(false);
    			    			showIconLogin.setVisible(false);
    			    			totalAttempts = 3;
    						}
    					});
    			timeline.getKeyFrames().add(keyFrame);
    			timeline.setCycleCount(60);
    			timeline.play();
    			}
    		}
    	}
    	else {
    		wrongInput.setText("Pincode field can't be empty");
    		wrongInput1.setText("");
    	}
    }
    @FXML
    void loginForm(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
		Stage window = (Stage) loginLink.getScene().getWindow();
		window.setScene(new Scene(root, 972, 730));
    }
    @FXML 
     void registration(ActionEvent event) throws Exception {
    	String fname = fullName.getText().toString();
    	SecureRandom randomNumber = new SecureRandom();
    	int num = randomNumber.nextInt(100000);
    	String pincode = String.format("%05d", num);
    	SecureRandom brojRacuna = new SecureRandom();
    	long brRacuna = brojRacuna.nextInt(1_000_000_000)             
                + (brojRacuna.nextInt(90) + 10) * 1_000_000_000L; 
    	String racun = String.format("%11d", brRacuna);
    	String dateofBirth = dateOfBirth.getText().toString();
    	String billAcc = billAccount.getText().toString();
    	String addressCity = address.getText().toString();
    	String city= cityFrom.getText().toString();
		if(fname.isEmpty() || dateofBirth.isEmpty() || billAcc.isEmpty() 
				|| addressCity.isEmpty() || city.isEmpty()) {
			wrongRegistration.setText("Please fill all input fields!");
		}
		else if(fname.matches("[0-9]+") || addressCity.matches("[0-9]+") || city.matches("[0-9]+")) {
			wrongRegistration.setText("Input only letters!");
		}
		else if(!billAcc.matches("[0-9]+")) {
			wrongRegistration.setText("Invalid bill finance or birth date!");
		}
		else {
		Connection connection =  DBConnection();
    	PreparedStatement registration =  connection.prepareStatement
    			("INSERT INTO userBank(fullName,password,financialBill,address,city,dateofBirth,brojRacuna)"
    			+ "VALUES"
    			+ "('"+fname+"','"+pincode+"', '"+billAcc+"', '"+addressCity+"', '"+city+"', '"+dateofBirth+"', "+racun+")");
    		registration.executeUpdate();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));	
    		root = loader.load();	
    		setR(pincode);
    		Scene2Controller scene2Controller = loader.getController();
    		scene2Controller.displayInfo();
    		connection.close();
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }
    @FXML 
    public  Connection DBConnection() throws ClassNotFoundException, SQLException {
    	Connection connection = null;
    	String databaseName = "bank";
		String url = "jdbc:mysql://localhost:3306/"+ databaseName;
		String user = "root";
		String pw = "password";
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url,user, pw);
		return connection;
	}

}