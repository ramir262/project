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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
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
    
    
    PantherInspectProject master;
    userHomePage toHomePage;
    SubmitCourseReview toPrevPage;
    
    String cId = "0";
    boolean all = true;

    viewPost(PantherInspectProject master) {
        this.master = master;
        toHomePage = new userHomePage(master);
        this.toPrevPage = toPrevPage;
        
    }

    userHomePage user;

    /*
    param: cId (classId or courseId, depending on boolean), all (if all classes in course selected)
    */
    public Scene viewPosting(Stage primaryStage, String cId, boolean all)
    {

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
      
      ScrollPane scroll = addScrollPane(posts);
      scroll.setContent(grid);
      posts.add(scroll, 0, 0);

      //TODO: Cindy: add UI labels, back btn, and combobox
      /*
      Back button (return to table view page)
      Label: Display Course Name, result set determined by all == true boolean
      ComboBox: values are (1,2,3,4,5,All)
      */
      int g = 1;
      ResultSet rs;
      if (all == true) { // display ALL course reviews
          rs = this.master.qp.selectPost(cId, "Creation DESC");
          // Label: Display Course Name
          // courseRs = this.master.qp.getCourseName(cId)
          // ResultSet (Subject, CourseNum, cName) -> if you want to get Subject implement courseRs.getString(1)
          // cName is Course Name
      }
      else { // display class reviews (determined by professor)
        rs = this.master.qp.selectPostByClass(cId, "Creation DESC");
        // Label: Display Course Name
          // classRs = this.master.qp.getClassName(cId)
          // ResultSet (Subject, CourseNum, cName, pName) -> if you want to get Subject implement classRs.getString(1)
          // pName is Professor Name
      }
      
        try {

            while(rs.next()) {
                GridPane post = new GridPane();
                post.setAlignment(Pos.CENTER);
                post.setHgap(15);
                post.setVgap(15);
                post.setGridLinesVisible(false);
                grid.add(post,0,g++);
                //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, reviewId, accountId)
                createPost(primaryStage,post,rs.getString(9),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getInt(5),rs.getString(8),rs.getString(6),rs.getString(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch( FileNotFoundException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if (g == 1) {
            System.out.println("No reviews");
            Label lbl = new Label("No reviews available.");
            grid.add(lbl,0,g);
            
        }
      Scene scene = new Scene(posts, 400, 600);

      return scene;

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
		navigate to profile of user
	*/
    private void navigateToEdit(Stage primaryStage, Button btn, String reviewId) {
        btn.setOnAction((ActionEvent event) -> {

            // navigate to profile page for user
            rateCoursePage profilePage = new rateCoursePage(this.master);
            primaryStage.setScene(profilePage.rateCourse(primaryStage,reviewId));

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
    private void createPost(Stage primaryStage,GridPane post,String accountId,String subject, String courseNum, String cName, 
            String pName, int stars, String reviewId, String create, String edit) throws SQLException, FileNotFoundException {
        //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, reviewId, accountId)
        
                // get profile info based on accountId
                // "Email, Username, Picture, Year, Semester, Graduated";
                System.out.println(accountId);
                ResultSet accountRs = this.master.qp.selectProfileDisplay(accountId);
                accountRs.next();
                //TODO: change to picture
                int i = 1;
                Button picBtn = new Button(accountRs.getString(3));
                navigateToProfile(primaryStage,picBtn,accountId);
                HBox picBox = new HBox(picBtn);
                post.add(picBox,0,i++);
                Label accountLbl = new Label(accountRs.getString(2));
                //navigateToProfile(primaryStage,accountBtn,accountId);
                HBox accountBox = new HBox(accountLbl);
                post.add(accountBox,0,i++);
                
                //add edit button if accountId = master.accountId
                if (accountId.equals(this.master.getAccountId())) {
                    Button editBtn = new Button("Edit");
                    navigateToEdit(primaryStage,editBtn,reviewId);
                    HBox editBox = new HBox(editBtn);
                    post.add(editBox,0,i++);
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
       scroll.setPrefSize(400, 600);
       scroll.setContent(grid);
       return scroll;
   }
    
    
}
