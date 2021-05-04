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
import javafx.event.Event;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
     
     PantherInspectProject master;
     ComboBox comboBoxCourse;
     ComboBox comboBoxProfessor;
     ComboBox comboBoxSubject = new ComboBox();
     List<Button> starList;
     Map<String,TextArea> responseMap;
     int starCount;
     String classId;
     String courseId;
     public rateCoursePage(PantherInspectProject master) {
        this.master = master;
        this.starCount = 0;
        this.classId = "0";
        this.courseId = "0";
     }



// added boolean view
    public Scene rateCourse(Stage primaryStage, String edit)
    {

        InputStream stream = null;

            GridPane grid = new GridPane();
            GridPane ratePage = new GridPane();
            grid.add(ratePage, 1, 1);
            
            ratePage.setAlignment(Pos.TOP_CENTER);
            ratePage.setHgap(15);
            ratePage.setVgap(15);
            ratePage.setGridLinesVisible(false);
            Scene scene = new Scene(grid, 850, 600, Color.WHITESMOKE);


            //comboBox for subject, course, and professor

        Text settingsTitle = new Text();
        switch (edit){
            

            case PantherInspectProject.NEW_POST:
                 primaryStage.setTitle("Rate a Course ");
                 settingsTitle.setText("Rate a Chapman Course");
                 settingsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                 ratePage.add(settingsTitle, 0, 0, 2, 1);
                 break;
                 
            default:
                primaryStage.setTitle("Edit Course Posting ");
                settingsTitle.setText("Edit Chapman Course Rating");
                settingsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                ratePage.add(settingsTitle, 0, 0, 2, 1);
                break;

            

        }

        createSubjectBox(ratePage, edit);

            //Would Recommend (star rating)
            System.out.println(System.getProperty("user.dir"));
        try {
            createStars(ratePage);

            GridPane qGrid = createQuestions();
            ScrollPane scroll = addScrollPane(qGrid);
            grid.add(scroll, 2, 1);


            HBox submitCourseHB = createSubmission(ratePage,primaryStage, edit);
            ratePage.add(submitCourseHB, 0, 9);


            Button cancelPost = new Button("Cancel");
            HBox cancelHB = new HBox(10);
            //ratePage.add(cancelPost, 0, 8);
            //====== workimg on this===============
            //toUserHomePage = toUserRootPage;
            
            userHomePage toUserHomePage = new userHomePage(master);
            cancelPost.setOnAction(e -> primaryStage.setScene(toUserHomePage.userpage(primaryStage)));
            cancelHB.setAlignment(Pos.BOTTOM_CENTER);
            cancelHB.getChildren().add(cancelPost);
            ratePage.add(cancelPost, 0, 10);


        } catch (FileNotFoundException ex) {
            Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
        }


         return scene;




    }
    /*
	-------------------------------
	function: selectClass
	-------------------------------
        params:
                String edit
                    edit identified by postId
	purpose:
		select combo boxes by searching for postId
	*/
    private void selectClass(String edit) {
        //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, courseId, classId)
        ResultSet rs = this.master.qp.selectPostByReviewId(edit, "subject");
        
         try {
             rs.next();
             comboBoxSubject.setValue(rs.getString(1));
             comboBoxCourse.setValue(String.format("%s: %s",rs.getString(2),rs.getString(3)));
             comboBoxProfessor.setValue(rs.getString(4));
            
             
             // grab classId and courseId
             this.courseId = rs.getString(8);
             this.classId = rs.getString(9);
             
             selectStars(0,rs.getInt(5));
             
             // auto fill answers
             ResultSet rs2 = this.master.qp.selectReviewQuestions(edit);
            //question,response,questionId
                while(rs2.next()) {
                    this.responseMap.get(rs2.getString(3)).setText(rs2.getString(2));
                }
             
             
         } catch (SQLException ex) {
             Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    /*
	-------------------------------
	function: createSubjects
	-------------------------------
        params:
                GridPane grid
                String edit
	purpose:
		create subject, course, and professor boxes, modify based on selection
                disable comboboxes if edit
	*/
    private void createSubjectBox(GridPane grid, String edit) {
        // ============ Subject Name =================
        // initialize course and professor
        comboBoxCourse = new ComboBox();
        comboBoxProfessor = new ComboBox();

        Label courseSubject = new Label("Subject Name");
        grid.add(courseSubject, 0,1);
        Label courseName = new Label("Course Name: ");
        grid.add(courseName, 0,3);

        grid.add(comboBoxCourse, 0,4);

        grid.add(comboBoxProfessor, 0,6);

        Label professorName = new Label("Professor Name: ");
        grid.add(professorName, 0,5);

        // call database to get subjects
        ResultSet subjectRs = this.master.qp.selectSubjects();
        comboBoxSubject.getItems().clear();
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
        
        
        // enable or disable selection
        switch(edit)
        {
            // new
            case PantherInspectProject.NEW_POST:
                comboBoxCourse.setDisable(false);
                comboBoxProfessor.setDisable(false);
                comboBoxSubject.setDisable(false);
                break;
                
            // edit post
            default:
                comboBoxCourse.setDisable(true);
                comboBoxProfessor.setDisable(true);
                comboBoxSubject.setDisable(true);
                break;
        }
    }

    /*
	-------------------------------
	function: selectStars
	-------------------------------
        params:
                int start
                    will be > 0 if some already selected
                int selectedIdx
                    number of stars to highlight
	purpose:
		highlight stars up to current index if clicked
	*/
    private void selectStars(int start, int selectedIdx) {
            this.starCount = selectedIdx;
            if (start > selectedIdx) {
                start = selectedIdx;
            }
            for (int i=start; i<this.starList.size(); i++) {
                if (i < selectedIdx) {
                    this.starList.get(i).setStyle("-fx-background-color: #ff0000");
                }
                else {
                    this.starList.get(i).setStyle("-fx-background-color: WHITESMOKE ");
                }
            }
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
            
            selectStars(start,currentIdx);
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
        // create question label
        Label assignments = new Label(question);
        grid.add(assignments, 6, loc);
        
        // create text box
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(100);
        
        // add to container
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
        Map<String,Map<String,String>> professorMap = new HashMap<>();
         try {
             while (profRs.next()) {
                 String classId = profRs.getString(1);
                 String name = profRs.getString(6);
                 Map<String,String> info = new HashMap<>();
                 info.put("classId",classId);
                 info.put("courseId", courseId);
                 professorMap.put(name,info);
                 comboBoxProfessor.getItems().add(name);
             }} catch (SQLException ex) {
             Logger.getLogger(rateCoursePage.class.getName()).log(Level.SEVERE, null, ex);
         }
         comboBoxProfessor.setOnAction((event) -> {
            int selectedIndex = comboBoxProfessor.getSelectionModel().getSelectedIndex();
            Object selectedItem = comboBoxProfessor.getSelectionModel().getSelectedItem();
            if(selectedItem != null)
            {
                this.classId = professorMap.get(selectedItem.toString()).get("classId");
                this.courseId = professorMap.get(selectedItem.toString()).get("courseId");
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
    
    private void insertToDB(Stage primaryStage, Button submit) {
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
                    SubmitCourseReview toSubmit = new SubmitCourseReview(this.master);
                    primaryStage.setScene(toSubmit.submitReview(primaryStage,this.courseId,reviewId));
                } else {
                    ErrorPopup.Pop("All fields must be filled out to submit.");
                }
            });
    }
    private void updateDB(Stage primaryStage, Button submit, String reviewId) {
        submit.setOnAction((ActionEvent e)-> {
                //get current timestamp
                    Time time = new Time();
                    String timestamp = time.getCurrentTimestamp();
                    //get new reviewId
                    
                    //insert star review
                    this.master.qp.updateReview(reviewId,Integer.toString(this.starCount),timestamp);
                    //insert responses to all questions
                    for (String questionId : this.responseMap.keySet()) {
                        String response = this.responseMap.get(questionId).getText();
                        // remove all empty spaces to check for valid entry
                        if (response.replace(" ","").length() > 0) {
                            this.master.qp.updateResponse(reviewId, questionId, response, timestamp);
                        }
                    }
                    //navigate to next page
                    SubmitCourseReview toSubmit = new SubmitCourseReview(this.master);
                    primaryStage.setScene(toSubmit.submitReview(primaryStage,this.courseId,reviewId));
            });
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
    private HBox createSubmission(GridPane grid, Stage primaryStage, String edit) {
        HBox hbox = new HBox(10);

        Button submit = new Button();

            

        switch(edit)
        {

            // new post
            case PantherInspectProject.NEW_POST:
                submit.setText("Submit Course Review");
                insertToDB(primaryStage,submit);
                break;
            // edit post
            default:
                submit.setText("Update Course Review");
                
                selectClass(edit);
                updateDB(primaryStage,submit,edit);
                break;


        }

        hbox.getChildren().add(submit);

        return hbox;
    }

}
