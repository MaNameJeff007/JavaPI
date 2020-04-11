/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;


import Entities.Classe;
import Entities.Coeff;
import Entities.Seance;
import Entities.User;
import static GUIInterface.AfficherClassesController.Recup;
import Services.ServiceClasse;
import Services.ServiceCoeff;
import Services.ServiceSeance;
import Services.ServiceUser;
import Utils.JavaMail;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
public class AfficherSeanceController implements Initializable {

    private TableColumn<Seance, Integer> Id;
   
    @FXML
    private TableColumn<Seance, String> Jour;
    @FXML
    private TableColumn<Seance, String> Hdeb;
    @FXML
    private TableColumn<Seance, String> Hfin;
    @FXML
    private Text SeancesTitle;
    @FXML
    private Text RechLabel;
    @FXML
    private ComboBox<String> ComboClasses;
   
    ObservableList<Seance> classeslist ;
    @FXML
    private TableView<Seance> SeancesTab;
    @FXML
    private Button Ajouter;
    @FXML
    private Button Retour;
    static Seance Recup;
    @FXML
    private TableColumn<Seance, String> nomEnseigant;
    @FXML
    private TableColumn<Seance, String> Niveau;
    @FXML
    private TableColumn<Seance, String> SalleCours;
    @FXML
    private TableColumn<Seance, String> nomMatiere;
    final ObservableList<String> liste = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    
     public void fillcomboboxNiveau()
    {
        ServiceCoeff sr=new   ServiceCoeff();
        List<Classe> res = sr.fillNiv();
        
          for (Classe e:res)
    {
        int idC=e.getId();
        String name=e.getLibelle();
        liste.add(name);
    }
  
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nomEnseigant.setCellValueFactory(new PropertyValueFactory<>("NomEnseigant"));
         Niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        SalleCours.setCellValueFactory(new PropertyValueFactory<>("SalleCours"));
        nomMatiere.setCellValueFactory(new PropertyValueFactory<>("NomMatiere"));
        Jour.setCellValueFactory(new PropertyValueFactory<>("Jour"));
        Hdeb.setCellValueFactory(new PropertyValueFactory<>("Hdeb"));
        Hfin.setCellValueFactory(new PropertyValueFactory<>("Hfin"));
        
        fillcomboboxNiveau();
        delete();
        ComboClasses.setItems(liste);
        
        addButtonUpdateToTable();
        ServiceSeance ac =new ServiceSeance();
         classeslist=FXCollections.observableArrayList(ac.afficherSeance());
      
        SeancesTab.setItems(classeslist);
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
  
  
    @FXML
    public void recher(ActionEvent event) {
         
        //Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nomEnseigant.setCellValueFactory(new PropertyValueFactory<>("NomEnseigant"));
        Niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        SalleCours.setCellValueFactory(new PropertyValueFactory<>("SalleCours"));
        nomMatiere.setCellValueFactory(new PropertyValueFactory<>("NomMatiere"));
        Jour.setCellValueFactory(new PropertyValueFactory<>("Jour"));
        Hdeb.setCellValueFactory(new PropertyValueFactory<>("Hdeb"));
        Hfin.setCellValueFactory(new PropertyValueFactory<>("Hfin"));
        
        ServiceSeance ac =new ServiceSeance();
         
      classeslist=FXCollections.observableArrayList(ac.rechercherSeanceParClasse(ComboClasses.getSelectionModel().getSelectedItem()));
     
        SeancesTab.setItems(classeslist);
        
    
       
  
}
    
 @FXML
    private void redirectionAjSeance(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AjouterSeance.fxml"));
            Parent root= loader.load();
           AjouterSeanceController rc= loader.getController();
            
            Ajouter.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
    }    
    
    
    
     private void addButtonUpdateToTable() {
        TableColumn<Seance, Void> colBtn = new TableColumn("Modifier Seance");

        Callback<TableColumn<Seance, Void>, TableCell<Seance, Void>> cellFactory = new Callback<TableColumn<Seance, Void>, TableCell<Seance, Void>>() {
            @Override
            public TableCell<Seance, Void> call(final TableColumn<Seance, Void> param) {
                final TableCell<Seance, Void> cell = new TableCell<Seance, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                 try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierSeance.fxml"));
                                Parent root = loader.load();
                                ModifierSeanceController rc = loader.getController();
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
        

        SeancesTab.getColumns().add(colBtn);

    }
    
    
   private void delete() {
        TableColumn<Seance, Void> colBtn = new TableColumn("Supprimer Seance");

        Callback<TableColumn<Seance, Void>, TableCell<Seance, Void>> cellFactory = new Callback<TableColumn<Seance, Void>, TableCell<Seance, Void>>() {
            @Override
            public TableCell<Seance, Void> call(final TableColumn<Seance, Void> param) {
                final TableCell<Seance, Void> cell = new TableCell<Seance, Void>(){

                    private final Button btn = new Button("Supprimer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           
                            Seance art = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez vraiment supprimer la seance dont l'Id " + art.getId() +"> ?");
                            Optional<ButtonType> action = alert.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {
                                ServiceSeance ac = new ServiceSeance();
                                ServiceUser ue = new ServiceUser();
                                 
                               List<User> emE= ue.sEns(art.getEnseignant());
                                 System.out.println(art.getEnseignant());
                               for ( User u : emE)
                                {
                               String emailEns = u.getEmail();
                               System.out.println(emailEns);
                                     try {
                                JavaMail.sendMail(emailEns ,"Suppression ");
                                } catch (Exception ex) {
                                 Logger.getLogger(AjouterSeanceController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                               
                                }
                                ac.supprimerSeance(art.getId()); 
                              
                             

                            }
                            try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherSeance.fxml"));
                                Parent root = loader.load();
                                AfficherSeanceController rc = loader.getController();
                                SeancesTab.getScene().setRoot(root);
                                
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
        

       SeancesTab.getColumns().add(colBtn);
       

    }

    @FXML
    private void testAff(KeyEvent event) {
        // Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nomEnseigant.setCellValueFactory(new PropertyValueFactory<>("NomEnseigant"));
        Niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        SalleCours.setCellValueFactory(new PropertyValueFactory<>("SalleCours"));
        nomMatiere.setCellValueFactory(new PropertyValueFactory<>("NomMatiere"));
        Jour.setCellValueFactory(new PropertyValueFactory<>("Jour"));
        Hdeb.setCellValueFactory(new PropertyValueFactory<>("Hdeb"));
        Hfin.setCellValueFactory(new PropertyValueFactory<>("Hfin"));
        
        ServiceSeance ac =new ServiceSeance();
         classeslist=FXCollections.observableArrayList(ac.afficherSeance());
      
        SeancesTab.setItems(classeslist);
    }
    
    
    
}
