/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Services.ServiceSalle;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ModifierSalleController implements Initializable {

    @FXML
    private Button modbutton;
    @FXML
    private TextField mod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          mod.setText(AjouterSalleController.Recup.getLibelle());
      
    }    
 
 @FXML   
private void validerModifSalle(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modification");
                            alert.setHeaderText(null);
                            alert.setContentText("voulez vous vraiment modifier cette salle ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {ServiceSalle ac=new ServiceSalle ();
        
      
        ac. modifierSalle(AjouterSalleController.Recup.getId(),mod.getText()); // insertion dans la base de données
          try {
 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterSalleController.fxml"));
                                Parent root = loader.load();
                               AjouterSalleController rc = loader.getController();
                                modbutton.getScene().setRoot(root);
                             
                            
                               
             
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }
         
                            }
   
    }

}
