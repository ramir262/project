/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author cindyramirez
 */
public class viewPost {

  
    
    /* view all professors views in scroll view 
    
    */
    
    PantherInspectProject master;
    public String selectedCourse = "";
     
     public viewPost(PantherInspectProject master)
     {
         this.master = master;
     }
     
     
   
     
     public Scene viewPosting(Stage primaryStage, String selectedCourse)
    {
      this.selectedCourse = selectedCourse;
      
      primaryStage.setTitle("View All Professors  ");
      GridPane viewpost = new GridPane();
      viewpost.setAlignment(Pos.TOP_LEFT);
      viewpost.setHgap(15);
      viewpost.setVgap(15);
      viewpost.setGridLinesVisible(false);
      
      Button backButton = new Button("Back");
      HBox backButtonBox = new HBox(10);
      backButton.setOnAction(e -> primaryStage.setScene(master.getCourseDisplay().display(primaryStage, selectedCourse)));
      backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
      backButtonBox.getChildren().add(backButton);
      viewpost.add(backButtonBox,0,1);
      
        
      
      Scene scene = new Scene(viewpost, 800, 800); 
      
      return scene;
      
    }
    
    
}
