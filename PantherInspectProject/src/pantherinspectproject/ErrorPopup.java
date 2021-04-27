/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import javafx.scene.control.Alert;

/**
 *
 * @author mmatt
 */
public class ErrorPopup {

    public static void Pop(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error has Occurred.");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
    
}
