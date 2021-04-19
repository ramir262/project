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
     List<Button> starList;
     Map<String,TextArea> responseMap;
     int starCount;
     String classId;
     //userHomePage toUserHomePage = new userHomePage();
     public rateCoursePage(PantherInspectProject master) {
        this.master = master;
        this.starCount = 0;
        this.classId = "0";
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
            createStars(ratePage);
                 
            GridPane qGrid = createQuestions();
            ScrollPane scroll = addScrollPane(qGrid);
            grid.add(scroll, 2, 1);
            
            
            HBox submitCourseHB = createSubmission(ratePage,primaryStage);
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
    
    /*
	-------------------------------
	function: createStar
	-------------------------------
        params:
                GridPane grid
                Image img: image of a star
	purpose:
		highlight stars up to current index if clicked
	*/
    private void createStar(GridPane grid, Image img) {
        Button button = new Button();
        this.starList.add(button);
        ImageView view = getImageView(img, 50, 50, false);
        button.setGraphic(view);
        int currentIdx = this.starList.size();
        button.setOnAction((ActionEvent e)-> {
            
            int start = this.starCount;
            this.starCount = currentIdx;
            if (start > currentIdx) {
                start = currentIdx;
            }
            for (int i=start; i<this.starList.size(); i++) {
                if (i < currentIdx) {
                    this.starList.get(i).setStyle("-fx-background-color: #ff0000");
                }
                else {
                    this.starList.get(i).setStyle("-fx-background-color: WHITESMOKE ");
                }
            }
        });
    }
    
    /*
	-------------------------------
	function: createStars
	-------------------------------
	purpose:
		create Stars
                if clicked, highlight all prior
	*/
    private void createStars(GridPane grid) throws FileNotFoundException {
           //============ stars ====================
            FileInputStream stream = new FileInputStream("star.jpeg");
            Image starsRate = new Image(stream);
           
            
            this.starList = new ArrayList();
            for (int i=0; i<5; i++) {
                createStar(grid, starsRate);
            }
            
            Label label = new Label("Rate from 1 (low rating) to 5 (high rating): ");
            grid.add(label,0,7);
            HBox hbox = new HBox(this.starList.get(0), this.starList.get(1), this.starList.get(2), this.starList.get(3), this.starList.get(4));
            grid.add(hbox,0,8);
            
    }
    
    private TextArea createQuestion(GridPane grid, String question, int loc) {
        Label assignments = new Label(question);
        grid.add(assignments, 6, loc);
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(100);
        VBox vbox = new VBox(textArea);
        grid.add(vbox, 6, loc+1);
        return textArea;
    }
    
    private GridPane createQuestions() {
            //============= Questions ============
            this.responseMap = new HashMap<>();
            ResultSet rs = this.master.qp.selectPostQuestions();
            int loc = 1;
            GridPane qGrid = new GridPane();
            try {
                while (rs.next()) {
                    String questionId = rs.getString(1);
                    String question = rs.getString(2);
                    TextArea textArea = createQuestion(qGrid,question,loc);
                    this.responseMap.put(questionId, textArea);
                    loc = loc+2;
                }
            } catch (SQLException ex) {
                Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            return qGrid;
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
        Map<String,String> professorMap = new HashMap<>();
         try {
             while (profRs.next()) {
                 String classId = profRs.getString(1);
                 String name = profRs.getString(6);
                 professorMap.put(name,classId);
                 comboBoxProfessor.getItems().add(name);
             }} catch (SQLException ex) {
             Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
         }
         comboBoxProfessor.setOnAction((event) -> {
            int selectedIndex = comboBoxProfessor.getSelectionModel().getSelectedIndex();
            Object selectedItem = comboBoxProfessor.getSelectionModel().getSelectedItem();
            if(selectedItem != null)
            {
                this.classId = professorMap.get(selectedItem.toString());
            }
        });
        
        grid.add(comboBoxProfessor,0,6);

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
    /*
	-------------------------------
	function: createSubmission
	-------------------------------
	purpose:
		create submission button
                check if entries valid
                get new review id
                insert review
                insert question responses
                insert post
	return:
		boolean HBox
	*/
    private HBox createSubmission(GridPane grid, Stage primaryStage) {
        HBox hbox = new HBox(10);
        Button submit = new Button("Submit Course Review");
            
        submit.setOnAction((ActionEvent e)-> {
            if (!this.classId.equals("0") && (this.starCount != 0)) {
                //get current timestamp
                Time time = new Time();
                String timestamp = time.getCurrentTimestamp();
                //get new reviewId
                String reviewId = this.master.qp.getUniqueId("ReviewId", "Review");
                //insert star review
                this.master.qp.insertReview(reviewId,Integer.toString(this.starCount),timestamp);
                //insert responses to all questions
                for (String questionId : this.responseMap.keySet()) {
                    String response = this.responseMap.get(questionId).getText();
                    if (response.replace(" ","").length() > 0) {
                        this.master.qp.insertResponse(reviewId, questionId, response, timestamp);
                    }
                }
                //insert post
                this.master.qp.insertPost(reviewId, this.master.getAccountId(), this.classId, timestamp);

                primaryStage.setScene(toSubmit.submitReview(primaryStage));
            }
        });
        hbox.getChildren().add(submit);
        
        return hbox;
    }
    
   
    
    
    
    
     
    
    
    
    
    
}
