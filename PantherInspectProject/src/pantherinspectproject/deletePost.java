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
public class deletePost {
    
    public Scene deletePosting(Stage primaryStage)
    {
      primaryStage.setTitle("Delete Post ");
      GridPane deletepost = new GridPane();
      deletepost.setAlignment(Pos.CENTER);
      deletepost.setHgap(15);
      deletepost.setVgap(15);
      deletepost.setGridLinesVisible(false);
      
      
      Scene scene = new Scene(deletepost, 800, 800); 
      
      return scene;
      
    }
    
}
