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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author cindyramirez
 */


public class displayCourseRatings {
    PantherInspectProject master;
    
    

    public displayCourseRatings(PantherInspectProject master){
        this.master = master;
    }

    public Scene display(Stage primaryStage, String courseId, String selectedSubject)
    {
        searchCoursePage toSearchCoursePage = this.master.getSearchCoursePage();
        viewPost toViewPost = this.master.getViewPost();
        //build table
        TableView<Data> table = new TableView<>();
        ObservableList<Data> tvObservableList = FXCollections.observableArrayList();

        GridPane displayCourse = new GridPane(); 

        VBox gridBox = new VBox();
        gridBox.setAlignment(Pos.CENTER);
        gridBox.getChildren().addAll(displayCourse, table);

        primaryStage.setTitle("View All Postings");
        Scene scene = new Scene(gridBox); //object to return

        //set table
        setTableappearance(table);

        //populate table
        List<String> classes = fillTableObservableListWithData(tvObservableList,courseId);
        table.setItems(tvObservableList);


        TableColumn<Data, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("datePosted"));


        TableColumn<Data, String> colName = new TableColumn<>("Professor Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<Data, String> colStars = new TableColumn<>("Average Rating");
        colStars.setCellValueFactory(new PropertyValueFactory<>("avgRating"));

        table.getColumns().addAll(colDate, colName, colStars);

        addButtonToTable(primaryStage,table,classes,toViewPost,selectedSubject, courseId);


        // Back Button

      
        Button backButton = new Button("Back");
        HBox backButtonBox = new HBox(10);
        backButton.setOnAction(e -> primaryStage.setScene(toSearchCoursePage.toSearchCourse(primaryStage, selectedSubject)));
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.getChildren().add(backButton);
        displayCourse.add(backButtonBox,0,0);

        Button viewAllProfessors = new Button("View all Professors");
        viewAllProfessors.setOnAction(e -> primaryStage.setScene(toViewPost.viewPosting(primaryStage,selectedSubject,courseId,courseId,true)));
        //
        HBox viewHB = new HBox(10);
        //=============================================================================
        // Set action for Button to go to
        //=============================================================================
        //viewAllProfessors.setOnAction(e -> primaryStage.setScene(master.getViewPost().viewPosting(primaryStage, selectedCourse)));
        viewHB.setAlignment(Pos.CENTER);
        viewHB.getChildren().add(viewAllProfessors);
        displayCourse.add(viewHB,4,0);

        return scene;

}

     public void setTableappearance(TableView<Data> table) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(600);
        table.setPrefHeight(600);
    }

    public List fillTableObservableListWithData(ObservableList<Data> tvObservableList, String courseId) {
        List<String> classes = new ArrayList<>();

        try {
            ResultSet rs = this.master.qp.selectCourseProfessors(courseId);

            while (rs.next()) {
                //classid,subject,coursenum,cname,professorid,pname

                // get stars
                String date = this.master.qp.selectMaxPostTimestamp(rs.getString(1));
                String stars = this.master.qp.selectAvgOfClass(rs.getString(1));
                if (date == null) {
                    date = "N/A";
                    stars = "N/A";
                }

                tvObservableList.add(new Data(date,rs.getString(6),stars));
                classes.add(rs.getString(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(displayCourseRatings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }

    public void addButtonToTable(Stage primaryStage, TableView<Data> table, List<String> classes, viewPost toViewPost, String selectedSubject, String courseId) {
        TableColumn<Data, Void> colBtn = new TableColumn("View More Info");

        
        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

                    private final Button btn = new Button("Read More");

                    {

                        btn.setOnAction((ActionEvent event) -> {
                            Data data = getTableView().getItems().get(getIndex());
                            primaryStage.setScene(toViewPost.viewPosting(primaryStage,selectedSubject,classes.get(getIndex()),courseId, false));
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
        private String avgRating;


        private Data(String datePosted, String name, String avgRating) {
            this.datePosted = datePosted;
            this.name = name;
            this.avgRating = avgRating;
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

        public String getAvgRating() {
            return avgRating;

        }

        public void setAvgRatings(String sr) {
            this.avgRating = sr;
        }


        @Override
        public String toString() {
            return "date posted:" + datePosted + " name: " + name + " stars: " + avgRating;
        }

    }

}
