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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pantherinspectproject.userHomePage;

/**
 *
 * @author cindyramirez
 */
public class deletePost {
    PantherInspectProject master;
    String postId = "0";
    userHomePage toHomePage;
    SubmitCourseReview toPrevPage;
    //String BOLD = "<b>%s</b>";
    deletePost(PantherInspectProject master, String postId, SubmitCourseReview toPrevPage) {
        this.master = master;
        this.postId = postId;
        toHomePage = new userHomePage(master);
        this.toPrevPage = toPrevPage;
    }

    userHomePage user;


    public Scene deletePosting(Stage primaryStage)
    {

      primaryStage.setTitle("Delete Post ");
      GridPane deletepost = new GridPane();
      deletepost.setAlignment(Pos.CENTER);
      deletepost.setHgap(15);
      deletepost.setVgap(15);
      deletepost.setGridLinesVisible(false);

      //TODO: add scrollpane
      ResultSet rs = this.master.qp.selectPostByReviewId(this.postId, "ReviewId");
      int i = 1;
        try {

            while(rs.next()) {
                //ResultSet (subject, courseNum, cName, pName, stars, creation, edit)
                Label classLblBold = new Label("Class:");
                classLblBold.setStyle("-fx-font-weight: bold");
                Label classLbl = new Label(String.format("%s %s: %s", rs.getString(1), rs.getString(2), rs.getString(3)));
                HBox classBox = new HBox(classLblBold,classLbl);
                deletepost.add(classBox,0,i++);

                Label profLblBold = new Label("Professor:");
                profLblBold.setStyle("-fx-font-weight: bold");
                Label profLbl = new Label(rs.getString(4));
                HBox profBox = new HBox(profLblBold,profLbl);
                deletepost.add(profBox,0,i++);

                HBox starGrid = createStars(rs.getInt(5));
                deletepost.add(starGrid,0,i++);

                String create = rs.getString(6);
                String edit = rs.getString(7);
                Label createLbl = new Label(create);
                deletepost.add(createLbl,0,i++);
                if (!create.equals(edit)) {
                    Label editLblBold = new Label("Edit:");
                    editLblBold.setStyle("-fx-font-weight: bold");
                    Label editLbl = new Label(edit);
                    HBox editBox = new HBox(editLblBold,editLbl);
                    deletepost.add(editBox,0,i++);
                }

                ResultSet rs2 = this.master.qp.selectReviewQuestions(this.postId);

                while(rs2.next()) {
                    Label quesLbl = new Label(rs2.getString(1));
                    quesLbl.setStyle("-fx-font-weight: bold");
                    deletepost.add(quesLbl,0,i++);
                    Label resLbl = new Label(rs2.getString(2));
                    deletepost.add(resLbl,0,i++);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch( FileNotFoundException ex) {
            Logger.getLogger(PantherInspectProject.class.getName()).log(Level.SEVERE, null, ex);
        }

        Button delBtn = new Button("Delete");
        delBtn.setOnAction((ActionEvent e)-> {
            this.master.qp.deleteReview(this.postId);
            primaryStage.setScene(toHomePage.userpage(primaryStage));
        });
        deletepost.add(delBtn,0,i++);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> primaryStage.setScene(toPrevPage.submitReview(primaryStage)));
        deletepost.add(cancelBtn,0,i++);

      Scene scene = new Scene(deletepost, 800, 800);

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

}
