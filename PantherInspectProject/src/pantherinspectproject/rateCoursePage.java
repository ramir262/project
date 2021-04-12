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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     SubmitCourseReview toSubmit = new SubmitCourseReview();
     PantherInspectProject master;
     ComboBox comboBoxCourse;
     ComboBox comboBoxProfessor;
     //userHomePage toUserHomePage = new userHomePage();
     public rateCoursePage(PantherInspectProject master) {
        this.master = master;
     }
     
    
    public Scene rateCourse(Stage primaryStage) 
    {
        InputStream stream = null;
       
     
            primaryStage.setTitle("Rate a Course ");
            //ScrollPane scrollPane = new ScrollPane();
            GridPane grid = new GridPane();
            GridPane ratePage = new GridPane();
            grid.add(ratePage, 1, 1);
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
            Scene scene = new Scene(grid, 850, 600, Color.WHITESMOKE);
            Text settingsTitle = new Text("Rate a Chapman Course");
            settingsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            ratePage.add(settingsTitle, 0, 0, 2, 1);
            
            //comboBox for subject, course, and professor
            createSubjectBox(ratePage);
            
            //Would Recommend (star rating)
            System.out.println(System.getProperty("user.dir"));
            try {
                //============ stars ====================
            stream = new FileInputStream("star.jpeg");
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
            ratePage.add(label,0,7);
            HBox hbox = new HBox(button, button2, button3, button4, button5);
            ratePage.add(hbox,0,8);
            
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
            ResultSet rs = this.master.qp.selectPostQuestions();
            int loc = 1;
            GridPane qGrid = new GridPane();
            try {
                while (rs.next()) {
                    createQuestion(qGrid,rs.getString(1),loc);
                    loc = loc+2;
                }
            } catch (SQLException ex) {
                Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            ScrollPane scroll = addScrollPane(qGrid);
            grid.add(scroll, 2, 1);
            
            Button sumbitCourseReview = new Button("Submit Course Review");
            HBox submitCourseHB = new HBox(10);
            //ratePage.add(sumbitCourseReview, 2, 8);
            
            sumbitCourseReview.setOnAction(e -> primaryStage.setScene(toSubmit.submitReview(primaryStage))); 
            submitCourseHB.getChildren().add(sumbitCourseReview);
            ratePage.add(submitCourseHB, 0, 9);

            
            Button cancelPost = new Button("Cancel Post");
            HBox cancelHB = new HBox(10);
            ratePage.add(cancelPost, 0, 10);
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
    
    private void createSubjectBox(GridPane grid) {
        // ============ Subject Name =================
        // label
        Label courseSubject = new Label("Subject Name");
        grid.add(courseSubject, 0,1);
        Label courseName = new Label("Course Name: ");
        grid.add(courseName, 0,3);
        comboBoxCourse = new ComboBox();
        grid.add(comboBoxCourse, 0,4);
        comboBoxProfessor = new ComboBox();
        grid.add(comboBoxProfessor, 0,6);
        
        Label professorName = new Label("Professor Name: ");
        grid.add(professorName, 0,5);
        ComboBox comboBoxSubject = new ComboBox();
        ResultSet subjectRs = this.master.qp.selectSubjects();
            try {
                while (subjectRs.next()){
                    comboBoxSubject.getItems().add(subjectRs.getString(1));
                }
                comboBoxSubject.setOnAction((event) -> {
                int selectedIndex = comboBoxSubject.getSelectionModel().getSelectedIndex();
                Object selectedItem = comboBoxSubject.getSelectionModel().getSelectedItem();
                if(selectedItem != null)
                {
                    //grab classes
                    createCourseBox(grid, selectedItem.toString());
                }
            });
            } catch (SQLException ex) {
                Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        grid.add(comboBoxSubject, 0,2);
    }
    
    private void createCourseBox(GridPane grid, String subject) {
        //grab classes
        //============================================
        //==================CourseName==============
        //label
        
        ResultSet courseRs = this.master.qp.selectCourseBySubject(subject, "CourseNum");
        grid.getChildren().remove(comboBoxCourse);
        grid.getChildren().remove(comboBoxProfessor);
        comboBoxCourse = new ComboBox();
        comboBoxProfessor = new ComboBox();
        grid.add(comboBoxProfessor, 0,6);
        Map<String,String> courseMap = new HashMap<>();
         try {
             while (courseRs.next()) {
                 String course = String.format("%s: %s",courseRs.getString(1),courseRs.getString(2));
                 courseMap.put(course,courseRs.getString(3));
                 comboBoxCourse.getItems().add(course);
             }} catch (SQLException ex) {
             Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        comboBoxCourse.setOnAction((event) -> {
            int selectedIndex = comboBoxCourse.getSelectionModel().getSelectedIndex();
            Object selectedItem = comboBoxCourse.getSelectionModel().getSelectedItem();
            if(selectedItem != null)
            {
                //grab classes
                createProfessorBox(grid, courseMap.get(selectedItem.toString()));
            }
        });
        grid.add(comboBoxCourse, 0,4);
    }
    
    private void createProfessorBox(GridPane grid, String courseId) {
        
        //grab classes
        //============================================
        //==================CourseName==============
        //label
        
        ResultSet profRs = this.master.qp.selectCourseProfessors(courseId);
        grid.getChildren().remove(comboBoxProfessor);
        comboBoxProfessor = new ComboBox();
        
         try {
             while (profRs.next()) {
                 comboBoxProfessor.getItems().add(profRs.getString(6));
             }} catch (SQLException ex) {
             Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        grid.add(comboBoxProfessor,0,6);

    }
    
    private VBox createQuestion(GridPane grid, String question, int loc) {
        Label assignments = new Label(question);
        grid.add(assignments, 6, loc);
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(100);
        VBox vbox = new VBox(textArea);
        grid.add(vbox, 6, loc+1);
        return vbox;
    }
    
     /* 
    ---------------------------
    function: addScrollPane
    ----------------------------
    params:
        GridPane grid
    purpose:
        make courses scrollable
    return:
        ScrollPane
    */
   
   public ScrollPane addScrollPane(GridPane grid) {
       ScrollPane scroll = new ScrollPane();
       scroll.setPrefSize(600, 900);
       scroll.setContent(grid);
       return scroll;
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
