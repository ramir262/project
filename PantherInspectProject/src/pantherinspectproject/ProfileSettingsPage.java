/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 *
 * @author cindyramirez
 */
public class ProfileSettingsPage 
{
    
    PantherInspectProject master;
    public ProfileSettingsPage(PantherInspectProject master) {
         this.master = master;
     }
    
    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
    purpose:
        create profile settings page
            allow user to edit profile
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage)
    {
        SettingsMenu settingsPage = this.master.getSettings();
        
        primaryStage.setTitle("Edit Profile Settings ");
        GridPane editprofileSettings = new GridPane();
        editprofileSettings.setAlignment(Pos.TOP_LEFT);
        editprofileSettings.setHgap(15);
        editprofileSettings.setVgap(15);
        editprofileSettings.setGridLinesVisible(false);

        Scene scene = new Scene(editprofileSettings, 400, 400); 

        // edit graduation semester in textfield
        Label eGradSemester = new Label("Edit graduation semester: ");
        editprofileSettings.add(eGradSemester,0,1);
        TextField editGradSemester = new TextField();
        editprofileSettings.add(editGradSemester, 1, 1);

        // edit graduation year in textfield
        Label eGradYear = new Label("Edit graduation year: ");
        editprofileSettings.add(eGradYear,0,2);
        TextField editGradYear = new TextField();
        editprofileSettings.add(editGradYear, 1,2);

        // edit graduation status in checkbox
        Label eGradStatus = new Label("Edit graduation status: ");
        editprofileSettings.add(eGradStatus,0,3);
        CheckBox editGradStatus = new CheckBox();
        editprofileSettings.add(editGradStatus, 1,3);
        
        populateData(editGradSemester,editGradYear,editGradStatus);

        // Cancel Button - user home page error 
        Button cancelButton = new Button("Cancel");
        HBox cancelChangesHB = new HBox(cancelButton);
        
        cancelButton.setOnAction(e -> primaryStage.setScene(settingsPage.setupPage(primaryStage)));
        editprofileSettings.add(cancelChangesHB, 0, 0);

        // Save Changes Button 
        Button saveChangesButton = new Button("Save Changes");
        saveChangesButton.setOnAction((ActionEvent e) -> {
                try {
                    String accountId = master.getAccountId();
                    
                    String year = editGradYear.getText();
                    // check if values valid
                    int testYear = Integer.parseInt(year);
                    String semester = editGradSemester.getText();
                    if ((year.length() > 4) || (semester.replace(" ","").equals(""))) {
                        ErrorPopup.Pop("Error: Graduation year and semester must be valid.");
                    }
                    else {
                        int status = editGradStatus.isSelected() ? 1 : 0;
                        Boolean success = master.qp.updateGraduation(accountId, year, semester, String.valueOf(status));
                        primaryStage.setScene(settingsPage.setupPage(primaryStage));
                    }
                } catch (NumberFormatException exception) { 
                    ErrorPopup.Pop("Error: Year must be digit.");
                } catch (Exception exception) {
                    System.out.println("Error: " + exception);
                    ErrorPopup.Pop(exception.getMessage());
                }

            });
        HBox saveChangesHB = new HBox(10);
        saveChangesHB.setAlignment(Pos.BOTTOM_RIGHT);
        saveChangesHB.getChildren().add(saveChangesButton);
        editprofileSettings.add(saveChangesHB, 1, 4);

        return scene;
    }
    
    /*
    ----------------------------------------
    function: populateData
    ----------------------------------------
    params:
        TextField editGradSemester
        TextField editGradYear
        CheckBox editGradStatus
    purpose:
        populate values with data from database
    */
    private void populateData(TextField editGradSemester, TextField editGradYear, CheckBox editGradStatus) {
        try {
            //Email, Username, Picture, Year, Semester, Graduated
            ResultSet rs = this.master.qp.selectProfileDisplay(this.master.getAccountId());
            rs.next();
            editGradSemester.setText(rs.getString(5));
            editGradYear.setText(rs.getString(4));
            editGradStatus.setSelected(rs.getBoolean(6));
        } catch (SQLException ex) {
            Logger.getLogger(ProfileSettingsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
