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
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.H;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author cindyramirez
 */
public class searchCoursePage 
{
    PantherInspectProject master;
    displayCourseRatings courseRatings = new displayCourseRatings(this);
    
    List<HBox> buttonList = new ArrayList<>();
    String selectedCourse = "";
    String selectedOrder = "courseNum";
    
    public searchCoursePage(PantherInspectProject master, String selectedCourse)
    {
        this.master = master;
        this.selectedCourse = selectedCourse;
    }
   public Scene toSearchCourse(Stage primaryStage)
    {
      primaryStage.setTitle("Search Courses ");
      GridPane coursesPage = new GridPane();
      coursesPage.setAlignment(Pos.TOP_LEFT);
      coursesPage.setHgap(15);
      coursesPage.setVgap(15);
      coursesPage.setGridLinesVisible(false);
      
      Label titleLbl = new Label(String.format("Welcome: %s", this.selectedCourse));
      coursesPage.add(titleLbl, 0,0);
      
      //add combobox to order findings
      Label orderLbl = new Label("Order");
      coursesPage.add(orderLbl,1,1);
      ComboBox comboBox = addOrderComboBox(coursesPage,primaryStage);
      coursesPage.add(comboBox, 2, 1);
      
      addClasses(coursesPage,primaryStage,"courseNum");
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
        display course number and course name
        dynamically generate buttons
    
    */
   public void addClasses(GridPane grid, Stage primaryStage, String sort_by)  {
       ResultSet rs = this.master.qp.selectCourseBySubject(this.selectedCourse,sort_by);
       
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
                HBox hb = coursesButton(rs.getString(1),rs.getString(2),grid,primaryStage,x,y);
                this.buttonList.add(hb);
                row++;
                
            }} catch (SQLException ex) {
                Logger.getLogger(searchCoursePage.class.getName()).log(Level.SEVERE, null, ex);
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
   
   public ComboBox addOrderComboBox(GridPane grid, Stage primaryStage) {
       ComboBox comboBox = new ComboBox();
       comboBox.getItems().add("Course Number");
       comboBox.getItems().add("Course Name");
       
       comboBox.setOnAction((event) -> {
           //if selection made:
           //   delete buttons
           //   replace with new order
            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
            if(selectedItem.equals("Course Number"))
            {
                deleteClasses(grid);
                addClasses(grid,primaryStage,"courseNum");
            }
            else if(selectedItem.equals("Course Name"))
            {
                deleteClasses(grid);
                addClasses(grid,primaryStage,"cName");
            }
        });
       
       
       return comboBox;
   }
   
   public HBox coursesButton(String courseNum, String courseName, GridPane grid, Stage primaryStage, int x, int y)
   {
        Button btn = new Button((String.format("%s: %s", courseNum, courseName)));
        btn.setMinWidth(180);
        HBox hbBtn = new HBox(10);
        btn.setOnAction(e -> primaryStage.setScene(courseRatings.display(primaryStage)));
        hbBtn.setAlignment(Pos.BASELINE_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, x, y);
        
        return hbBtn;
   }
}
