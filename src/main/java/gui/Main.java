/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author drumm
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
      
/*        scene = new Scene(new PageAccueil(primaryStage));
        primaryStage.setScene(scene);
        primaryStage.show();  
*/  
        scene = new Scene(new PageConnection(primaryStage));
        //scene.getStylesheets().add(getClass().getResource("Apparence.css").toString()); 
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}
