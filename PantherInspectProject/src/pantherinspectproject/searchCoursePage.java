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
import javafx.scene.control.ChoiceBox;
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


/**
 *
 * @author cindyramirez
 */
public class searchCoursePage 
{
    userHomePage master;
    displayCourseRatings courseRatings = new displayCourseRatings(this);
    
    public searchCoursePage(userHomePage master)
    {
        this.master = master;
    }
   public Scene toSearchCourse(Stage primaryStage)
    {
      primaryStage.setTitle("Search Courses ");
      GridPane coursesPage = new GridPane();
      coursesPage.setAlignment(Pos.CENTER);
      coursesPage.setHgap(15);
      coursesPage.setVgap(15);
      coursesPage.setGridLinesVisible(false);
      
      Label label = new Label(String.format("Welcome: %s", this.master.selectedCourse));
      coursesButton("230", "Computer Science I", coursesPage, primaryStage);
      coursesPage.add(label, 0,0);
      
  
           

      
      Scene scene = new Scene(coursesPage, 800, 800); 
      
      
      
      
      
      return scene;
    }
    
   public void coursesButton(String courseNum, String courseName, GridPane grid, Stage primaryStage)
   {
        Button btn = new Button((String.format("%s: %s", courseNum, courseName)));
        HBox hbBtn = new HBox(10);
        btn.setOnAction(e -> primaryStage.setScene(courseRatings.display(primaryStage)));
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
   }
}
