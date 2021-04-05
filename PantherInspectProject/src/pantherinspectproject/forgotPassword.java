/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import pantherinspectproject.PantherInspectProject;
/**
 *
 * @author cindyramirez
 */
public class forgotPassword 
{
    PantherInspectProject master;
    public forgotPassword(PantherInspectProject master) {
         this.master = master;
     }
    
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
      
      
      //get security question
        Label securityQuestion = new Label("*Filler security question*");
        securityQuestion.setVisible(false);
        resetPassword.add(securityQuestion,0,7);

        TextField securityAnswer = new TextField();
        securityAnswer.setVisible(false);
        resetPassword.add(securityAnswer,1,7);

        Button submitQuestion = new Button("Submit");
        submitQuestion.setVisible(false);
        resetPassword.add(submitQuestion,1,8);
        
        Label newPasswordLabel = new Label("New Password");
        newPasswordLabel.setVisible(false);
        resetPassword.add(newPasswordLabel,0,9);
        
        TextField newPassword = new TextField();
        newPassword.setVisible(false);
        resetPassword.add(newPassword,1,9);
        
        Button changePassword = new Button("Reset Password");
        changePassword.setVisible(false);
        resetPassword.add(changePassword, 1, 10);
      
      
        resetButton.setOnAction((ActionEvent e) -> {

            securityQuestion.setVisible(true);
            securityAnswer.setVisible(true);
            submitQuestion.setVisible(true);

        });
        
        submitQuestion.setOnAction((ActionEvent e) -> {
        
            newPasswordLabel.setVisible(true);
            newPassword.setVisible(true);
            changePassword.setVisible(true);
            
        });
        
        changePassword.setOnAction((ActionEvent e) -> {
        
            master.start(primaryStage);
            
        });
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(resetButton);
      resetPassword.add(hbBtn, 1, 6);
      
      Scene scene = new Scene(resetPassword, 800, 800); 
      
      return scene;
    }
    
    private ComboBox addQuestions() {
        ResultSet rs = this.master.qp.selectQuestions();

        ComboBox comboBox = new ComboBox();

        try {
            while(rs.next()) {
                comboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(userHomePage.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comboBox;
    }
    
}
