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

/**
 *
 * @author cindyramirez
 */
public class AccountSettings 
{
    // ======== error =============
    SettingsPage settingsPage;
    PantherInspectProject master;
    
    public AccountSettings(PantherInspectProject master, SettingsPage settingsPage) {
        this.master = master;
        this.settingsPage = settingsPage;
    }
    
    public Scene accountPage(Stage primaryStage)
    {
      primaryStage.setTitle("Account Settings:  ");
      GridPane accountHome = new GridPane();
      accountHome.setAlignment(Pos.CENTER);
      accountHome.setHgap(15);
      accountHome.setVgap(15);
      accountHome.setGridLinesVisible(false);
      
       Text scenetitle = new Text("Change email and/or password ");
       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
       accountHome.add(scenetitle, 0, 0, 2, 1);
      
      Label changeEmailLabel = new Label("Change email");
      accountHome.add(changeEmailLabel, 0,1);
      TextField changeEmailField = new TextField();
      accountHome.add(changeEmailField, 1,2);
      
      Label changePasswordLabel = new Label("Change password");
      accountHome.add(changePasswordLabel, 0,2);
      TextField changePasswordField = new TextField();
      accountHome.add(changePasswordField, 1,1);
      
      // Add 1 button to submit changes; 1 to cancel (go back to userhome page?????)
      Button accSettingsCancelButton = new Button("Cancel");
      HBox cancelChangesHB = new HBox(accSettingsCancelButton);
      accSettingsCancelButton.setOnAction(e -> primaryStage.setScene(settingsPage.settingsPage(primaryStage)));
      accountHome.add(cancelChangesHB, 0, 4);
      
      Button accSettingsSaveButton = new Button("Save Changes");
      HBox saveChangesHB = new HBox(accSettingsSaveButton);
      //accSettingsCancelButton.setOnAction(e -> primaryStage.setScene(toUserPage.userpage(primaryStage)));
      accountHome.add(saveChangesHB, 1, 4);
      
      
      
      Scene scene = new Scene(accountHome, 800, 800); 
      
      
      
      return scene;
        
    
    }
    
}
