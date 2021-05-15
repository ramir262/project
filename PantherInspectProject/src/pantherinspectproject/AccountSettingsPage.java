/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

/**
 *
 * @author cindyramirez
 */
public class AccountSettingsPage 
{
    
    PantherInspectProject master;
    
    public AccountSettingsPage(PantherInspectProject master) {
        this.master = master;
    }
    
    
    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
    purpose:
        generate account settings page
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage)
    {
        
        SettingsPage settingsPage = this.master.getSettings();
        
        primaryStage.setTitle("Account Settings:  ");
        GridPane accountHome = new GridPane();
        accountHome.setAlignment(Pos.CENTER);
        accountHome.setHgap(15);
        accountHome.setVgap(15);
        accountHome.setGridLinesVisible(false);

        Text scenetitle = new Text("Change password ");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        accountHome.add(scenetitle, 0, 0, 2, 1);
      
        Label changePasswordLabel = new Label("Change password");
        accountHome.add(changePasswordLabel, 0,2);
        PasswordField changePasswordField = new PasswordField();
        accountHome.add(changePasswordField, 1,1);

        // Add 1 button to submit changes; 1 to cancel (go back to userhome page?????)
        Button accSettingsCancelButton = new Button("Cancel");
        HBox cancelChangesHB = new HBox(accSettingsCancelButton);
        accSettingsCancelButton.setOnAction(e -> primaryStage.setScene(settingsPage.setupPage(primaryStage)));
        accountHome.add(cancelChangesHB, 0, 4);

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
                  primaryStage.setScene(settingsPage.setupPage(primaryStage));
              } catch (Exception exception) {
                  System.out.println("Error: " + exception);
                  ErrorPopup.Pop("Password must be 8 characters or longer and contain numbers and letters.");
              }
            }
          });
        
        accountHome.add(saveChangesHB, 1, 4);
        
        Scene scene = new Scene(accountHome, 800, 800); 
        return scene;
    }
    
    /*
    ----------------------------------------
    function: verifyPassword
    ----------------------------------------
    params:
        String pass : new password
    purpose:
        verify that new password is valid
            must contain >= 8 characters
            must not contain spaces
            must contain at least 1 number
    return:
        boolean :
            if valid, return true
            if invalid, return false
    
    */
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
}
