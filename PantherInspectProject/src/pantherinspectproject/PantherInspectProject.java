/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import com.sun.javafx.application.LauncherImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author cindyramirez
 */
public class PantherInspectProject extends Application
{
    public Stage primaryStage;
    
    // pages
    SignupPage signupform;
    HomePage userHome;
    ForgotPasswordPage toReset;
    DeletePostPage delete;
    CourseMenu displayCourse;
    RateCoursePage rateCourse;
    SubmitMenu submitCourseReview;
    SubjectMenu searchCourse;
    ViewPostPage view;
    ProfileSettingsPage profileSetting;
    SettingsMenu settings;
    ProfilePage profile;
    AccountSettingsPage accountSettings;
    

    static final String NEW_POST = "create";

    // Environment variables
    Env env;

    //  Database credentials
    String USER;
    String PASS;

    public String UPLOAD_PATH;

    public String SETUP_FILE;

    //Signed in user info
    private String userEmail;
    private String accountId;
    public String COURSE_FILE;
    public String POST_FILE;


    public Database db;
    public QueryProcessor qp;
    public Connection conn = null;

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Log In");
        setupPages();
        //get environment variables

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

        PasswordField pwBox = createPasswordField(userTextField);
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
        Button forgotPassword = new Button("Forgot Password?");
        forgotPassword.setOnAction((ActionEvent e) -> {

            primaryStage.setScene(toReset.setupPage(primaryStage));

        });
        grid.add(forgotPassword, 0, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //complete setup
        setupScene(primaryStage,grid);
    }
    
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

            getEnvironmentVariables();
            System.out.println(env.ToString());
            USER = env.get("USER");
            PASS = env.get("PASS");
            UPLOAD_PATH = env.get("UPLOAD_PATH");
            SETUP_FILE = env.get("SETUP_FILE");
            COURSE_FILE = env.get("COURSE_FILE");
            POST_FILE = env.get("POST_FILE");

            db = new Database();
            String db_url = db.createURL("localhost","PantherInspect");
            conn = db.createConnection(db_url,USER,PASS);
            try{
                if(!conn.isValid(2)) {
                ErrorPopup.Pop("Could not connect to database.");
                System.exit(0);
                }
            } catch(Exception e) {
                ErrorPopup.Pop("Could not connect to database.");
                System.exit(0);
            }

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

        //generate classes and posts
        int count = qp.selectTableCount("Course");
        if (count == 0) {
            qp.createTables(COURSE_FILE);
            qp.createTables(POST_FILE);
        }
    }
    
     /*
	-------------------------------
	function: createPasswordField
	-------------------------------
        params:
                TextField userTextField : text field for email input
	purpose:
		create password field and related handle
                if enter is pressed, act as if sign in button is pressed
	*/
    private PasswordField createPasswordField(TextField userTextField) {
        PasswordField pwBox = new PasswordField();
        pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if(k.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("signing in..");
                    Boolean check = checkPassword(userTextField.getText(),pwBox.getText());
                    if(check) {
                        primaryStage.setScene(userHome.setupPage(primaryStage));
                    }
                }
            }
        });
        return pwBox;
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
                //Create session
                userEmail = email;
                accountId = qp.getAccountId(userEmail);
                System.out.println("Correct Password!");
                return true;
            } else {
                System.out.println("Incorrect Password!");
                ErrorPopup.Pop("Your username or password was incorrect.");
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            ErrorPopup.Pop("Your username or password was incorrect.");
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
            Boolean check = checkPassword(emailField.getText(),pwField.getText());
            if(check) {
                primaryStage.setScene(userHome.setupPage(primaryStage));
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
                    open signup setupPage
	return:
		Button
	*/
    private Button createSignupButton(Stage primaryStage) {
        Button SUbtn = new Button("Sign Up to get an Account! ");
        SUbtn.setOnAction((ActionEvent e) -> {

            primaryStage.setScene(signupform.setupPage(primaryStage));

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
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        SplashScreenLoader.splashScreen.hide();
        primaryStage.getIcons().add(new Image("file:icon.png"));
    }
    
    /*
	-------------------------------
	function: setupPages
	-------------------------------
        purpose:
		setup pages for access later
	*/
    public void setupPages()
    {
        signupform = new SignupPage(this);
        userHome = new HomePage(this);
        toReset = new ForgotPasswordPage(this);
        delete = new DeletePostPage(this);
        displayCourse = new CourseMenu(this);
        rateCourse = new RateCoursePage(this);
        submitCourseReview =  new SubmitMenu(this);
        searchCourse = new SubjectMenu(this);
        view = new ViewPostPage(this);
        profileSetting = new ProfileSettingsPage(this);
        settings = new SettingsMenu(this);
        profile = new ProfilePage(this);
        accountSettings = new AccountSettingsPage(this);

    }
    
    // get and set account details

    public void setAccountId(String id) {
        accountId = id;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setUserEmail(String email) {
        userEmail = email;
    }
    public String getUserEmail() {
        return userEmail;
    }

    // get environmental variables
    public void getEnvironmentVariables() {
        env = new Env();
    }

    // get pages
    public RateCoursePage getRateCoursePage() {
        return rateCourse;
    }
    
    public DeletePostPage getDeletePost() {
        return delete;
    }

    public HomePage getUserHomePage()
    {
        return userHome;
    }

    public CourseMenu getCourseDisplay() {
        return displayCourse;

    }

    public RateCoursePage getCoursePage() {
        return rateCourse;

    }

    public SubmitMenu getCourseReview()
    {
        return submitCourseReview;
    }
   
    public SubjectMenu getSearchCoursePage()
    {
        return searchCourse;
    }

    public ViewPostPage getViewPost()
    {
        return view;
    }
    public ProfilePage getProfilePage() {
        return profile;
    }
    public ProfileSettingsPage getProfileSettings() {
        return profileSetting;
    }
    public SettingsMenu getSettings() {
        return settings;
    }
    
    public AccountSettingsPage getAccountSettings() {
        return accountSettings;
    }

    public static void main(String[] args) {
      LauncherImpl.launchApplication(PantherInspectProject.class, SplashScreenLoader.class, args);
   }
    
}
