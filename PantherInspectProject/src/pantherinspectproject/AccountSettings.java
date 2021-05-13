/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;
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
import static javafx.scene.input.KeyCode.H;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pantherinspectproject.SettingsPage;
import pantherinspectproject.userHomePage;
import javafx.scene.control.PasswordField;

/**
 *
 * @author cindyramirez
 */
public class AccountSettings 
{
    
    PantherInspectProject master;
    
    public AccountSettings(PantherInspectProject master) {
        this.master = master;
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
    
    public Scene accountPage(Stage primaryStage)
    {
        
        SettingsPage settingsPage = this.master.getSettings();
        
      primaryStage.setTitle("Account Settings:  ");
      GridPane accountHome = new GridPane();
      accountHome.setAlignment(Pos.TOP_LEFT);
      accountHome.setHgap(15);
      accountHome.setVgap(15);
      accountHome.setGridLinesVisible(false);
      
       Text scenetitle = new Text("Change password ");
       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
       accountHome.add(scenetitle, 0, 0, 3, 4);
      
      /*Label changeEmailLabel = new Label("Change email");
      accountHome.add(changeEmailLabel, 0,1);
      TextField changeEmailField = new TextField();
      accountHome.add(changeEmailField, 1,2);*/
      
      Label changePasswordLabel = new Label("Change password");
      accountHome.add(changePasswordLabel, 0,3);
      PasswordField changePasswordField = new PasswordField();
      accountHome.add(changePasswordField, 1,3);
      
      // Add 1 button to submit changes; 1 to cancel (go back to userhome page?????)
      Button accSettingsCancelButton = new Button("Cancel");
      HBox cancelChangesHB = new HBox(accSettingsCancelButton);
      accSettingsCancelButton.setOnAction(e -> primaryStage.setScene(settingsPage.settingsPage(primaryStage)));
      accountHome.add(cancelChangesHB, 0, 0);
      
      Button accSettingsSaveButton = new Button("Save Changes");
      HBox saveChangesHB = new HBox(accSettingsSaveButton);
      accSettingsSaveButton.setOnAction((ActionEvent e) -> {
          if(verifyPassword(changePasswordField.getText())) {
              try {
                String accountId = master.getAccountId();
                String passwd = changePasswordField.getText();
                String salt = BCrypt.gensalt(10);
                String hash = BCrypt.hashpw(passwd, salt);

                Boolean success = master.qp.updateAccount(accountId, master.getUserEmail(), hash);
                System.out.println(success);
                primaryStage.setScene(settingsPage.settingsPage(primaryStage));
            } catch (Exception exception) {
                System.out.println("Error: " + exception);
                ErrorPopup.Pop("Password must be 8 characters or longer and contain numbers and letters.");
            }
          }
            

        });
      accountHome.add(saveChangesHB, 1, 4);
      
      
      
      Scene scene = new Scene(accountHome, 400, 400); 
      
      
      
      return scene;
        
    
    }
    
}
