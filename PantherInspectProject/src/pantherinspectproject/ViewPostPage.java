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
public class ViewPostPage {

    PantherInspectProject master;
    List<GridPane> postList;
    
    ViewPostPage(PantherInspectProject master) {
        this.master = master;
    }

    
    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
        String selectedSubject
        String cId (classId or courseId, depending on boolean), all (if all classes in course selected)
        String courseId
        boolean all
    purpose:
        setup page to view all posts associated with class or course
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage, String selectedSubject, String cId, String courseId, boolean all)
    {
        this.postList = new ArrayList<>();

        primaryStage.setTitle("View Post");

        //setup grid
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

        grid.setPrefSize(800, 700); 

        ScrollPane scroll = addScrollPane(posts);
        scroll.setContent(grid);

        posts.add(scroll, 0, 0); //0,0

       VBox vbox = new VBox();
       vbox.setAlignment(Pos.CENTER);
       vbox.getChildren().addAll(posts, grid, scroll);
       
       // setup page title
       Label displayCourseName = new Label("Course Name:");
       displayCourseName.setStyle("-fx-font-weight:bold");
       grid.add(displayCourseName, 0,1); // posts
       
       // setup combo box to filter reviews by star count
       Label rateByStars = new Label("Filter by Star Ratings:");
       grid.add(rateByStars, 5,1);
       
       addStarComboBox(primaryStage, grid, all, cId);
       
       // setup back button
       Button backButton = new Button("Back");
       HBox backHbox = new HBox();
       backHbox.setAlignment(Pos.TOP_RIGHT);
       backHbox.getChildren().add(backButton);
       backButton.setOnAction(e -> primaryStage.setScene(master.getCourseDisplay().setupPage(primaryStage, courseId, selectedSubject)));
       grid.add(backButton, 0, 0);
       
       
        if (all == true) { // setupPage ALL course reviews
          
            ResultSet courseRs = this.master.qp.getCourseName(cId);
            try {
                courseRs.next();
                displayTitle(grid,courseRs);
            } catch (SQLException ex) {
                Logger.getLogger(ViewPostPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else { // setupPage class reviews (determined by professor)
             ResultSet courseRs = this.master.qp.getClassName(cId);
            try {
                courseRs.next();
                String pName = courseRs.getString(4);
                displayTitle(grid,courseRs);

                Label displayProfName = new Label(pName);
                grid.add(displayProfName, 0,3);

            } catch (SQLException ex) {
                Logger.getLogger(ViewPostPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
      
        displayUnfilteredReviews(primaryStage, grid, all, cId);

        Scene scene = new Scene(vbox,900,900); 
        return scene;

    }
    /*
    ---------------------------
    function: addStarComboBox
    ----------------------------
    purpose:
        select review by star count
    return:
        ComboBox
    */
    private ComboBox addStarComboBox(Stage primaryStage, GridPane grid, boolean all, String cId) {
        ComboBox starBox = new ComboBox();
        starBox.getItems().add("1 Star");
        starBox.getItems().add("2 Star");
        starBox.getItems().add("3 Star");
        starBox.getItems().add("4 Star");
        starBox.getItems().add("5 Star");
        starBox.getItems().add("All");
        starBox.setOnAction((event) -> {
           //if selection made:
           //   delete buttons
           //   replace with new order
            Object selectedItem = starBox.getSelectionModel().getSelectedItem();

            if(selectedItem.equals("All"))
            {
                removeReviews(grid);
                displayUnfilteredReviews(primaryStage, grid, all, cId);
            }
            else
            {
                removeReviews(grid);
                displayFilteredReviews(primaryStage, grid, all, cId, selectedItem.toString());
            }
        });
       grid.add(starBox, 6,1);
       return starBox;
    }
    /*
	-------------------------------
	function: displayTitle
	-------------------------------
        params:

                GridPane grid
                ResultSet
	purpose:
		take in courseRs from course info or class info
                generate title
	*/
    private void displayTitle(GridPane grid, ResultSet courseRs) throws SQLException {

              String subject = courseRs.getString(1);
              String courseNum = courseRs.getString(2);           
              String cName = courseRs.getString(3);
              
              String classPattern = "%s %s: %s";
              String classTitle = String.format(classPattern, subject, courseNum,cName);
              
              Label displayClassName = new Label(classTitle);
              grid.add(displayClassName, 0,2);
    }
    /*
	-------------------------------
	function: removeReviews
	-------------------------------
        params:
                GridPane grid
	purpose:
                remove posts from grid
	*/
    private void removeReviews(GridPane grid) {
        
        for (GridPane post : this.postList) {
            grid.getChildren().remove(post);
        }
        this.postList = new ArrayList<>();
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
        if (all == true) { // setupPage ALL course reviews
            rs = this.master.qp.selectPost(cId, "Creation DESC");
            
        }
        else { // setupPage class reviews (determined by professor)
          rs = this.master.qp.selectPostByClass(cId, "Creation DESC");
          
        }

        displayReviews(primaryStage,grid,all,rs);
    }
    /*
	-------------------------------
	function: displayFilteredReviews
	-------------------------------
        params:
                Stage primaryStage
                GridPane grid
                boolean all : if all classes in course all is true
                string cId : courseId if all==true, else classId
                string star : count of stars to filter by
	purpose:
                create resultset without star count filter
		populate grid with posts
	*/
    private void displayFilteredReviews(Stage primaryStage, GridPane grid, boolean all, String cId, String star) {
        
        ResultSet rs;
        if (all == true) { // setupPage ALL course reviews
            rs = this.master.qp.selectCourseReviewByStars(star,cId, "Creation DESC");
            
        }
        else { // setupPage class reviews (determined by professor)
          rs = this.master.qp.selectClassReviewByStars(star,cId, "Creation DESC");
          
        }

        displayReviews(primaryStage,grid,all,rs);
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
    private void displayReviews(Stage primaryStage, GridPane grid, boolean all, ResultSet rs) {
        int FIRST_POST = 4;
        int g = FIRST_POST;
        try {
            
            while(rs.next()) {
                GridPane post = new GridPane();
                post.setAlignment(Pos.CENTER);
                post.setHgap(15);
                post.setVgap(15);
                post.setGridLinesVisible(false);
                post.setStyle("-fx-background-color: white; -fx-grid-lines-visible: false");
                this.postList.add(post);
                grid.add(post,0,g++);
                g++;
                //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, reviewId, accountId,courseId)
                createPost(primaryStage,post,all,rs.getString(9),rs.getString(10),rs.getString(1),rs.getString(2),
                        rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(8),rs.getString(6),rs.getString(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch( FileNotFoundException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (g == FIRST_POST) {
                GridPane post = new GridPane();
                post.setAlignment(Pos.CENTER);
                post.setHgap(15);
                post.setVgap(15);
                post.setGridLinesVisible(false);
                post.setStyle("-fx-background-color: white; -fx-grid-lines-visible: false");
                this.postList.add(post);
                grid.add(post,0,g++);
                
                Label lbl = new Label("No reviews available.");
                post.add(lbl,0,g);

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
            primaryStage.setScene(profilePage.setupPage(primaryStage,accountId));

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
            RateCoursePage ratePage = this.master.getRateCoursePage();
            primaryStage.setScene(ratePage.setupPage(primaryStage,reviewId));

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
            DeletePostPage deletePage = this.master.getDeletePost();
            primaryStage.setScene(deletePage.setupPage(primaryStage,courseId,reviewId));

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
    private void createPost(Stage primaryStage,GridPane post,boolean all,String accountId, String courseId, String subject, String courseNum, String cName,
            String pName, int stars, String reviewId, String create, String edit) throws SQLException, FileNotFoundException {
        //ResultSet (subject, courseNum, cName, pName, stars, creation, edit, reviewId, accountId,courseId)

                // get profile info based on accountId
                // "Email, Username, Picture, Year, Semester, Graduated";

                ResultSet accountRs = this.master.qp.selectProfileDisplay(accountId);
                accountRs.next();

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
                        Logger.getLogger(SignupPage.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }
                Button picBtn = new Button("View Profile");
                picBtn.setGraphic(imageView);
                navigateToProfile(primaryStage,picBtn,accountId);
                HBox picBox = new HBox(picBtn);
                post.add(picBox,0,i++);
                Label accountLbl = new Label(accountRs.getString(2));

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
                
                //display professor name
                if (all) {
                    Label profLblBold = new Label("Professor:");
                    profLblBold.setStyle("-fx-font-weight: bold");
                    Label profLbl = new Label(pName);
                    HBox profBox = new HBox(profLblBold,profLbl);
                    post.add(profBox,0,i++);
                }

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
                ResultSet quesRs = this.master.qp.selectReviewQuestions(reviewId);
                while(quesRs.next()) {
                    Label quesLbl = new Label(quesRs.getString(1));
                    quesLbl.setStyle("-fx-font-weight: bold");
                    post.add(quesLbl,0,i++);
                    String[] responses = quesRs.getString(2).split("\n");
                    //String response = quesRs.getString(2);
                    for (String response : responses) {
                        //handle responses with long output
                        while (response.length() > 80) {
                            // try to split at space
                            int next = response.substring(61,response.length()).indexOf(" ");
                            if (next > -1) {
                                Label resLbl = new Label(response.substring(0,60+next+1));
                                post.add(resLbl,0,i++);
                                response = response.substring(60+next+1,response.length());
                            }
                            else {
                                Label resLbl = new Label(response.substring(0,60)+"-");
                                post.add(resLbl,0,i++);
                                response = response.substring(61,response.length());
                            }
                            
                        }
                        Label resLbl = new Label(response);
                        post.add(resLbl,0,i++);
                    }
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
