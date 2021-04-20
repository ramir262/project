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
    
     public Scene viewPosting(Stage primaryStage)
    {
      primaryStage.setTitle("View Post ");
      GridPane viewpost = new GridPane();
      viewpost.setAlignment(Pos.TOP_LEFT);
      viewpost.setHgap(15);
      viewpost.setVgap(15);
      viewpost.setGridLinesVisible(false);
      
      //=========== Seperator ===========
      Separator seperator = new Separator(Orientation.HORIZONTAL);
      Separator seperator2 = new Separator(Orientation.HORIZONTAL);
      
      //=============label and ComnboBox ========
      Label viewLabel = new Label("Viewing Posting Options");
      viewpost.add(viewLabel, 0, 3);
      ComboBox viewOptions = new ComboBox();
      viewOptions.getItems().add("Most Recent");
      viewOptions.getItems().add("High Rating to Low Rating");
      viewOptions.getItems().add("Low Rating to High Rating");
      viewpost.add(viewOptions, 1,3);
      
      Label label1 = new Label("label 1;");
      Label label2 = new Label("label 2");
      Label label3 = new Label("label 3");
       Label label4 = new Label("label 4");
      
      viewpost.add(label1, 1,5);
      viewpost.add(label2, 1,6);
      viewpost.add(label3, 1,7);
      viewpost.add(label4, 1,7);
      VBox vbox = new VBox(label1, seperator, label2);
      VBox vbox2 = new VBox(label3, seperator2, label4);
      //VBox vbox3 = new VBox(label2, seperator2, label3);
      
      viewpost.add(vbox,5,5);
     // viewpost.add(vbox3,10,10);
      
    
      
      
      Scene scene = new Scene(viewpost, 800, 800); 
      
      return scene;
      
    }
    
    
}
