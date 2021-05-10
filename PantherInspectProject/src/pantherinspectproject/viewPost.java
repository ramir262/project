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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author allis
 */
public class viewPost {



    // view all professors views in scroll view


    PantherInspectProject master;
    //public String selectedSubject = "";


    viewPost(PantherInspectProject master) {
        this.master = master;
    }

    userHomePage user;
    
    /*
    param: cId (classId or courseId, depending on boolean), all (if all classes in course selected)
    */
    public Scene viewPosting(Stage primaryStage, String cId, boolean all)
    {
        //this.selectedSubject = selectedSubject;

      primaryStage.setTitle("Select Post ");
      
      GridPane posts = new GridPane();
      posts.setAlignment(Pos.CENTER);
      posts.setHgap(15);
      posts.setVgap(15);
      posts.setGridLinesVisible(false);

      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(15);
      grid.setVgap(15);
      grid.setGridLinesVisible(false);
      //Cindy: new 
      grid.setPrefSize(800, 700); //400, 600
      
      ScrollPane scroll = addScrollPane(posts);
      scroll.setContent(grid);
      
      
      posts.add(scroll, 0, 0); //0,0
      
      // Cindy: label
      //Label displayCourseName = new Label("Course Name:");
      //grid.add(displayCourseName, 0,0);
      
       //Cindy: setFitToHeight and Width
      
       
       
       // Cindy: VBOX 
       VBox vbox = new VBox();
       vbox.setAlignment(Pos.CENTER);
       vbox.getChildren().addAll(posts, grid, scroll);
       //scroll.setFitToHeight(true);
       
       Label displayCourseName = new Label("Course Name:");
       displayCourseName.setStyle("-fx-font-weight:bold");
       grid.add(displayCourseName, 0,1); // posts
       // Label to display course name - userhome page 
       
       
       Label rateByStars = new Label("Filter by Star Ratings:");
       grid.add(rateByStars, 5,1);
       
       ComboBox starBox = new ComboBox();
       starBox.getItems().add("1 Star");
       starBox.getItems().add("2 Star");
       starBox.getItems().add("3 Star");
       starBox.getItems().add("4 Star");
       starBox.getItems().add("5 Star");
       starBox.getItems().add("All");
       grid.add(starBox, 6,1);
       
       
       
       
       
       // Cindy: Back Button
       Button backButton = new Button("Back");
       HBox backHbox = new HBox();
       backHbox.setAlignment(Pos.TOP_RIGHT);
       backHbox.getChildren().add(backButton);
       backButton.setOnAction(e -> primaryStage.setScene(master.getCourseDisplay().display(primaryStage, cId, cId)));
       grid.add(backButton, 0, 0);
       
       
      
       
      

      
      //TODO: Cindy: add UI labels, back btn, and combobox
      /*
      Back button (return to table view page)
      Label: Display Course Name, result set determined by all == true boolean
      ComboBox: values are (1,2,3,4,5,All)
      */
        if (all == true) { // display ALL course reviews
            // Label: Display Course Name
          
            ResultSet courseRs = this.master.qp.getCourseName(cId);
          try {
              courseRs.next();
              String subject = courseRs.getString(1);
              String courseNum = courseRs.getString(2);           
              String cName = courseRs.getString(3);
              
              String classPattern = "Class Name: \n %s %s: %s";
              String classTitle = String.format(classPattern, subject, courseNum,cName);
              
              displayCourseName.setText(classTitle);
              
          } catch (SQLException ex) {
              Logger.getLogger(viewPost.class.getName()).log(Level.SEVERE, null, ex);
          }
            
            //courseRs = this.master.qp.getCourseName(cId)
            // ResultSet (Subject, CourseNum, cName) -> if you want to get Subject implement courseRs.getString(1)
            // cName is Course Name
            //grid.add(displayCouseNameTitle, 2,0);
        }
        else { // display class reviews (determined by professor)
             ResultSet courseRs = this.master.qp.getClassName(cId);
          try {
              courseRs.next();
              String subject = courseRs.getString(1);
              String courseNum = courseRs.getString(2);           
              String cName = courseRs.getString(3);
              String pName = courseRs.getString(4);
              
              String classPattern = "%s %s: %s";
              String classTitle = String.format(classPattern, subject, courseNum,cName);
              
              Label displayClassName = new Label(classTitle);
              grid.add(displayClassName, 0,2);
              
              Label displayProfName = new Label(pName);
              grid.add(displayProfName, 0,3);
              
          } catch (SQLException ex) {
              Logger.getLogger(viewPost.class.getName()).log(Level.SEVERE, null, ex);
          }
            // Label: Display Course Name
            // classRs = this.master.qp.getClassName(cId)
            // ResultSet (Subject, CourseNum, cName, pName) -> if you want to get Subject implement classRs.getString(1)
            // pName is Professor Name
        }
      
        displayUnfilteredReviews(primaryStage, grid, all, cId);
        //Scene scene = new Scene(posts, 400, 600);
        Scene scene = new Scene(vbox,900,900); // Cindy : VBOX
        return scene;

    }
    /*
	-------------------------------
	function: displayUnfilteredReviews
	-------------------------------
        params:
                Stage primaryStage
                GridPane grid
                boolean all : if all classes in course all is true
                string cId : courseId if all==true, else classId
	purpose:
                create resultset without any filter (any star type allowed)
		populate grid with posts
	*/
    private void displayUnfilteredReviews(Stage primaryStage, GridPane grid, boolean all, String cId) {
        
        ResultSet rs;
        if (all == true) { // display ALL course reviews
            rs = this.master.qp.selectPost(cId, "Creation DESC");
            
        }
        else { // display class reviews (determined by professor)
          rs = this.master.qp.selectPostByClass(cId, "Creation DESC");
          
        }

        displayReviews(primaryStage,grid,rs);
    }
    /*
	-------------------------------
	function: displayUnfilteredReviews
	-------------------------------
        params:
                Stage primaryStage
                GridPane grid
                ResultSet
	purpose:
		take in resultset and populate reviews
                may be called using either filtered or unfiltered post resultset
	*/
    private void displayReviews(Stage primaryStage, GridPane grid, ResultSet rs) {
        int g = 4;
        try {
            
            while(rs.next()) {
                GridPane post = new GridPane();
                post.setAlignment(Pos.CENTER);
                post.setHgap(15);
                post.setVgap(15);
                post.setGridLinesVisible(false);
                grid.add(post,0,g++);
                //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, reviewId, accountId,courseId)
                createPost(primaryStage,post,rs.getString(9),rs.getString(10),rs.getString(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(8),rs.getString(6),rs.getString(7));
                System.out.println(rs.getString(10));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch( FileNotFoundException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (g == 2) {
            System.out.println("No reviews");
            Label lbl = new Label("No reviews available.");
            grid.add(lbl,0,g);

        }
    }
    /*
	-------------------------------
	function: navigateToProfile
	-------------------------------
        params:
                Stage primaryStage
                Button btn : either image or username attached to post
                String accountId
	purpose:
		navigate to profile of user
	*/
    private void navigateToProfile(Stage primaryStage, Button btn, String accountId) {
        btn.setOnAction((ActionEvent event) -> {

            // navigate to profile page for user
            ProfilePage profilePage = new ProfilePage(this.master);
            primaryStage.setScene(profilePage.userpage(primaryStage,accountId));

        });
    }
    /*
	-------------------------------
	function: navigateToEdit
	-------------------------------
        params:
                Stage primaryStage
                Button btn : either image or username attached to post
                String accountId
	purpose:
		navigate to edit post
	*/
    private void navigateToEdit(Stage primaryStage, Button btn, String reviewId) {
        btn.setOnAction((ActionEvent event) -> {

            // navigate to profile page for user
            rateCoursePage ratePage = this.master.getRateCoursePage();
            primaryStage.setScene(ratePage.rateCourse(primaryStage,reviewId));

        });
    }
    /*
	-------------------------------
	function: navigateToDelete
	-------------------------------
        params:
                Stage primaryStage
                Button btn : either image or username attached to post
                String courseId, reviewId
	purpose:
		navigate to delete page of post
	*/
    private void navigateToDelete(Stage primaryStage, Button btn, String courseId, String reviewId) {
        btn.setOnAction((ActionEvent event) -> {

            // navigate to profile page for user
            deletePost deletePage = this.master.getDeletePost();
            primaryStage.setScene(deletePage.deletePosting(primaryStage,courseId,reviewId));

        });
    }
     /*
	-------------------------------
	function: createPost
	-------------------------------
        params:
                GridPane grid
                ResultSet values
	purpose:
		format post
	*/
    private void createPost(Stage primaryStage,GridPane post,String accountId, String courseId, String subject, String courseNum, String cName,
            String pName, int stars, String reviewId, String create, String edit) throws SQLException, FileNotFoundException {
        //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, reviewId, accountId,courseId)

                // get profile info based on accountId
                // "Email, Username, Picture, Year, Semester, Graduated";
                System.out.println(accountId);
                ResultSet accountRs = this.master.qp.selectProfileDisplay(accountId);
                accountRs.next();
                //TODO: change to picture
                int i = 1;
                String imgPath = accountRs.getString(3);
                ImageView imageView = new ImageView();
                imageView.setFitHeight(128);
                imageView.setFitWidth(128);
                imageView.setPreserveRatio(true);
                if (!imgPath.equals("empty")) {
                    try
                    {
                        FileInputStream stream = new FileInputStream(imgPath);
                        Image imagestream = new Image(stream);
                        imageView.setImage(imagestream);

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SignupForm.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }
                Button picBtn = new Button("View Profile");
                picBtn.setGraphic(imageView);
                navigateToProfile(primaryStage,picBtn,accountId);
                HBox picBox = new HBox(picBtn);
                post.add(picBox,0,i++);
                Label accountLbl = new Label(accountRs.getString(2));
                //navigateToProfile(primaryStage,accountBtn,accountId);
                HBox accountBox = new HBox(accountLbl);
                post.add(accountBox,0,i++);

                //add edit and delete button if accountId = master.accountId
                if (accountId.equals(this.master.getAccountId())) {
                    Button editBtn = new Button("Edit");
                    navigateToEdit(primaryStage,editBtn,reviewId);
                    Button deleteBtn = new Button("Delete");
                    navigateToDelete(primaryStage,deleteBtn,courseId,reviewId);
                    HBox actionBox = new HBox(editBtn,deleteBtn);
                    post.add(actionBox,0,i++);
                }

                // display course info
                Label classLblBold = new Label("Class:");
                classLblBold.setStyle("-fx-font-weight: bold");
                Label classLbl = new Label(String.format("%s %s: %s", subject, courseNum, cName));
                HBox classBox = new HBox(classLblBold,classLbl);
                post.add(classBox,0,i++);

                //display professor name
                Label profLblBold = new Label("Professor:");
                profLblBold.setStyle("-fx-font-weight: bold");
                Label profLbl = new Label(pName);
                HBox profBox = new HBox(profLblBold,profLbl);
                post.add(profBox,0,i++);

                //display star count
                HBox starGrid = createStars(stars);
                post.add(starGrid,0,i++);

                //display timestamp
                Label createLbl = new Label(create);
                post.add(createLbl,0,i++);
                if (!create.equals(edit)) {
                    Label editLblBold = new Label("Edit:");
                    editLblBold.setStyle("-fx-font-weight: bold");
                    Label editLbl = new Label(edit);
                    HBox editBox = new HBox(editLblBold,editLbl);
                    post.add(editBox,0,i++);
                }

                //display questions and responses
                ResultSet rs2 = this.master.qp.selectReviewQuestions(reviewId);
                while(rs2.next()) {
                    Label quesLbl = new Label(rs2.getString(1));
                    quesLbl.setStyle("-fx-font-weight: bold");
                    post.add(quesLbl,0,i++);
                    Label resLbl = new Label(rs2.getString(2));
                    post.add(resLbl,0,i++);
                }
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
	function: createStar
	-------------------------------
        params:
                GridPane grid
                Image img: image of a star
	purpose:
		highlight stars up to current index if clicked
	*/
    private void createStar(List<Button> starList, int count, Image img) {
        Button button = new Button();
        starList.add(button);
        ImageView view = getImageView(img, 50, 50, false);
        button.setGraphic(view);
        int currentIdx = starList.size();
        if (currentIdx <= count) {
            button.setStyle("-fx-background-color: #ff0000");
        }
        else {
            button.setStyle("-fx-background-color: WHITESMOKE ");
        }
    }

    /*
	-------------------------------
	function: createStars
	-------------------------------
	purpose:
		create Stars
                if clicked, highlight all prior
	*/
    private HBox createStars(int count) throws FileNotFoundException {
           //============ stars ====================
            FileInputStream stream = new FileInputStream("star.jpeg");
            Image starsRate = new Image(stream);


            List<Button> starList = new ArrayList();
            for (int i=0; i<5; i++) {
                createStar(starList, count, starsRate);
            }

            HBox hbox = new HBox(starList.get(0), starList.get(1), starList.get(2), starList.get(3), starList.get(4));
            return hbox;

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
       scroll.setPrefSize(900, 900); // 400, 600
       scroll.setContent(grid);
       return scroll;
   }


}
