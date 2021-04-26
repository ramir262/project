/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pantherinspectproject.userHomePage;

/**
 *
 * @author cindyramirez
 */
public class deletePost {
    
    userHomePage user;
    
    
    public Scene deletePosting(Stage primaryStage, PantherInspectProject toRootPage)
    {
      
      primaryStage.setTitle("Delete Post ");
      GridPane deletepost = new GridPane();
      deletepost.setAlignment(Pos.CENTER);
      deletepost.setHgap(15);
      deletepost.setVgap(15);
      deletepost.setGridLinesVisible(false);
      
      Text deleteTitle = new Text("Posting Successfully Deleted!");
      deleteTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
      deletepost.add(deleteTitle, 1,1,2,1);
      
      user = new userHomePage(toRootPage);
      
      
      Button backToHomePage = new Button("Home Page");
      HBox backToHomePageHB = new HBox(10);
      backToHomePage.setOnAction(e -> primaryStage.setScene(user.userpage(primaryStage)));
      backToHomePageHB.setAlignment(Pos.CENTER);
      backToHomePageHB.getChildren().add(backToHomePage);
      deletepost.add(backToHomePage,2,2);
      
      Scene scene = new Scene(deletepost, 800, 800); 
      
      return scene;
      
    }
    
}
