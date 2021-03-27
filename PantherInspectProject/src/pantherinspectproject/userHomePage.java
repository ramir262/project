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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.H;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pantherinspectproject.PantherInspectProject;
import pantherinspectproject.SettingsPage;
import pantherinspectproject.profileSettings;
import pantherinspectproject.AccountSettings;
import pantherinspectproject.searchCoursePage;
/**
 *
 * @author cindyramirez
 */

public class userHomePage 
{
    SettingsPage settings = new SettingsPage();
    profileSettings profilesetting = new profileSettings();
    searchCoursePage searchCourse = new searchCoursePage(this);
    
    public String selectedCourse = "";
    private PantherInspectProject master;
    
    public userHomePage(PantherInspectProject master) {
         this.master = master;
     }
    public Scene userpage(Stage primaryStage)
    {
      primaryStage.setTitle("User Home Page ");
      GridPane homePage = new GridPane();
      homePage.setAlignment(Pos.CENTER);
      homePage.setHgap(15);
      homePage.setVgap(15);
      homePage.setGridLinesVisible(false);
      

      
      Scene scene = new Scene(homePage, 800, 800); 
      
      //=========== Search Course Label ============
      Label searchCourseLabel = new Label("Search a Chapman Subject:");
      homePage.add(searchCourseLabel, 0, 0);
      
      ComboBox comboBox = addClasses();
      homePage.add(comboBox, 1,0);
      
      Button searchButton = new Button("Search");
      HBox hsearchbox = new HBox(searchButton);
     
      comboBox.setOnAction((event) -> {
      searchButton.setOnAction(e -> primaryStage.setScene(searchCourse.toSearchCourse(primaryStage)));
      int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
      Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
      if(selectedItem != null)
      {
          selectedCourse = selectedItem.toString();
      }
      });
      
      
      hsearchbox.setAlignment(Pos.BOTTOM_RIGHT);
      homePage.add(hsearchbox, 2, 0);
       
      
      //============ Rate Course Label ====================
      Label rateCourseLabel = new Label("Rate a Chapman Course:");
      homePage.add(rateCourseLabel, 0, 2);
      TextField rateCourseField = new TextField();
      homePage.add(rateCourseField, 1, 2);
      
      Button rateButton = new Button("Search");
      HBox hRatebox = new HBox(rateButton);
      homePage.add(hRatebox, 2, 2);
      //=========================================
      
     
      
      
      Button settingsButton = new Button("Settings");
      HBox hbox = new HBox(settingsButton);
      settingsButton.setOnAction(e -> primaryStage.setScene(settings.settingsPage(primaryStage)));
      homePage.add(hbox, 0, 10);
      
  
      
      return scene;
     
    }
    
    /* 
    ---------------------------
    function: addClasses
    ----------------------------
    params:
    purpose:
        call all classes in database
        dynamically generate combo box items
    return:
        ComboBox: contains all classes
    
    */
    private ComboBox addClasses() {
        ResultSet rs = this.master.qp.selectSubjects();
        
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