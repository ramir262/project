/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherinspectproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author allis
 */
public class SplashScreenLoader extends Preloader {
    private Stage splashScreen;

    @Override
    public void start(Stage stage) throws Exception {
        splashScreen = stage;
        splashScreen.setScene(createScene());
        splashScreen.show();

    }

    public Scene createScene() throws FileNotFoundException {
        //creating the image object
        InputStream stream = new FileInputStream("SplashScreen.png");
        Image image = new Image(stream);
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(image);
        //Setting the image view parameters
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        
        
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 570, 125);
        return scene;
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
        //TODO: fix error where it does not get notification to hide
        if (notification instanceof StateChangeNotification) {
            splashScreen.hide();
        }
        
    }
}
