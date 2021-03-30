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
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pantherinspectproject.SettingsPage;
import pantherinspectproject.userHomePage;
import pantherinspectproject.PantherInspectProject;
/**
 *
 * @author cindyramirez
 */
public class profileSettings 
{
    SettingsPage settingsPage;
    PantherInspectProject master;
    public profileSettings(PantherInspectProject master, SettingsPage settingsPage) {
         this.master = master;
         this.settingsPage = settingsPage;
     }
    
    
    public Scene editProfile(Stage primaryStage)
    {
      primaryStage.setTitle("Edit Profile Settings ");
      GridPane editprofileSettings = new GridPane();
      editprofileSettings.setAlignment(Pos.CENTER);
      editprofileSettings.setHgap(15);
      editprofileSettings.setVgap(15);
      editprofileSettings.setGridLinesVisible(false);
      
      
      Scene scene = new Scene(editprofileSettings, 800, 800); 
      
      //Text profileSettings = new Text("Profile Settings");
      //profileSettings.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
      //editprofileSettings.add(profileSettings, 0, 0, 9, 2);
      Label eGradSemester = new Label("Edit graduation semester: ");
      editprofileSettings.add(eGradSemester,0,1);
      TextField editGradSemester = new TextField();
      editprofileSettings.add(editGradSemester, 1, 1);
      
      
      Label eGradYear = new Label("Edit graduation year: ");
      editprofileSettings.add(eGradYear,0,2);
      TextField editGradYear = new TextField();
      editprofileSettings.add(editGradYear, 1,2);
      
      Label eGradStatus = new Label("Edit graduation status: ");
      editprofileSettings.add(eGradStatus,0,3);
      CheckBox editGradStatus = new CheckBox();
      editprofileSettings.add(editGradStatus, 1,3);
      
      // Cancel Button - user home page error 
      Button cancelButton = new Button("Cancel");
      HBox cancelChangesHB = new HBox(cancelButton);
      //========== error on line 72 =================
      cancelButton.setOnAction(e -> primaryStage.setScene(settingsPage.settingsPage(primaryStage)));
      //cancelChangesHB.setAlignment(Pos.BOTTOM_RIGHT);
      //cancelChangesHB.getChildren().add(cancelButton);
      editprofileSettings.add(cancelChangesHB, 0, 4);
      
      // Save Changes Button 
      Button saveChangesButton = new Button("Save Changes");
      saveChangesButton.setOnAction((ActionEvent e) -> {
            try {
                String accountId = master.getAccountId();
                String year = editGradYear.getText();
                String semester = editGradSemester.getText();
                int status = editGradStatus.isSelected()  ? 1 : 0;

                Boolean success = master.qp.updateGraduation(accountId, year, semester, String.valueOf(status));
                System.out.println(success);
                primaryStage.setScene(settingsPage.settingsPage(primaryStage));
            } catch (Exception exception) {
                System.out.println("Error: " + exception);
            }

        });
      HBox saveChangesHB = new HBox(10);
      saveChangesHB.setAlignment(Pos.BOTTOM_RIGHT);
      saveChangesHB.getChildren().add(saveChangesButton);
      editprofileSettings.add(saveChangesHB, 1, 4);
      
      return scene;
    }
    
}
