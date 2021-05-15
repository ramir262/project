/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 *
 * @author cindyramirez
 */
public class SubjectMenu
{
    PantherInspectProject master;
   
    List<HBox> buttonList = new ArrayList<>();

    String selectedOrder = "courseNum";

    public SubjectMenu(PantherInspectProject master)
    {
        this.master = master;
    }
    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
        String selectedSubject
    purpose:
        create a page which takes in a subject and outputs buttons associated with courses
        order courses
    return:
        Scene
    */
   public Scene setupPage(Stage primaryStage, String selectedSubject)
    {

      primaryStage.setTitle("Course Menu");
      GridPane coursesPage = new GridPane();
      coursesPage.setAlignment(Pos.TOP_LEFT);
      coursesPage.setHgap(15);
      coursesPage.setVgap(15);
      coursesPage.setGridLinesVisible(false);

      Label titleLbl = new Label(String.format("Welcome: %s", selectedSubject));
      coursesPage.add(titleLbl, 0,0);

      //add combobox to order findings
      Label orderLbl = new Label("Order");
      coursesPage.add(orderLbl,1,1);
      ComboBox comboBox = addOrderComboBox(coursesPage,primaryStage,selectedSubject);
      coursesPage.add(comboBox, 2, 1);

      // Back Button

      Button backButton = new Button("Back");

      HBox backButtonBox = new HBox(10);
      backButton.setOnAction(e -> primaryStage.setScene(master.getUserHomePage().setupPage(primaryStage)));
      backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
      backButtonBox.getChildren().add(backButton);
      coursesPage.add(backButtonBox,0,1);

      addClasses(coursesPage,primaryStage,selectedSubject,"courseNum");
      ScrollPane scroll = addScrollPane(coursesPage);

      Scene scene = new Scene(scroll, 800, 800);

      return scene;
    }

   /*
    ---------------------------
    function: addClasses
    ----------------------------
    params:
        GridPane grid
        Stage primaryStage
        String sort_by
            "courseNum" or "cName"
    purpose:
        call all classes in database
        setupPage course number and course name
        dynamically generate buttons

    */
   public void addClasses(GridPane grid, Stage primaryStage, String selectedSubject, String sort_by)  {
       //ourseNum, cname, courseId
       ResultSet rs = this.master.qp.selectCourseBySubject(selectedSubject,sort_by);
     
        try {
            int row = 0;
            int x = 0;
            int y = 2;
            while(rs.next()) {
                if (row % 4 == 0) {
                    x = 0;
                    y++;
                }
                else {
                    x++;
                }
                HBox hb = coursesButton(selectedSubject,rs.getString(1),rs.getString(2),rs.getString(3),grid,primaryStage,x,y);
                this.buttonList.add(hb);
                row++;

            }} catch (SQLException ex) {
                Logger.getLogger(SubjectMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

   /*
    ---------------------------
    function: deleteClasses
    ----------------------------
    params:
        GridPane grid
    purpose:
        remove all buttons from GUI

    */
   private void deleteClasses(GridPane grid) {

       for (HBox hb : this.buttonList) {
           grid.getChildren().remove(hb);
       }

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
       scroll.setPrefSize(120, 120);
       scroll.setContent(grid);
       scroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
       return scroll;
   }

   /*
    ---------------------------
    function: addOrderComboBox
    ----------------------------
    purpose:
        make buttons sortable
    return:
        ComboBox
    */

   public ComboBox addOrderComboBox(GridPane grid, Stage primaryStage,String selectedSubject) {
       ComboBox comboBox = new ComboBox();
       comboBox.getItems().add("Course Number");
       comboBox.getItems().add("Course Name");

       comboBox.setOnAction((event) -> {
           //if selection made:
           //   delete buttons
           //   replace with new order
            Object selectedItem = comboBox.getSelectionModel().getSelectedItem();

            if(selectedItem.equals("Course Number"))
            {
                deleteClasses(grid);
                addClasses(grid,primaryStage,selectedSubject,"courseNum");
            }
            else if(selectedItem.equals("Course Name"))
            {
                deleteClasses(grid);
                addClasses(grid,primaryStage,selectedSubject,"cName");
            }
        });


       return comboBox;
   }

   public HBox coursesButton(String selectedSubject, String courseNum, String courseName, String courseId, GridPane grid, Stage primaryStage, int x, int y)
   {
        Button btn = new Button((String.format("%s: %s", courseNum, courseName)));
        btn.setMinWidth(180);
        HBox hbBtn = new HBox(10);
        CourseMenu courseRatings = this.master.getCourseDisplay();
        btn.setOnAction(e -> primaryStage.setScene(courseRatings.setupPage(primaryStage, courseId, selectedSubject)));
        hbBtn.setAlignment(Pos.BASELINE_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, x, y);

        return hbBtn;
   }
}
