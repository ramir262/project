/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;


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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author cindyramirez
 */


public class displayCourseRatings {
    PantherInspectProject master;
    public String selectedCourse = "";
    
    private final TableView<Data> table = new TableView<>();
    private final ObservableList<Data> tvObservableList = FXCollections.observableArrayList();

    public displayCourseRatings(PantherInspectProject master){
        this.master = master;
        
        TableColumn<Data, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        

        TableColumn<Data, String> colName = new TableColumn<>("Professor Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        
        TableColumn<Data, String> colStars = new TableColumn<>("Stars Rating");
        colStars.setCellValueFactory(new PropertyValueFactory<>("starRatings"));
        
        table.getColumns().addAll(colDate, colName, colStars);
        
        setTableappearance();
        addButtonToTable(master.primaryStage);
 
    }
    
    
    
    public Scene display(Stage primaryStage, String selectedCourse)
    {
        tvObservableList.removeAll();
        //table.getItems().removeAll(tvObservableList);
        this.selectedCourse = selectedCourse;
        GridPane displayCourse = new GridPane(); 
        table.getItems().clear();
        VBox gridBox = new VBox();
        gridBox.setAlignment(Pos.CENTER);
        gridBox.getChildren().addAll(displayCourse, table);
        
        
        primaryStage.setTitle("View Postings");
        Scene scene = new Scene(gridBox); //object to return
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        
        
        
        Button backButton = new Button("Back");
        HBox backButtonBox = new HBox(10);
        backButton.setOnAction(e -> primaryStage.setScene(master.getSearchCoursePage().toSearchCourse(primaryStage, selectedCourse)));
        backButtonBox.setAlignment(Pos.TOP_LEFT);
        backButtonBox.getChildren().add(backButton);
        displayCourse.add(backButtonBox,0,0);
        
        Button viewAllProfessors = new Button("View all Professors");
        HBox viewHB = new HBox(10);
        //=============================================================================
        // Set action for Button to go to
        //=============================================================================
        //viewAllProfessors.setOnAction(e -> primaryStage.setScene(master.getViewPost().viewPosting(primaryStage, selectedCourse)));
        viewHB.setAlignment(Pos.CENTER);
        viewHB.getChildren().add(viewAllProfessors);
        displayCourse.add(viewHB,14,9);
        
        
        fillTableObservableListWithSampleData();
        table.setItems(tvObservableList);
       
        //addButtonToTable();
        

        return scene;

}
    
     public void setTableappearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(600);
        table.setPrefHeight(600);
    }
     
     public void fillTableObservableListWithSampleData() {

        tvObservableList.addAll(new Data("string", "Boyd", "3"),
                                new Data("3/3/3", "LLedo", "3"), 
                                new Data("3/3/3", "linstead", "3"), 
                                new Data("string", "german", "3"),
                                new Data("3/3/3", "transue", "4"));
    }
     
     
     public void addButtonToTable(Stage primaryStage) {
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
                        });
                        
                         //=============================================================================
                        // Set action for Button to Read More students review of courses for specific professor 
                        //=============================================================================
                        //btn.setOnAction(e -> primaryStage.setScene(master.readMore.readMore(primaryStage, selectedCourse)));
                        
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