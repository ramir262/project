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
import javafx.scene.control.Label;
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


public class CourseMenu {
    PantherInspectProject master;

    public CourseMenu(PantherInspectProject master){
        this.master = master;
    }

    /*
    ----------------------------------------
    function: setupPage
    ----------------------------------------
    params:
        Stage primaryStage
        String courseId
        String selectedSubject
    purpose:
        generate menu associated with course
            links to navigate to entire course, or specific instructors
    return:
        Scene
    */
    public Scene setupPage(Stage primaryStage, String courseId, String selectedSubject)
    {
        //setup pages
        SubjectMenu toSearchCoursePage = this.master.getSearchCoursePage();
        ViewPostPage toViewPost = this.master.getViewPost();

        //build table
        TableView<Data> table = new TableView<>();
        ObservableList<Data> tvObservableList = FXCollections.observableArrayList();

        GridPane displayCourse = new GridPane();

        VBox gridBox = new VBox();
        gridBox.setAlignment(Pos.CENTER);
        gridBox.getChildren().addAll(displayCourse, table);

        primaryStage.setTitle("Course Menu");
        Scene scene = new Scene(gridBox); //object to return

        //set table
        setTableAppearance(table);

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
        backButton.setOnAction(e -> primaryStage.setScene(toSearchCoursePage.setupPage(primaryStage, selectedSubject)));
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.getChildren().add(backButton);
        displayCourse.add(backButtonBox,0,0);

        //
        HBox viewHB = new HBox(10);
        //=============================================================================
        // Set action for Button to go to
        //=============================================================================
        
        Label starLbl = new Label("Average Rating: ");
        starLbl.setStyle("-fx-font-weight:bold");
        viewHB.getChildren().add(starLbl);

        String stars = this.master.qp.selectAvgOfCourse(courseId);
        if (stars == null) {
            stars = "N/A" + "    ";
        }

        Label avgStars = new Label(stars + "                                                                                            ");
        viewHB.getChildren().add(avgStars);

        Button viewAllProfessors = new Button("View all Professors");
        viewAllProfessors.setOnAction(e -> primaryStage.setScene(toViewPost.setupPage(primaryStage,selectedSubject,courseId,courseId,true)));
        viewHB.getChildren().add(viewAllProfessors);
        displayCourse.add(viewHB,0,2);
        return scene;

    }
    /*
    ----------------------------------------
    function: setTableAppearance
    ----------------------------------------
    params:
        TableView<Data> table
    purpose:
        setup table's appearance
    */
    public void setTableAppearance(TableView<Data> table) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(600);
        table.setPrefHeight(600);
    }

    /*
    ----------------------------------------
    function: fillTableObservableListWithData
    ----------------------------------------
    params:
        Observable<Data> tvObservableList
        String courseId
    purpose:
        fill table with data associated with course
            date of last post
            professor name
            average star count
    return:
        List classes
    */
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
            Logger.getLogger(CourseMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }

    /*
    ----------------------------------------
    function: addButtonToTable
    ----------------------------------------
    params:
        Stage primaryStage
        TableView<Data> table : table object which holds data
        List<String> classes
        ViewPost toViewPost
        String selectedSubject
        String courseId
    purpose:
        add button to right of each instance in table
            view professor
    */
    public void addButtonToTable(Stage primaryStage, TableView<Data> table, List<String> classes,
            ViewPostPage toViewPost, String selectedSubject, String courseId) {
        TableColumn<Data, Void> colBtn = new TableColumn("View More Info");

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<TableColumn<Data, Void>, TableCell<Data, Void>>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<Data, Void>() {

                    private final Button btn = new Button("Read More");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            primaryStage.setScene(toViewPost.setupPage(primaryStage,selectedSubject,classes.get(getIndex()),courseId, false));
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

}
