/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import Entities.Coeff;
import Entities.Salle;
import static GUIInterface.AjouterSalleController.Recup;
import Services.ServiceClasse;
import Services.ServiceCoeff;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AfficherCoeffController implements Initializable {

    @FXML
    private TableView<Coeff> ListeCoeff;
    private TableColumn<Coeff, Integer> Id;
    @FXML
    private TableColumn<Coeff, Integer> niveau;
    @FXML
    private TableColumn<Coeff, Integer> matiere;
    @FXML
    private TableColumn<Coeff, Integer> valeur;
    @FXML
    private Button AjouterCoeff;
    @FXML
    private Button Retour;
    @FXML
    private Text TitreCoeffs;

    
    ObservableList<Coeff> coefflist ;
    static Coeff Recup;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        matiere.setCellValueFactory(new PropertyValueFactory<>("NomMatiere"));
        valeur.setCellValueFactory(new PropertyValueFactory<>("Valeur"));
        addButtonUpdateToTable();
        delete();
        ServiceCoeff ac= new  ServiceCoeff();
       coefflist=FXCollections.observableArrayList(ac.afficherCoeff());
      
        ListeCoeff.setItems(coefflist);
    }    
  
    @FXML
    private void testAff(KeyEvent event) {
        //Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        matiere.setCellValueFactory(new PropertyValueFactory<>("NomMatiere"));
        valeur.setCellValueFactory(new PropertyValueFactory<>("Valeur"));
        
        ServiceCoeff ac= new  ServiceCoeff();
        coefflist=FXCollections.observableArrayList(ac.afficherCoeff());
      
        ListeCoeff.setItems(coefflist);  
}
    
  @FXML
    private void redirectionAjC(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AjouterCoeff.fxml"));
            Parent root= loader.load();
            AjouterCoeffController rc= loader.getController();
            
            AjouterCoeff.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
    }  
    
    
    private void delete() {
        TableColumn<Coeff, Void> colBtn = new TableColumn("Supprimer Coefficient");

        Callback<TableColumn<Coeff, Void>, TableCell<Coeff, Void>> cellFactory = new Callback<TableColumn<Coeff, Void>, TableCell<Coeff, Void>>() {
            @Override
            public TableCell<Coeff, Void> call(final TableColumn<Coeff, Void> param) {
                final TableCell<Coeff, Void> cell = new TableCell<Coeff, Void>() {

                    private final Button btn = new Button("Supprimer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           
                            Coeff art = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez vraiment supprimer la coefficient dont l'Id " + art.getId() +"> ?");
                            Optional<ButtonType> action = alert.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {
                                ServiceCoeff ac = new ServiceCoeff();
                                ac.supprimerCoeff(art.getId()); //supprimer T3amlet

                            }
                            try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCoeff.fxml"));
                                Parent root = loader.load();
                                AfficherCoeffController rc = loader.getController();
                                ListeCoeff.getScene().setRoot(root);
                                
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }
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
        

        ListeCoeff.getColumns().add(colBtn);
       

    }
  
    
  private void addButtonUpdateToTable() {
        TableColumn<Coeff, Void> colBtn = new TableColumn("Modifier Coefficient");

        Callback<TableColumn<Coeff, Void>, TableCell<Coeff, Void>> cellFactory = new Callback<TableColumn<Coeff, Void>, TableCell<Coeff, Void>>() {
            @Override
            public TableCell<Coeff, Void> call(final TableColumn<Coeff, Void> param) {
                final TableCell<Coeff, Void> cell = new TableCell<Coeff, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                 try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierCoeff.fxml"));
                                Parent root = loader.load();
                              ModifierCoeffController rc = loader.getController();
                               Scene sc = new Scene(root);
                               Stage second=new Stage();
                               second.setScene(sc);
                               second.show();
                             //  btn.getScene().setRoot(root);

                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
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
        

        ListeCoeff.getColumns().add(colBtn);

    }
  
    
  @FXML
    private void retourMenuA(ActionEvent event) {
          try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("menuA.fxml"));
            Parent root= loader.load();
            MenuAController rc= loader.getController();
            
            Retour.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
        
    }  
    
    
    
}
