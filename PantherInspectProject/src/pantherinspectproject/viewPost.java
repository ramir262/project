/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
    
    /* NOT USED !!!!!
    Instead of ViewPost have it be directed to rateCoursePage 
    Issue: 
        Boolean already has True/ False 
     Boolean for three options
    */
     public Scene viewPosting(Stage primaryStage)
    {
      primaryStage.setTitle("View Post ");
      GridPane viewpost = new GridPane();
      viewpost.setAlignment(Pos.TOP_LEFT);
      viewpost.setHgap(15);
      viewpost.setVgap(15);
      viewpost.setGridLinesVisible(false);
      
      
      Scene scene = new Scene(viewpost, 800, 800); 
      
      return scene;
      
    }
    
    
}
