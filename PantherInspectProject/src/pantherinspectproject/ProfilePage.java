/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author allis
 */
public class ProfilePage {
    PantherInspectProject master;
    public ProfilePage(PantherInspectProject master) {
        this.master = master;
    }
    
    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
        String accountId
    purpose:
        create profile page
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage,String accountId)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setGridLinesVisible(false);
        Scene scene = new Scene(grid, 800, 800);
        
        Button backBtn = new Button("Profile Page");
        backBtn.setOnAction(e -> primaryStage.setScene(this.master.userHome.setupPage(primaryStage)));
        grid.add(backBtn, 0, 0);
        
        ResultSet rs = this.master.qp.selectProfileDisplay(accountId);
        try {
            rs.next();
            //ResultSet = Email, Username, Picture, Year, Semester, Graduated
            // username
            Label usernameLbl = new Label("Username: ");
            usernameLbl.setStyle("-fx-font-weight: bold");
            Label userLbl = new Label(rs.getString(2));
            HBox userBox = new HBox(usernameLbl,userLbl);
            grid.add(userBox,0,1);
            
            // profile picture
            ImageView imageView = new ImageView();
            imageView.setFitHeight(256);
            imageView.setFitWidth(256);
            imageView.setPreserveRatio(true);
            String path = rs.getString(3);
            if (!path.equals("empty")) {
                try
                {
                    System.out.println(path);
                    FileInputStream stream = new FileInputStream(path);
                    Image imagestream = new Image(stream);
                    imageView.setImage(imagestream);
                    grid.add(imageView,0,3);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SignupPage.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
            }
            
            // graduation status
            Label graduatedLbl = new Label("Graduation Status: ");
            graduatedLbl.setStyle("-fx-font-weight: bold");
            String graduated = "True";
            if (rs.getString(6).equals("0")) {
                graduated = "False";
            }
            Label gradLbl = new Label(graduated);
            HBox gradBox = new HBox(graduatedLbl,gradLbl);
            grid.add(gradBox, 0, 2);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return scene;
    }
}
