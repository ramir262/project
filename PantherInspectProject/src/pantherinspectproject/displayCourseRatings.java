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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author cindyramirez
 */


public class displayCourseRatings {
    PantherInspectProject master;
    searchCoursePage toSearchCoursePage;
    viewPost toViewPost;
    private final TableView<Data> table = new TableView<>();
    private final ObservableList<Data> tvObservableList = FXCollections.observableArrayList();
    
    public displayCourseRatings(PantherInspectProject master, searchCoursePage toSearchCoursePage){
        this.master = master;
        this.toSearchCoursePage = toSearchCoursePage;
        toViewPost = new viewPost(master);
    }
    
    public Scene display(Stage primaryStage, String courseId)
    {
        
        primaryStage.setTitle("Tableview with button column");
        Scene scene = new Scene(new Group(table)); //object to return
        //TODO: fix this
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        
        setTableappearance();
        
        List<String> classes = fillTableObservableListWithData(courseId);
        table.setItems(tvObservableList);
        
        
        TableColumn<Data, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("datePosted"));
        

        TableColumn<Data, String> colName = new TableColumn<>("Professor Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        
        TableColumn<Data, String> colStars = new TableColumn<>("Stars Rating");
        colStars.setCellValueFactory(new PropertyValueFactory<>("starRatings"));

        table.getColumns().addAll(colDate, colName, colStars);

        addButtonToTable(primaryStage,classes);
        
        
        // Back Button
      
      Button backButton = new Button("Back");
      HBox backButtonBox = new HBox(10);
      backButton.setOnAction(e -> primaryStage.setScene(toSearchCoursePage.toSearchCourse(primaryStage)));
      backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
      backButtonBox.getChildren().add(backButton);
      
      //displayCourse.add(backButtonBox,0,0);
        
        return scene;
    }
    
    public void setTableappearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(600);
        table.setPrefHeight(600);
    }
    
    public List fillTableObservableListWithData(String courseId) {
        List<String> classes = new ArrayList<>();
            
        try {
            ResultSet rs = this.master.qp.selectCourseProfessors(courseId);
            
            while (rs.next()) {
                //classid,subject,coursenum,cname,professorid,pname
                
                // get stars
                String date = this.master.qp.selectMaxPostTimestamp(rs.getString(1));
                String stars = this.master.qp.selectAvgOfClass(rs.getString(1));
                tvObservableList.add(new Data(date,rs.getString(6),stars));
                classes.add(rs.getString(1));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(displayCourseRatings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }
    
    public void addButtonToTable(Stage primaryStage, List<String> classes) {
        TableColumn<Data, Void> colBtn = new TableColumn("View More Info");

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

                    private final Button btn = new Button("Read More");

                    {
                        
                        btn.setOnAction((ActionEvent event) -> {
                            Data data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            primaryStage.setScene(toViewPost.viewPosting(primaryStage,classes.get(getIndex()),false));
                        });
                        
                        
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }

    public class Data {

        private String datePosted;
        private String name;
        private String starRatings;
       

        private Data(String datePosted, String name, String starRatings) {
            this.datePosted = datePosted;
            this.name = name;
            this.starRatings = starRatings;
        }
        
        public String getDatePosted() {
            return datePosted;
        }

        public void setDatePosted(String dp) {
            this.datePosted = dp;
        }


        public String getName() {
            return name;
        }

        public void setName(String nme) {
            this.name = nme;
        }
        
        public String getStarsRating() {
            return starRatings;
            
        }

        public void setStarsRatings(String sr) {
            this.starRatings = sr;
        }
        

        @Override
        public String toString() {
            return "date posted:" + datePosted + "name: " + name + "starsRating: " + starRatings;
        }

    }
    
   
}
