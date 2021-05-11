/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    String profileImagePath = "";

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
    private Button createSignupButton(Stage primaryStage, TextField email, PasswordField pwBox, TextField username, TextField gradyear, TextField gradsemester, CheckBox gradstatus, ComboBox questions, TextField answer) {
        Button btn = new Button("Sign Up");
        btn.setOnAction((ActionEvent event) -> {
            //run account creation
        if(verifyPassword(pwBox.getText())) {
            try {
            String aid = master.qp.getAccountId(email.getText());
            if (aid.equals("0")) {
                int questionId = questions.getSelectionModel().getSelectedIndex() + 1;
                Thread thr = new Thread(() -> createSignupEvent(email.getText(),username.getText(),pwBox.getText(),gradyear.getText(),gradsemester.getText(),gradstatus.isSelected(),questionId,answer.getText()));
								thr.start();
                //return to initial scene
                master.start(primaryStage);
            }
            else {
                //TODO: setup alert for user interface
                System.out.println("This email is already taken.  Please attempt to reset your password.");
            }

            } catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            ErrorPopup.Pop("Password must be 8 characters or longer and contain numbers and letters.");
        }
        

        });
        return btn;
    }
    public boolean verifyPassword(String pass) {
        //check length
        if(pass.length() < 8)
            return false;
        
        //check for spaces
        if (pass.contains(" "))
            return false;

        
  
        // check for #
        int count = 0;
        for (int i = 0; i < 10; i++) {

            // to convert int to string
            String str = Integer.toString(i);

            if (pass.contains(str)) {
                count += 1;
            }
        }
        if (count < 1) {
            return false;
        };
        
        return true;
    }

     /*
	-------------------------------
	function: createSignupEvent
	-------------------------------
        params: text entries
            String email, username, passwd, gradyear, gradsemester
	purpose:
                hash password
		insert new values in database
	*/
    private void createSignupEvent(String email, String username, String passwd, String gradyear, String gradsemester, Boolean gradstatus, int securityQuestionId, String securityQuestionAnswer) {
      try {
        String aid = master.qp.getUniqueId("accountid", "Account");
        String salt = BCrypt.gensalt(10);
        String hash = BCrypt.hashpw(passwd, salt);
        
        String[] splitAnswer = securityQuestionAnswer.split("\\W+");
        String result = new String();
          for (int i = 0; i < splitAnswer.length; i++) {
              result += splitAnswer[i];
          }
          result = result.toLowerCase();
        String hashedSecurityAnswer = BCrypt.hashpw(result, salt);

        String imgPath;
        if(!profileImagePath.equals("")) {
            File source = new File(profileImagePath);
            String targetPath = master.UPLOAD_PATH + aid + ".jpg";
            System.out.println(targetPath);
            File target = new File(targetPath);
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            imgPath = targetPath;
        } else {
            imgPath = "empty";
        }

        int isGrad = gradstatus ? 1 : 0;

        master.qp.insertAccount(aid, email, hash);
        master.qp.insertProfile(aid, username, imgPath);
        master.qp.insertGraduation(aid, gradyear, gradsemester, String.valueOf(isGrad));
        master.qp.insertSecurity(aid, Integer.toString(securityQuestionId), hashedSecurityAnswer);
      } catch(Exception e) {
          System.out.println("Error: " + e.getMessage());
      }
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
        ImageView imageView = new ImageView();
        imageView.setFitHeight(256);
        imageView.setFitWidth(256);
        imageView.setPreserveRatio(true);
        Label picture = new Label("Picture: ");
        Button btnimage = new Button("Upload Profile Picture");
        HBox picBtn = new HBox(8);
        btnimage.setOnAction((event) ->
        {    // lambda expression
               profileImagePath = image(primaryStage, imageView);
               System.out.println(profileImagePath);
            });
        picBtn.getChildren().addAll(picture, btnimage, imageView);

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
  public String image(Stage primaryStage, ImageView imageView)
  {
        fileChooser.setTitle("Upload Picture");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        String selectedFilePath = selectedFile.getPath();

        try
        {

            FileInputStream stream = new FileInputStream(selectedFilePath);
            Image imagestream = new Image(stream);
            imageView.setImage(imagestream);


        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignupForm.class.getName()).log(Level.SEVERE, null, ex);
            Dialog<String> dialog = new Dialog<String>();
            //Setting the title
            dialog.setTitle("error");
            dialog.showAndWait();
        }
        return selectedFilePath;
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
      
      //security question
      Label securityQuestionLabel = new Label("Security Question: ");
      signup.add(securityQuestionLabel,0,3);
      ComboBox questions = addQuestions();
      questions.getSelectionModel().selectFirst();
      signup.add(questions,1,3);
      
      //security answer
      Label securityAnswerLabel = new Label("Answer: ");
      signup.add(securityAnswerLabel,0,4);
      TextField securityAnswer = new TextField();
      signup.add(securityAnswer,1,4);

      //create grad semester entry
      Label gradSemester = new Label("Graduation Semester: ");
      signup.add(gradSemester,0,5);
      TextField gradsemester = new TextField();
      signup.add(gradsemester, 1,5);

      //create grad year entry
      Label gradYear = new Label("Graduation Year: ");
      signup.add(gradYear,0,6);
      TextField gradyear = new TextField();
      signup.add(gradyear, 1,6);


      Label gradStatus = new Label("Graduated: ");
      signup.add(gradStatus,0,7);
      CheckBox gradstatus = new CheckBox();
      signup.add(gradstatus, 1,7);

      //create picture for profile photo entry
      HBox picBtn = setupPicture(primaryStage);
      signup.add(picBtn, 0, 8); //1,8
      
      //cancel button
      Button logoutButton = new Button("Cancel");
      logoutButton.setOnAction(e -> {
          primaryStage.close();
          master.start(new Stage());
      });
      signup.add(logoutButton, 0, 9);

      //create signup functionality
      Button btn = createSignupButton(primaryStage, email, pwBox, username, gradyear, gradsemester, gradstatus ,questions, securityAnswer);
      //HBox hbBtn = createBox(btn);
      //signup.add(hbBtn,1,8); // btn UI 
      signup.add(btn,1,9); //0,8

      Scene scene = new Scene(signup, 770, 700); //object to return
      return scene;

    }
    
    private ComboBox addQuestions() {
        ResultSet rs = this.master.qp.selectSecurityQuestions();

        ComboBox comboBox = new ComboBox();

        try {
            while(rs.next()) {
                comboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(userHomePage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comboBox;
    }


}
