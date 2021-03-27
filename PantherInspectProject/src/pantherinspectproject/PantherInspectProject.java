/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import com.sun.javafx.application.LauncherImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.H;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pantherinspectproject.SignupForm;
import pantherinspectproject.userHomePage;
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
    //CoursePage coursePage = new CoursePage(this);
    userHomePage userHome = new userHomePage(this);
    forgotPassword toReset = new forgotPassword();

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";//"2367";
    
    public static final String UPLOAD_PATH = "//localhost/D$/Downloads/images/";

    public static final String SETUP_FILE = "tables.sql";
    public static final String COURSE_FILE = "courses.sql";

    public Database db;
    public QueryProcessor qp;
    public Connection conn = null;

    /*
	-------------------------------
	function: setupDB
	-------------------------------
	purpose:
		set up database connection
                set up query processor
                generate tables if they don't yet exist
	*/
    private void setupDB() {
        if (conn == null) {
            //setup database
            db = new Database();
            String db_url = db.createURL("localhost","PantherInspect");
            conn = db.createConnection(db_url,USER,PASS);
            qp = new QueryProcessor(conn);

            //run create tables via thread
            Thread thr = new Thread(() -> runSQL());
            thr.start();
        }
    }

      /*
	-------------------------------
	function: runSQL
	-------------------------------
	purpose:
		setup tables
                add demo data
	*/
    private void runSQL() {
        qp.createTables(SETUP_FILE);

        /*generate demo info*/

        //generate classes
        int count = qp.selectTableCount("Course");
        if (count == 0) {
            qp.createTables(COURSE_FILE);
        }

    }

    /*
	-------------------------------
	function: checkPassword
	-------------------------------
        params:
            String email, password (from textfield and passwordfield)
        purpose:
		if email exists in accounts, retrieve hash
                check entered password against existing hash
                generate alert if password does not match
	*/
    private Boolean checkPassword(String email, String password) {
        try {

            ResultSet rs = qp.selectAccountHash(email);
            rs.next();
            String hashedPass = rs.getString(1);
            if(BCrypt.checkpw(password, hashedPass)) {
                System.out.println("Correct Password!");
                return true;
            } else {
                System.out.println("Incorrect Password!");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An Error has Occurred.");
                alert.setContentText("Your username or password was incorrect.");

                alert.showAndWait();
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    /*
	-------------------------------
	function: createSigninButton
	-------------------------------
        params: entry fields where strings are collected at time of button click
                TextField emailField
                PasswordField pwField
	purpose:
                create button with action:
                    check if email and password exist in database
                    catch errors
	return:
		Button
	*/
    private Button createSigninButton(TextField emailField, PasswordField pwField, Stage primaryStage) {
        Button btn = new Button("Sign in");
        btn.setOnAction((ActionEvent event) -> {

            System.out.println("signing in..");

            //temp fix: not running this in a thread as it should be locking and should freeze gui
            //run check password as thread
            //Thread thr = new Thread(() -> checkPassword(emailField.getText(),pwField.getText()));
            //thr.start();
            Boolean check = checkPassword(emailField.getText(),pwField.getText());
            if(check) {
                primaryStage.setScene(userHome.userpage(primaryStage));
            }

        });
        return btn;
    }

     /*
	-------------------------------
	function: createSignupButton
	-------------------------------
        params:
                Stage primaryStage
	purpose:
		create signup button with action:
                    open signup form
	return:
		Button
	*/
    private Button createSignupButton(Stage primaryStage) {
        Button SUbtn = new Button("Sign Up to get an Account! ");
        SUbtn.setOnAction((ActionEvent e) -> {

            primaryStage.setScene(signupform.form(primaryStage));

        });
        return SUbtn;
    }
    /*
	-------------------------------
	function: createGrid
	-------------------------------
	purpose:
		create grid for GUI
	return:
		GridPane
	*/
    private HBox createBox(Button btn, Pos position) {
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(position);
        hbBtn.getChildren().add(btn);
        return hbBtn;
    }
    /*
	-------------------------------
	function: createGrid
	-------------------------------
	purpose:
		create grid for GUI
	return:
		GridPane
	*/
    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        return grid;
    }
    /*
	-------------------------------
	function: createTitle
	-------------------------------
        params:
                Stage primaryStage
                String appTitle
        purpose:
		create title for GUI
	return:
		GridPane
	*/
    private Text createTitle(Stage primaryStage, String appTitle) {
        primaryStage.setTitle(appTitle);
        Text scenetitle = new Text(String.format("%s Login",appTitle));
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        return scenetitle;
    }

    /*
	-------------------------------
	function: setupScene
	-------------------------------
        params:
                Stage primaryStage
                GridPane grid
        purpose:
		create title for GUI
	return:
		GridPane
	*/
    private void setupScene(Stage primaryStage, GridPane grid) {
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
        SplashScreenLoader.splashScreen.hide();
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        
        //set up database and query processor
        setupDB();

        //set up GUI
        GridPane grid = createGrid();

        //create title
        Text scenetitle = createTitle(primaryStage,"PantherInspect");
        grid.add(scenetitle, 0, 0, 2, 1);

        //create email entry
        Label userName = new Label("Email:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        //create password entry
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        //create signin button
        Button signinBtn = createSigninButton(userTextField,pwBox,primaryStage);
        HBox hbBtn = createBox(signinBtn,Pos.BOTTOM_RIGHT);
        grid.add(hbBtn, 1, 4);

        //create signup button
        Button SUbtn = createSignupButton(primaryStage);
        HBox suhbBtn = createBox(SUbtn,Pos.BOTTOM_CENTER);
        grid.add(suhbBtn, 1, 8);

        //forgot password label
        //TODO: forgot password button + logic
        Button forgotPassword = new Button("Forgot Password?");
         HBox forgotPasswordHB = new HBox(10);

         forgotPassword.setOnAction(e -> primaryStage.setScene(toReset.toResetPassword(primaryStage))); 
         forgotPasswordHB.setAlignment(Pos.BOTTOM_CENTER);
         forgotPasswordHB.getChildren().add(forgotPassword);
         grid.add(forgotPasswordHB, 0, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //complete setup
        setupScene(primaryStage,grid);
    }



    public static void main(String[] args) {
      LauncherImpl.launchApplication(PantherInspectProject.class, SplashScreenLoader.class, args);
   }


}
