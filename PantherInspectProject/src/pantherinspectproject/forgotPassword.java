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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.H;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author cindyramirez
 */
public class forgotPassword 
{
    public Scene toResetPassword(Stage primaryStage)
    {
      primaryStage.setTitle("Reset password ");
      GridPane resetPassword = new GridPane();
      resetPassword.setAlignment(Pos.CENTER);
      resetPassword.setHgap(15);
      resetPassword.setVgap(15);
      resetPassword.setGridLinesVisible(false);
      
      Label label = new Label("Enter email to reset password:");
      resetPassword.add(label, 0,4);
      
      TextField emailtoResetpassword = new TextField();
      resetPassword.add(emailtoResetpassword, 1, 4);
      
      Button resetButton = new Button("reset password");
      HBox hbBtn = new HBox(10);
      //resetButton.setOnAction(e -> primaryStage.setScene(userHome.userpage(primaryStage)));
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(resetButton);
      resetPassword.add(hbBtn, 1, 6);
      
      Scene scene = new Scene(resetPassword, 800, 800); 
      
      return scene;
    }
    
    
}
