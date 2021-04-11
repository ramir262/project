/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//import pantherinspectproject.editPost;
//import pantherinspectproject.deletePost;

/**
 *
 * @author cindyramirez
 */
public class SubmitCourseReview {
    
    editPost toEditPosting = new editPost();
    deletePost toDeletePosting = new deletePost();
    viewPost toViewPosting = new viewPost();
    
    public Scene submitReview(Stage primaryStage)
    {
      primaryStage.setTitle("Successfully Posted ");
      GridPane submitPage = new GridPane();
      submitPage.setAlignment(Pos.CENTER);
      submitPage.setHgap(15);
      submitPage.setVgap(15);
      submitPage.setGridLinesVisible(false);
      Scene scene = new Scene(submitPage, 800, 600);
      
      
      //============== editPost Button ============
      Button editPost = new Button("Edit Post");
      HBox editPostHB = new HBox(10);
      editPost.setOnAction(e -> primaryStage.setScene(toEditPosting.editPosting(primaryStage)));
      editPostHB.setAlignment(Pos.BOTTOM_RIGHT);
      editPostHB.getChildren().add(editPost);
      submitPage.add(editPost,1,2);
      
      //============ deletePost =====================
      Button deletePost = new Button("Delete Post");
      HBox deletePostHB = new HBox(10);
      deletePost.setOnAction(e -> primaryStage.setScene(toDeletePosting.deletePosting(primaryStage)));
      deletePostHB.setAlignment(Pos.BOTTOM_RIGHT);
      deletePostHB.getChildren().add(deletePost);
      submitPage.add(deletePost,2,2);
      
      
      Button viewPost = new Button("View Post");
      HBox viewPostHB = new HBox(10);
      viewPost.setOnAction(e -> primaryStage.setScene(toViewPosting.viewPosting(primaryStage)));
      viewPostHB.setAlignment(Pos.BOTTOM_RIGHT);
      viewPostHB.getChildren().add(viewPost);
      submitPage.add(viewPost,3,2);
      
      
      
      //======== working on going back to user Home page =================
      Button backToHome = new Button("Home Page");
      submitPage.add(backToHome, 4, 2);
      
        
        return scene;
        
    }
    
}
