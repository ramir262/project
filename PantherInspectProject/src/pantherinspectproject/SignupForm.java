/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author cindyramirez
 */
public class SignupForm {
    
    PantherInspectProject master;
     FileChooser fileChooser = new FileChooser();
     
     public SignupForm(PantherInspectProject master) {
         this.master = master;
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
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setGridLinesVisible(false);
        return grid;
    }
    /*
	-------------------------------
	function: createBox
	-------------------------------
	purpose:
		create signup button
	return:
		HBox
	*/
    private HBox createBox(Button btn) {
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        return hbBtn;
    }
    
    /*
	-------------------------------
	function: createSignupButton
	-------------------------------
        params: text entries
            Text Field email, username, gradyear, gradsemester
            PasswordField pwBox
	purpose:
		create signup button functionality
                check if user email already exists
                insert new items to database
	return:
		HBox
	*/
    private Button createSignupButton(Stage primaryStage, TextField email, PasswordField pwBox, TextField username, TextField gradyear, TextField gradsemester) {
    Button btn = new Button("Sign Up");
        btn.setOnAction((ActionEvent event) -> {
        try {
            String aid = master.qp.getAccountId(email.getText());
            System.out.println(aid);
            if (aid.equals("0")) {
                    aid = master.qp.getUniqueId("accountid", "Account");
                    String passwd = pwBox.getText();
                    String salt = BCrypt.gensalt(10);
                    String hash = BCrypt.hashpw(passwd, salt);
                    master.qp.insertAccount(aid, email.getText(), hash);
                    master.qp.insertProfile(aid, username.getText(), "empty");
                    //TODO: set gradstatus to yes/no option
                    master.qp.insertGraduation(aid, gradyear.getText(), gradsemester.getText(), "0");
            }
            else {
                //TODO: setup alert for user interface
                System.out.println("This email is already taken.  Please attempt to reset your password.");
            }
            
            //return to initial scene
            master.start(primaryStage);
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        });
        return btn;
    }
     /*
	-------------------------------
	function: setupPicture
	-------------------------------
	purpose:
		setup sample picture for picture entry
                throw error if file isn't found
	return:
		HBox
	*/
    private HBox setupPicture(Stage primaryStage) {
        HBox picBtn = new HBox(8);
        try
        {
            FileInputStream stream = new FileInputStream(master.UPLOAD_PATH + "profile.png");
            Image imagestream = new Image(stream);
            ImageView imageView = new ImageView(imagestream);

            Label picture = new Label("Picture: ");
            Button btnimage = new Button("Upload Profile Picture");
            btnimage.setOnAction((event) -> 
            {    // lambda expression
                image(primaryStage);
            });
            picBtn.getChildren().addAll(picture, btnimage, imageView);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignupForm.class.getName()).log(Level.SEVERE, null, ex);
            Dialog<String> dialog = new Dialog<String>();
            //Setting the title
            dialog.setTitle("error");
            dialog.showAndWait();
        }
        
        return picBtn;
    }
    
     /*
	-------------------------------
	function: image
	-------------------------------
        params:
                Stage primaryStage
	purpose:
		setup file upload
                enable png and jpg images in file chooser
	*/
    public void image(Stage primaryStage)
      {
           fileChooser.setTitle("Upload Picture");
           fileChooser.getExtensionFilters().addAll(
           new ExtensionFilter("Image Files", "*.png", "*.jpg"));
           File selectedFile = fileChooser.showOpenDialog(primaryStage);   
           
      }
    
    public Scene form(Stage primaryStage) 
    {
      
      //setup grid
      GridPane signup = createGrid();
      
      //create username entry
      Label userName = new Label("Username: ");
      signup.add(userName,0,0);
      TextField username = new TextField();
      signup.add(username, 1, 0);
      
      //create email entry
      Label Email = new Label("Email: ");
      signup.add(Email,0,1);
      TextField email = new TextField();
      signup.add(email, 1,1);
      
      //create password entry
      Label Password = new Label("Password: ");
      signup.add(Password,0,2);
      PasswordField pwBox = new PasswordField();
      signup.add(pwBox, 1,2);
      
      //create grad semester entry
      Label gradSemester = new Label("Graduation Semester: ");
      signup.add(gradSemester,0,3);
      TextField gradsemester = new TextField();
      signup.add(gradsemester, 1,3);
      
      //create grad year entry
      Label gradYear = new Label("Graduation Year: ");
      signup.add(gradYear,0,4);
      TextField gradyear = new TextField();
      signup.add(gradyear, 1,4);
      
      //create grad status entry
      Label gradStatus = new Label("Graduation Status: ");
      signup.add(gradStatus,0,5);
      TextField gradstatus = new TextField();
      signup.add(gradstatus, 1,5);
      
      //create picture for profile photo entry
      HBox picBtn = setupPicture(primaryStage);
      signup.add(picBtn, 1, 5);
      
      //create signup functionality
      Button btn = createSignupButton(primaryStage, email, pwBox, username, gradyear, gradsemester);
      HBox hbBtn = createBox(btn);
      signup.add(hbBtn,1,11);
      
      
      Scene scene = new Scene(signup, 800, 800); //object to return
      return scene;
      
    }
      
    
}
