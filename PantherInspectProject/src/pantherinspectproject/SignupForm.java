/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author cindyramirez
 */
public class SignupForm {
    
     FileChooser fileChooser = new FileChooser();
   
    
    public Scene form(Stage primaryStage) 
    {
      
        
      GridPane signup = new GridPane();
      signup.setAlignment(Pos.CENTER);
      signup.setHgap(15);
      signup.setVgap(15);
      signup.setGridLinesVisible(false);
      
      Scene scene = new Scene(signup, 800, 800); //object to return
      
      Label userName = new Label("Username: ");
      signup.add(userName,0,0);
      TextField username = new TextField();
      signup.add(username, 1, 0);
      
      Label Email = new Label("Email: ");
      signup.add(Email,0,1);
      TextField email = new TextField();
      signup.add(email, 1,1);
      
      Label gradSemester = new Label("Graduation Semester: ");
      signup.add(gradSemester,0,2);
      TextField gradsemester = new TextField();
      signup.add(gradsemester, 1,2);
      
      Label gradYear = new Label("Graduation Year: ");
      signup.add(gradYear,0,3);
      TextField gradyear = new TextField();
      signup.add(gradyear, 1,3);
      
      
      Label gradStatus = new Label("Graduation Status: ");
      signup.add(gradStatus,0,4);
      TextField gradstatus = new TextField();
      signup.add(gradstatus, 1,4);
      
      
      
      
       try
        {
            
            FileInputStream stream = new FileInputStream("/Users/cindyramirez/NetBeansProjects/PantherInspect/src/pantherinspect/myimage.jpg");
            Image imagestream = new Image(stream);
            ImageView imageView = new ImageView(imagestream);
            
            Label picture = new Label("Picture: ");
            Button btnimage = new Button("Upload Profile Picture");
            HBox picBtn = new HBox(8);
            btnimage.setOnAction((event) -> 
            {    // lambda expression
                   image(primaryStage);
                });
            signup.add(picBtn, 1, 5);
            picBtn.getChildren().addAll(picture, btnimage, imageView);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignupForm.class.getName()).log(Level.SEVERE, null, ex);
            Dialog<String> dialog = new Dialog<String>();
            //Setting the title
            dialog.setTitle("error");
            dialog.showAndWait();
        }
      
     
   
       
      
      Button btn = new Button("Sign Up");
      HBox hbBtn = new HBox(10);
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(btn);
      signup.add(hbBtn,1,11);
      
       
       
      
      return scene;
      

   
      
    }
      public void image(Stage primaryStage)
      {
           fileChooser.setTitle("Upload Picture");
           fileChooser.getExtensionFilters().addAll(
           new ExtensionFilter("Image Files", "*.png", "*.jpg"));
           File selectedFile = fileChooser.showOpenDialog(primaryStage);   
           
      }
    
}
