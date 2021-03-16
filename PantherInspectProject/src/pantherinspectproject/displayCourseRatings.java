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


public class displayCourseRatings {
    searchCoursePage master;
    public displayCourseRatings(searchCoursePage master){
        this.master = master;
    }
    
    public Scene display(Stage primaryStage)
    {
        GridPane displayCourse = new GridPane();
        displayCourse.setAlignment(Pos.CENTER);
        displayCourse.setHgap(15);
        displayCourse.setVgap(15);
        displayCourse.setGridLinesVisible(false);
        Scene scene = new Scene(displayCourse, 800, 800); //object to return
        
        
        return scene;
    }
}
