/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;


import Services.ServiceMatiere;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ModifierMatiereController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField nbh;
    @FXML
    private Text nomL;
    @FXML
    private Text nbhL;
    @FXML
    private Text title;
    @FXML
    private Button Valider;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         nom.setText(AjouterMatiereController.Recup.getNom());
         nbh.setText(String.valueOf(AjouterMatiereController.Recup.getNbH()));
    }    
    
    @FXML   
private void validerModifMat(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modification");
                            alert.setHeaderText(null);
                            alert.setContentText("voulez vous vraiment modifier cette matiere ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {ServiceMatiere ac=new ServiceMatiere ();
        
      
        ac. modifierMatiere(AjouterMatiereController.Recup.getId(),nom.getText(),Integer.parseInt(nbh.getText())); // insertion dans la base de données
          try {
 
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterMatiereController.fxml"));
                                Parent root = loader.load();
                               AjouterMatiereController rc = loader.getController();
                                Valider.getScene().setRoot(root);
                             
                            
                               
             
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }
         
                            }
   
    }

}
