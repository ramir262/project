/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author cindyramirez
 */
public class viewPost {
    
     public Scene viewPosting(Stage primaryStage)
    {
      primaryStage.setTitle("View Post ");
      GridPane viewpost = new GridPane();
      viewpost.setAlignment(Pos.CENTER);
      viewpost.setHgap(15);
      viewpost.setVgap(15);
      viewpost.setGridLinesVisible(false);
      
      
      Scene scene = new Scene(viewpost, 800, 800); 
      
      return scene;
      
    }
    
    
}
