/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.H;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pantherinspectproject.SignupForm;
import pantherinspectproject.QueryProcessor;
import pantherinspectproject.Database;
import pantherinspectproject.Time;
//import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author cindyramirez
 */
public class PantherInspectProject extends Application 
{
    SignupForm signupform = new SignupForm(this);
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static final String UPLOAD_PATH = "C://Users//allis//Pictures//";
    
    public static final String SETUP_FILE = "tables.sql";

    public Database db;
    public QueryProcessor qp;
    public Connection conn = null;
    
    
    private String setup() {
        db = new Database();
        String db_url = db.createURL("localhost","PantherInspect");
        conn = db.createConnection(db_url,USER,PASS);
        qp = new QueryProcessor(conn);
        qp.createTables(SETUP_FILE);
        return db_url;
    }
    
    private void checkPassword(String email, String password) throws Exception {
        ResultSet rs = qp.selectAccountHash(email);
        rs.next();
        String hashedPass = rs.getString(1);
        if(BCrypt.checkpw(password, hashedPass)) {
            System.out.println("Correct Password!");
        } else {
            System.out.println("Incorrect Password!");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error has Occurred.");
            alert.setContentText("Your username or password was incorrect.");
                    
            alert.showAndWait();
        }
    }
    
    private Button createSigninButton(TextField emailField, PasswordField pwField) {
        Button btn = new Button("Sign in");
        btn.setOnAction((ActionEvent event) -> {
            
            System.out.println("signing in..");
            try {
                checkPassword(emailField.getText(),pwField.getText());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
        return btn;
    }
    
    private Button createSignupButton(Stage primaryStage) {
        Button SUbtn = new Button("Sign Up to get an Account! ");
        SUbtn.setOnAction((ActionEvent e) -> {
            
            primaryStage.setScene(signupform.form(primaryStage));
            
        }); 
        return SUbtn;
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
        String db_url = setup();
        
        primaryStage.setTitle("PantherInspect");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("PantherInspect Login");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Email:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Button signinBtn = createSigninButton(userTextField,pwBox);
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signinBtn);
        grid.add(hbBtn, 1, 4);
        
        Button SUbtn = createSignupButton(primaryStage);
        
        HBox suhbBtn = new HBox(10);
        suhbBtn.setAlignment(Pos.BOTTOM_CENTER);
        suhbBtn.getChildren().add(SUbtn);
        grid.add(suhbBtn, 1, 8);
        
        Label forgotPassword = new Label("Forgot Password?");
        grid.add(forgotPassword, 0, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
