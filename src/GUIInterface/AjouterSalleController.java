/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import Entities.Salle;
import static GUIInterface.AfficherClassesController.Recup;
import Services.ServiceClasse;
import Services.ServiceSalle;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AjouterSalleController implements Initializable {

    @FXML
    private Text AjSalle;
    @FXML
    private Text label;
    @FXML
    private TableView<Salle> Salles;
    private TableColumn<Salle, Integer> id;
    @FXML
    private TableColumn<Salle, String> libelle;
    @FXML
    private Button Ajouter;
    @FXML
    private Button Retour;
ObservableList<Salle> list ;
    @FXML
    private TextField libL;
    static Salle Recup ;
    private TextField Modifier;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //  id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        ServiceSalle ac =new ServiceSalle();
        addButtonUpdateToTable();
        validersupp();
        
       list=FXCollections.observableArrayList(ac.afficherSalles());
      
        Salles.setItems(list);
    }    
    
    @FXML
    public void insertSalle(ActionEvent event)
    {
        
        Salle r = new Salle();
        r.setLibelle(libL.getText());
        
        ServiceSalle sr=new  ServiceSalle();
        sr.ajouterSalle(r);
        
 }
    
   

    @FXML
    private void testAff(KeyEvent event) {
       // id.setCellValueFactory(new PropertyValueFactory<>("id"));
      libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
    ServiceSalle ac =new ServiceSalle();
        
       list=FXCollections.observableArrayList(ac.afficherSalles());
      
        Salles.setItems(list);
    }
   
    
   @FXML
    private void retourMenuA(ActionEvent event) {
          try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("BacK.fxml"));
            Parent root= loader.load();
            MenuAController rc= loader.getController();
            
            Retour.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
        
    }    
    
    
    public void validersupp() 
  {
    
      TableColumn<Salle, Void> colBtn = new TableColumn("Supprimer Salle");

        Callback<TableColumn<Salle, Void>, TableCell<Salle, Void>> cellFactory = new Callback<TableColumn<Salle, Void>, TableCell<Salle, Void>>() {
            @Override
            public TableCell<Salle, Void> call(final TableColumn<Salle, Void> param) {
                final TableCell<Salle, Void> cell = new TableCell<Salle, Void>() {

                    private final Button btn = new Button("Supprimer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           
                            Salle art = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez vraiment supprimer la salle dont l'Id " + art.getId() +"> ?");
                            Optional<ButtonType> action = alert.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {
                                ServiceSalle ac = new ServiceSalle();
                                ac.supprimerSalle(art.getId()); //supprimer T3amlet

                            }
                            /*try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterSalle.fxml"));
                                Parent root = loader.load();
                                AjouterSalleController rc = loader.getController();
                               Salles.getScene().setRoot(root);
                                
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());

                            }*/
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
        

        Salles.getColumns().add(colBtn);
       
  }
    
 

private void addButtonUpdateToTable() {
        TableColumn<Salle, Void> colBtn = new TableColumn("Modifier Salle");

        Callback<TableColumn<Salle, Void>, TableCell<Salle, Void>> cellFactory = new Callback<TableColumn<Salle, Void>, TableCell<Salle, Void>>() {
            @Override
            public TableCell<Salle, Void> call(final TableColumn<Salle, Void> param) {
                final TableCell<Salle, Void> cell = new TableCell<Salle, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                 try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierSalle.fxml"));
                                Parent root = loader.load();
                              ModifierSalleController rc = loader.getController();
                               Scene sc = new Scene(root);
                               Stage second=new Stage();
                               second.setScene(sc);
                               second.show();
                               //btn.getScene().setRoot(root);

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
        

        Salles.getColumns().add(colBtn);

    }

    
}
