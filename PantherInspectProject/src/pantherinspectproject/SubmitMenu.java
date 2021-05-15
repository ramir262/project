/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author cindyramirez
 */
public class SubmitMenu {


    PantherInspectProject master;

    SubmitMenu(PantherInspectProject master) {

        this.master = master;
    }

    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
        String courseId
        String postId
    purpose:
        create a menu for submissions
            edit post
            delete post
            view post
            return to home page
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage, String courseId, String postId)
    {
        // set up other pages
        DeletePostPage toDeletePosting = this.master.getDeletePost();
        HomePage toHomePage = this.master.getUserHomePage();
        RateCoursePage toRateCourse = this.master.getRateCoursePage();
        ViewPostPage toViewPosting = this.master.getViewPost();

        primaryStage.setTitle("Submission Menu");
        Text title = new Text("Posting Successful");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        GridPane submitPage = new GridPane();
        submitPage.setAlignment(Pos.CENTER);
        submitPage.setHgap(15);
        submitPage.setVgap(15);
        submitPage.setGridLinesVisible(false);
        Scene scene = new Scene(submitPage, 800, 600);
        submitPage.add(title, 1, 1, 2, 1);


        //============== editPost Button ============
        Button editPost = new Button("Edit Post");
        HBox editPostHB = new HBox(10);
        editPost.setOnAction(e -> primaryStage.setScene(toRateCourse.setupPage(primaryStage, postId)));
        editPostHB.setAlignment(Pos.BOTTOM_RIGHT);
        editPostHB.getChildren().add(editPost);
        submitPage.add(editPost,1,2);

        //============ DeletePostPage =====================
        Button deletePost = new Button("Delete Post");
        HBox deletePostHB = new HBox(10);
        deletePost.setOnAction(e -> primaryStage.setScene(toDeletePosting.setupPage(primaryStage,courseId,postId)));
        deletePostHB.setAlignment(Pos.BOTTOM_RIGHT);
        deletePostHB.getChildren().add(deletePost);
        submitPage.add(deletePost,2,2);


        // ========== View Post =======================
        Button viewPost = new Button("View Post");
        HBox viewPostHB = new HBox(10);
        //ResultSet  = subject
        ResultSet rs = this.master.qp.selectSubjectFromCourse(courseId);
            try {
                rs.next();
                String selectedSubject = rs.getString(1);
                viewPost.setOnAction(e -> primaryStage.setScene(toViewPosting.setupPage(primaryStage,selectedSubject,courseId,courseId,true)));
                viewPostHB.setAlignment(Pos.BOTTOM_RIGHT);
                viewPostHB.getChildren().add(viewPost);
            } catch (SQLException ex) {
                Logger.getLogger(SubmitMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        submitPage.add(viewPost,3,2);

        //======== Home page =================
        Button backToHome = new Button("Home Page");
        backToHome.setOnAction(e -> primaryStage.setScene(toHomePage.setupPage(primaryStage)));
        submitPage.add(backToHome, 4, 2);


        return scene;

    }

}
