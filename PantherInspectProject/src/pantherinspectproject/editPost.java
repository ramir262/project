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
public class editPost {
    
    public Scene editPosting(Stage primaryStage)
    {
      primaryStage.setTitle("Edit Post ");
      GridPane editpost = new GridPane();
      editpost.setAlignment(Pos.CENTER);
      editpost.setHgap(15);
      editpost.setVgap(15);
      editpost.setGridLinesVisible(false);
      
      
      Scene scene = new Scene(editpost, 800, 800); 
      
      return scene;
      
    }
    
}
