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
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 *
 * @author cindyramirez
 */
public class ForgotPasswordPage 
{
    PantherInspectProject master;
    private String hash;
    public ForgotPasswordPage(PantherInspectProject master) {
         this.master = master;
     }
    
    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
    purpose:
        create page to reset password
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage)
    {
        primaryStage.setTitle("Reset password ");
        GridPane resetPassword = new GridPane();
        resetPassword.setAlignment(Pos.CENTER);
        resetPassword.setHgap(15);
        resetPassword.setVgap(15);
        resetPassword.setGridLinesVisible(false);

        //cancel button
        Button logoutButton = new Button("Cancel");
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            master.start(new Stage());
        });
        resetPassword.add(logoutButton, 0, 0);

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
        
        PasswordField newPassword = new PasswordField();
        newPassword.setVisible(false);
        resetPassword.add(newPassword,1,9);
        
        Button changePassword = new Button("Reset Password");
        changePassword.setVisible(false);
        
        resetPassword.add(changePassword, 1, 10);
        resetButton.setOnAction((ActionEvent e) -> {
            int accountId = getAccountId(emailtoResetpassword.getText());
            String question;
            if(accountId > 0) {
                System.out.println(accountId);
                ResultSet rs = this.master.qp.selectSecurityAnswer(Integer.toString(accountId));
                //String[] questions = new ComboBox();

                try {
                    while(rs.next()) {
                        //comboBox.getItems().add(rs.getString(1));
                        question = rs.getString(1);
                        hash = rs.getString(2); 
                        securityQuestion.setText(question);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                securityQuestion.setVisible(true);
                securityAnswer.setVisible(true);
                submitQuestion.setVisible(true);
            } else {
                ErrorPopup.Pop("No account exists.");
            }
            

        });
        
        submitQuestion.setOnAction((ActionEvent e) -> {
            String answer = securityAnswer.getText();
            String salt = BCrypt.gensalt(10);

            String[] splitAnswer = answer.split("\\W+");
            String result = new String();
              for (int i = 0; i < splitAnswer.length; i++) {
                  result += splitAnswer[i];
              }
            result = result.toLowerCase();
            if(BCrypt.checkpw(result, hash)) {
                newPasswordLabel.setVisible(true);
                newPassword.setVisible(true);
                changePassword.setVisible(true);
            } else {
                ErrorPopup.Pop("Incorrect answer.");
            }
            
            
        });
        
        changePassword.setOnAction((ActionEvent e) -> {
            if(verifyPassword(newPassword.getText())) {
               int accountId = getAccountId(emailtoResetpassword.getText());
                String passwd = newPassword.getText();
                String salt = BCrypt.gensalt(10);
                String hash = BCrypt.hashpw(passwd, salt);
                Boolean succ = master.qp.updateAccount(Integer.toString(accountId),emailtoResetpassword.getText(),hash);
                if(succ) {
                master.start(primaryStage);
                } 
            } else {
                ErrorPopup.Pop("Password must be 8 characters or longer and contain numbers and letters.");
            }
            

        });
      hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
      hbBtn.getChildren().add(resetButton);
      resetPassword.add(hbBtn, 1, 6);
      
      Scene scene = new Scene(resetPassword, 800, 800); 
      
      return scene;
    }
    
    /*
    ----------------------------------------
    function: verifyPassword
    ----------------------------------------
    params:
        String pass : new password
    purpose:
        verify that new password is valid
            must contain >= 8 characters
            must not contain spaces
            must contain at least 1 number
    return:
        boolean :
            if valid, return true
            if invalid, return false
    
    */
    public boolean verifyPassword(String pass) {
        //check length
        if(pass.length() < 8)
            return false;
        
        //check for spaces
        if (pass.contains(" "))
            return false;

        // check for #
        int count = 0;
        for (int i = 0; i < 10; i++) {

            // to convert int to string
            String str = Integer.toString(i);

            if (pass.contains(str)) {
                count += 1;
            }
        }
        if (count < 1) {
            return false;
        }
        
        return true;
    }
    
    
    private int getAccountId(String email) {
        String id = this.master.qp.getAccountId(email);

        return Integer.parseInt(id);
    }
    
}