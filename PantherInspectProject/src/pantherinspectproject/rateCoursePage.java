/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.ImageIcon;

/**
 *
 * @author cindyramirez
 */
public class rateCoursePage 
{
    // User Interface: Review: for upcoming sprint 
    /*
    - number of stars: combo box to select OR import of a star they click
    - 1 demo question, difficulty of course; Label 
    - ------- with textField  (with limit of characters (200))
    - import course from Page; dropdown of courses and professors 
    
    
    */
    
    public Scene rateCourse(Stage primaryStage)
    {
        InputStream stream = null;
       
     
            primaryStage.setTitle("Rate a Course ");
            GridPane ratePage = new GridPane();
            ratePage.setAlignment(Pos.TOP_CENTER);
            ratePage.setHgap(15);
            ratePage.setVgap(15);
            ratePage.setGridLinesVisible(false);
            Scene scene = new Scene(ratePage, 950, 950);
            Text settingsTitle = new Text("Rate a Chapman Course");
            settingsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            ratePage.add(settingsTitle, 0, 0, 2, 1);
            // ============ Subject Name =================
            // label
            Label courseSubject = new Label("Subject Name");
            ratePage.add(courseSubject, 1,1);
            //comboBox
            ComboBox comboBoxCourses = new ComboBox();
            comboBoxCourses.getItems().add("Computer Science");
            comboBoxCourses.getItems().add("Software Engineeing");
            comboBoxCourses.getItems().add("Data Analytics");
            ratePage.add(comboBoxCourses, 2,1);
            //============================================
            //==================CourseName==============
            //label
            Label courseName = new Label("Course Name: ");
            ratePage.add(courseName, 1,2);
            //comboBox
            ComboBox comboBoxCoursesName = new ComboBox();
            comboBoxCoursesName.getItems().add("230: Computer Science I");
            comboBoxCoursesName.getItems().add("231: Computer Science II");
            comboBoxCoursesName.getItems().add("408: Database Management");
            ratePage.add(comboBoxCoursesName, 2,2);
            //=======================================
            //===========Professor Name================
            Label professorName = new Label("Professor Name");
            ratePage.add(professorName, 1,3);
            ComboBox professorNameComboBox = new ComboBox();
            professorNameComboBox.getItems().add("Elisa Lledo");
            professorNameComboBox.getItems().add("Elizabeth Stevens");
            professorNameComboBox.getItems().add("Erik Linstead");
            ratePage.add(professorNameComboBox,2,3);
            //Would Recommend (star rating)
            System.out.println(System.getProperty("user.dir"));
            try {
                //============ stars ====================
            stream = new FileInputStream("/Users/cindyramirez/project/PantherInspectProject/src/pantherinspectproject/star.jpeg");
            Image starsRate = new Image(stream);
            ImageView iv1 = new ImageView();
            iv1.setImage(starsRate);
            iv1.setFitHeight(50);
            iv1.setFitWidth(50);
            
            
            
            ImageView iv2 = new ImageView();
            iv2.setImage(starsRate);
            iv2.setFitHeight(50);
            iv2.setFitWidth(50);
            
            ImageView iv3 = new ImageView();
            iv3.setImage(starsRate);
            iv3.setFitHeight(50);
            iv3.setFitWidth(50);
            
            ImageView iv4 = new ImageView();
            iv4.setImage(starsRate);
            iv4.setFitHeight(50);
            iv4.setFitWidth(50);
           
            
            ImageView iv5 = new ImageView();
            iv5.setImage(starsRate);
            iv5.setFitHeight(50);
            iv5.setFitWidth(50);
              
            ratePage.add(iv1, 0, 4, 1, 1);
            ratePage.add(iv2, 1, 4, 1, 1);
            ratePage.add(iv3, 2, 4, 1, 1);
            ratePage.add(iv4, 3, 4, 1, 1);
            ratePage.add(iv5, 4, 4, 1, 1);
            
           
            
            
            
            //============= Questions ============
            
            Label assignments = new Label("How many assignments were there? ");
            ratePage.add(assignments, 5, 1);
            TextArea textArea1 = new TextArea();
            VBox vbox1 = new VBox(textArea1);
            ratePage.add(vbox1, 6, 1);
            
            
            Label groupProjects = new Label("How many group projects were there? ");
            ratePage.add(groupProjects, 5, 2);
            TextArea textArea2 = new TextArea();
            VBox vbox2 = new VBox(textArea2);
            ratePage.add(vbox2, 6, 2);
            
            Label Exams = new Label("How many exams were there? ");
            ratePage.add(Exams, 5, 3);
            TextArea textArea3 = new TextArea();
            VBox vbox3 = new VBox(textArea3);
            ratePage.add(vbox3, 6, 3);
            
            Label comments = new Label("Comments/Concerns about the class? ");
            ratePage.add(comments, 5, 4);
            TextArea textArea4 = new TextArea();
            VBox vbox4 = new VBox(textArea4);
            ratePage.add(vbox4, 6, 4);
            
            Button sumbitCourseReview = new Button("Submit Course Review");
            ratePage.add(sumbitCourseReview, 2, 8);
            
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         return scene;
    
    }
    
   
    
    
    
    
     
    
    
    
    
    
}
