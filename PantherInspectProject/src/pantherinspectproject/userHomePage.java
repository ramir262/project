/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import com.sun.javafx.application.LauncherImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 *
 * @author cindyramirez
 */

public class userHomePage
{
   
    public String selectedSubject = "";
    PantherInspectProject master;

    public userHomePage(PantherInspectProject master) {
        this.master = master;
     }



    public Scene userpage(Stage primaryStage)
    {
      rateCoursePage rate = this.master.getRateCoursePage();
      SettingsPage settings = this.master.getSettings();
      ProfilePage profile = this.master.getProfilePage();
      
      

      primaryStage.setTitle("User Home Page ");
      GridPane homePage = new GridPane();
      homePage.setAlignment(Pos.CENTER);
      homePage.setHgap(15);
      homePage.setVgap(15);
      homePage.setGridLinesVisible(false);



      Scene scene = new Scene(homePage, 700, 600);

      //=========== Search Course Label ============
      Label searchCourseLabel = new Label("Search a Chapman Subject:");
      homePage.add(searchCourseLabel, 0, 0);

      Button searchButton = new Button("Search");
      HBox hsearchbox = new HBox(searchButton);

      ComboBox comboBox = addClasses();

      comboBox.setOnAction((event) -> {

            Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem);
            if(selectedItem != null)
            {
                selectedSubject = selectedItem.toString();
                searchCoursePage searchCourse = this.master.getSearchCoursePage();//new searchCoursePage(this.master,this.selectedCourse);
                searchButton.setOnAction(e -> primaryStage.setScene(searchCourse.toSearchCourse(primaryStage,selectedSubject)));

            }
      });

      homePage.add(comboBox, 1,0);

      hsearchbox.setAlignment(Pos.BOTTOM_RIGHT);
      homePage.add(hsearchbox, 2, 0);


      //============ Rate Course Label ====================
      //Label rateCourseLabel = new Label("Rate a Chapman Course:");
      //homePage.add(rateCourseLabel, 0, 2);
      //TextField rateCourseField = new TextField();
      //homePage.add(rateCourseField, 1, 2);

      Label labelOR = new Label("OR");

      homePage.add(labelOR, 1,2);
      Button rateButton = new Button("Rate a Chapman Course");
      HBox hRatebox = new HBox(rateButton);
      rateButton.setOnAction(e -> primaryStage.setScene(master.rateCourse.rateCourse(primaryStage, PantherInspectProject.NEW_POST)));
      homePage.add(hRatebox, 1, 3);
      //=========================================




      Button settingsButton = new Button("Settings");
      HBox hbox = new HBox(settingsButton);
      settingsButton.setOnAction(e -> primaryStage.setScene(settings.settingsPage(primaryStage)));
      homePage.add(hbox, 0, 10);

      Button profileButton = new Button("Profile");
      HBox profileBox = new HBox(profileButton);
      profileButton.setOnAction(e -> primaryStage.setScene(profile.userpage(primaryStage,this.master.getAccountId())));
      homePage.add(profileBox, 0, 11);
      
      //ToDo: Naviagate to PantherInspectProject 
      // LogOut 
      //PantherInspectProject panther = this;
      Button logoutButton = new Button("Log Out");
      logoutButton.setOnAction(e -> {
          primaryStage.close();
          master.start(new Stage());
      });
      homePage.add(logoutButton, 0, 15);



      return scene;

    }

    /*
    ---------------------------
    function: addClasses
    ----------------------------
    params:
    purpose:
        call all distinct course subjects in database
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
