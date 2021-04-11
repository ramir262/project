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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import pantherinspectproject.SubmitCourseReview;
import pantherinspectproject.userHomePage;

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
     boolean highlight = true;
     userHomePage master;
     SubmitCourseReview toSubmit = new SubmitCourseReview();
     //userHomePage toUserHomePage = new userHomePage();
    
    public Scene rateCourse(Stage primaryStage)
    {
        InputStream stream = null;
       
     
            primaryStage.setTitle("Rate a Course ");
            //ScrollPane scrollPane = new ScrollPane();
            GridPane ratePage = new GridPane();
            /*
            scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
            scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
            scrollPane.setPrefSize(500, 500);
            scrollPane.setContent(ratePage);
            */
            ratePage.setAlignment(Pos.TOP_CENTER);
            ratePage.setHgap(15);
            ratePage.setVgap(15);
            ratePage.setGridLinesVisible(false);
            Scene scene = new Scene(ratePage, 900, 950, Color.WHITESMOKE);
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
            
            ImageView view1 = getImageView(starsRate, 50, 50, false);
            ImageView view2 = getImageView(starsRate, 50, 50, false);
            ImageView view3 = getImageView(starsRate, 50, 50, false);
            ImageView view4 = getImageView(starsRate, 50, 50, false);
            ImageView view5 = getImageView(starsRate, 50, 50, false);
            
            Button button = new Button();
            Button button2 = new Button();
            Button button3 = new Button();
            Button button4 = new Button();
            Button button5 = new Button();
            
            button.setGraphic(view1);
            ratePage.add(button,1,4);
            
            button2.setGraphic(view2);
            ratePage.add(button2,2,4);
            
            button3.setGraphic(view3);
            ratePage.add(button3,3,4);
            
            button4.setGraphic(view4);
            ratePage.add(button4,4,4);
            
            button5.setGraphic(view5);
            ratePage.add(button5,5,4);
            
            Label label = new Label("Rate from 1 (low rating) to 5 (high rating): ");
            
            HBox hbox = new HBox(button, button2, button3, button4, button5);
            hbox.getChildren().add(label);
            ratePage.add(hbox,0,4);
            
            List<Button> buttonList = new ArrayList();
            buttonList.add(button);
            buttonList.add(button2);
            buttonList.add(button3);
            buttonList.add(button4);
            buttonList.add(button5);
            
            
            //======== Highlight Stars Rating=================
           
            
            
                   button.setOnAction((ActionEvent e)-> {
                   
                    if(highlight == true)
                    {
                      button.setStyle("-fx-background-color: #ff0000");
                      highlight = false;
                    }
                    
                    else 
                    {
                     highlight = false;
                     
                        button2.setStyle("-fx-background-color: WHITESMOKE ");
                        button3.setStyle("-fx-background-color: WHITESMOKE");
                        button4.setStyle("-fx-background-color: WHITESMOKE");
                        button5.setStyle("-fx-background-color: WHITESMOKE");    
                    }
                    
                 });
                   
                   button2.setOnAction((ActionEvent e)-> {
                   
                    if(highlight == false)
                    {
                      button.setStyle("-fx-background-color: #ff0000");
                      button2.setStyle("-fx-background-color: #ff0000");
                      highlight = true;
                    }
                    
                    else 
                    {
                     highlight = false;
                     
                        //button2.setStyle("-fx-background-color: WHITESMOKE ");
                        button3.setStyle("-fx-background-color: WHITESMOKE");
                        button4.setStyle("-fx-background-color: WHITESMOKE");
                        button5.setStyle("-fx-background-color: WHITESMOKE");    
                    }
                    
                 });
                   
                   button3.setOnAction((ActionEvent e)-> {
                   
                    if(highlight == false)
                    {
                      button.setStyle("-fx-background-color: #ff0000");
                      button2.setStyle("-fx-background-color: #ff0000");
                      button3.setStyle("-fx-background-color: #ff0000");
                      highlight = true;
                    }
                    
                    else 
                    {
                     highlight = false;
                     
                        //button2.setStyle("-fx-background-color: WHITESMOKE ");
                        //button3.setStyle("-fx-background-color: WHITESMOKE");
                        button4.setStyle("-fx-background-color: WHITESMOKE");
                        button5.setStyle("-fx-background-color: WHITESMOKE");    
                    }
                    
                 });
                   
                   button4.setOnAction((ActionEvent e)-> {
                   
                    if(highlight == false)
                    {
                      button.setStyle("-fx-background-color: #ff0000");
                      button2.setStyle("-fx-background-color: #ff0000");
                      button3.setStyle("-fx-background-color: #ff0000");
                      button4.setStyle("-fx-background-color: #ff0000");
                      highlight = true;
                    }
                    
                    else 
                    {
                     highlight = false;
                     
                        //button2.setStyle("-fx-background-color: WHITESMOKE ");
                        //button3.setStyle("-fx-background-color: WHITESMOKE");
                        //button4.setStyle("-fx-background-color: WHITESMOKE");
                        button.setStyle("-fx-background-color: WHITESMOKE");
                        button2.setStyle("-fx-background-color: WHITESMOKE");
                        button3.setStyle("-fx-background-color: WHITESMOKE");
                        button4.setStyle("-fx-background-color: WHITESMOKE");
                        button5.setStyle("-fx-background-color: WHITESMOKE");    
                    }
                    
                 });
                   
                   button5.setOnAction((ActionEvent e)-> {
                   
                    if(highlight == false)
                    {
                      button.setStyle("-fx-background-color: #ff0000");
                      button2.setStyle("-fx-background-color: #ff0000");
                      button3.setStyle("-fx-background-color: #ff0000");
                      button4.setStyle("-fx-background-color: #ff0000");
                      button5.setStyle("-fx-background-color: #ff0000");
                      highlight = true;
                    }
                    
                    else 
                    {
                     highlight = false;
                     button.setStyle("-fx-background-color: WHITESMOKE");
                      button2.setStyle("-fx-background-color: WHITESMOKE");
                      button3.setStyle("-fx-background-color: WHITESMOKE");
                      button4.setStyle("-fx-background-color: WHITESMOKE");
                      button5.setStyle("-fx-background-color: WHITESMOKE");
                     
                        
                    }
                    
                 });
                   
                   
                   
                   
                   
                 
          
            
            
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
            HBox submitCourseHB = new HBox(10);
            //ratePage.add(sumbitCourseReview, 2, 8);
            
            sumbitCourseReview.setOnAction(e -> primaryStage.setScene(toSubmit.submitReview(primaryStage))); 
            submitCourseHB.setAlignment(Pos.BOTTOM_CENTER);
            submitCourseHB.getChildren().add(sumbitCourseReview);
            ratePage.add(submitCourseHB, 2, 8);

            
            Button cancelPost = new Button("Cancel Post");
            HBox cancelHB = new HBox(10);
            ratePage.add(cancelPost, 3, 8);
            //====== workimg on this===============
            /*
            cancelPost.setOnAction(e -> primaryStage.setScene(toUserHomePage.userpage(primaryStage))); 
            cancelHB.setAlignment(Pos.BOTTOM_CENTER);
            cancelHB.getChildren().add(cancelPost);
            ratePage.add(cancelHB, 3, 8);
            */
           
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
    
    
    private ImageView getImageView(Image image, double fitWidth, double fitHeight, boolean preserveRation){
        ImageView view = new ImageView(image);
        view.setFitWidth(fitWidth);
        view.setFitHeight(fitHeight);
        view.setPreserveRatio(preserveRation);
        view.setSmooth(true);
        return view;
        
        
    }
    
   
    
    
    
    
     
    
    
    
    
    
}
