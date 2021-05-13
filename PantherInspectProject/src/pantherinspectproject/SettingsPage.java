/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class SettingsPage 
{
    
    PantherInspectProject master;
    public SettingsPage(PantherInspectProject master) {
         this.master = master;
     }
    
    public Scene settingsPage(Stage primaryStage)
    {
        userHomePage homePage = this.master.getUserHomePage();
        profileSettings editProfileSetting = this.master.getProfileSettings();//new profileSettings(master);
        AccountSettings accountSetting = this.master.getAccountSettings();
        
      primaryStage.setTitle("Settings Page ");
      GridPane settings = new GridPane();
      settings.setAlignment(Pos.TOP_LEFT);
      settings.setHgap(15);
      settings.setVgap(15);
      settings.setGridLinesVisible(false);
      
      
      Scene scene = new Scene(settings, 400, 400); 
      
      Text settingsTitle = new Text("Settings");
      settingsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
      settings.add(settingsTitle, 0, 0, 4, 5);
      
      // 3 buttons for three pages
      // Button to edit profile settings - grad status, year, semester
      Button profileSettings = new Button("Edit Profile:");
      HBox profileSettingsHB = new HBox(10);
      profileSettings.setOnAction(e -> primaryStage.setScene(editProfileSetting.editProfile(primaryStage)));
      profileSettingsHB.setAlignment(Pos.BOTTOM_RIGHT);
      profileSettingsHB.getChildren().add(profileSettings);
      settings.add(profileSettingsHB, 1, 4);
      
      // Button to edit account settings - email and password
      
      Button accountSettingsButton = new Button("Edit Account Settings");
      HBox accSettingshbBtn = new HBox(10);
      accountSettingsButton.setOnAction(e -> primaryStage.setScene(accountSetting.accountPage(primaryStage)));
      accSettingshbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      accSettingshbBtn.getChildren().add(accountSettingsButton);
      settings.add(accSettingshbBtn,0,4);
      
      // Back Button
      
      Button backButton = new Button("Back");
      HBox backButtonBox = new HBox(10);
      backButton.setOnAction(e -> primaryStage.setScene(homePage.userpage(primaryStage)));
      backButtonBox.setAlignment(Pos.TOP_LEFT);
      backButtonBox.getChildren().add(backButton);
      settings.add(backButtonBox,0,0);
      
      
    
      
      
      
      
      
      
      
      
    
      return scene;
    }

}
