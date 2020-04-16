/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Matiere;
import Entities.Salle;
import Services.ServiceMatiere;
import Services.ServiceSalle;
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
import javafx.scene.control.TextField;
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
public class AjouterMatiereController implements Initializable {

    @FXML
    private Text titre;
    @FXML
    private TextField Nom;
    @FXML
    private TextField NbH;
    @FXML
    private Text nomL;
    @FXML
    private Text nbhL;
    @FXML
    private TableView<Matiere> Matieres;
    private TableColumn<Matiere, Integer> id;
    @FXML
    private TableColumn<Matiere, String> nom;
    @FXML
    private TableColumn<Matiere, Integer> nbh;
    @FXML
    private Button ajouter;
    @FXML
    private Button retour;
    ObservableList<Matiere> list ;
 static Matiere Recup;
    @FXML
    private TextField RechC;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      // id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nbh.setCellValueFactory(new PropertyValueFactory<>("NbH"));
        ServiceMatiere ac =new ServiceMatiere();
        addButtonUpdateToTable();
        validersupp();
        
       list=FXCollections.observableArrayList(ac.afficherMatieres());
      
        Matieres.setItems(list);
    }    
   
    @FXML
    public void insertMatiere(ActionEvent event)
    {
        
        Matiere r = new Matiere();
        r.setNom(Nom.getText());
        r.setNbH(Integer.parseInt(NbH.getText()));
         ServiceMatiere sr=new   ServiceMatiere();
        sr.ajouterMatiere(r);
        
 }
    
   

    @FXML
    private void testAff(KeyEvent event) {
      // id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nbh.setCellValueFactory(new PropertyValueFactory<>("NbH"));
   ServiceMatiere ac =new ServiceMatiere();
        
        list=FXCollections.observableArrayList(ac.afficherMatieres());
      
        Matieres.setItems(list);
    }
    
    
     
 public void validersupp() 
  {
    
      TableColumn<Matiere, Void> colBtn = new TableColumn("Supprimer Matiere");

        Callback<TableColumn<Matiere, Void>, TableCell<Matiere, Void>> cellFactory = new Callback<TableColumn<Matiere, Void>, TableCell<Matiere, Void>>() {
            @Override
            public TableCell<Matiere, Void> call(final TableColumn<Matiere, Void> param) {
                final TableCell<Matiere, Void> cell = new TableCell<Matiere, Void>() {

                    private final Button btn = new Button("Supprimer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           
                            Matiere art = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez vraiment supprimer la Matiere dont l'Id " + art.getId() +"> ?");
                            Optional<ButtonType> action = alert.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {
                                ServiceMatiere ac = new ServiceMatiere();
                                ac.supprimerMatiere(art.getId()); //supprimer T3amlet

                            }
                            /*try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterMatiere.fxml"));
                                Parent root = loader.load();
                                AjouterMatiereController rc = loader.getController();
                               Matieres.getScene().setRoot(root);
                                
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
        

        Matieres.getColumns().add(colBtn);
       
  }
    
 
    @FXML
    public void recher(ActionEvent event) {
         
        
        //id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        nbh.setCellValueFactory(new PropertyValueFactory<>("NbH"));
   ServiceMatiere ac =new ServiceMatiere();
       list=FXCollections.observableArrayList(ac.rechercherParNom(RechC.getText()));
      
        Matieres.setItems(list);
      
 
}
    
  @FXML
    private void retourMenuA(ActionEvent event) {
          try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("BacK.fxml"));
            Parent root= loader.load();
            MenuAController rc= loader.getController();
            
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
        
    }  
    
private void addButtonUpdateToTable() {
        TableColumn<Matiere, Void> colBtn = new TableColumn("Supprimer Matiere");

        Callback<TableColumn<Matiere, Void>, TableCell<Matiere, Void>> cellFactory = new Callback<TableColumn<Matiere, Void>, TableCell<Matiere, Void>>() {
            @Override
            public TableCell<Matiere, Void> call(final TableColumn<Matiere, Void> param) {
                final TableCell<Matiere, Void> cell = new TableCell<Matiere, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                 try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierMatiere.fxml"));
                                Parent root = loader.load();
                              ModifierMatiereController rc = loader.getController();
                               Scene sc = new Scene(root);
                               Stage second=new Stage();
                               second.setScene(sc);
                               second.show();
                              // btn.getScene().setRoot(root);

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
        

        Matieres.getColumns().add(colBtn);

    }

  
     
    
    
}
