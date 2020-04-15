/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
/**
 *
 * @author dell
 */
public class MenuAdmin extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("/GUIInterface/BacK.fxml"));
              Scene scene = new Scene(root);

            //FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindowView.fxml"));
            //Parent root =  loader.load();
            primaryStage.setTitle("Service Scolarite");
                        //Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
