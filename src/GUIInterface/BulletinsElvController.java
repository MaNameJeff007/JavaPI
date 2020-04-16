/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import Entities.User;
import static GUIInterface.AfficherClassesController.Recup;
import Services.ServiceBulletin;
import Services.ServiceClasse;
import Services.ServiceCoeff;
import Services.ServiceUser;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BulletinPdf;
import com.itextpdf.text.pdf.testPdf;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class BulletinsElvController implements Initializable {

    @FXML
    private Text classeLabel;
    @FXML
    private TableView<User> TabEl;
    
    private TableColumn<User, Integer> Id;
    @FXML
    private TableColumn<User, String> Nom;
    @FXML
    private TableColumn<User, String> Prenom;
    private TableColumn<User, Integer> Classe;
    
    private TableColumn<User, Integer> ParentNom;
    @FXML
    private Text BulletinsTitle;
    @FXML
    private ComboBox<String> ComboClasses;
     ObservableList<User> Elvlist ;
     final ObservableList<String> liste = FXCollections.observableArrayList();
      static User Recup ;
    @FXML
    private TableColumn<User, String> libC;
    @FXML
    private Button Retour;
    @FXML
    private TableColumn<User, String> DateInscription;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
          libC.setCellValueFactory(new PropertyValueFactory<>("Classeeleve_id"));
         // NomParent.setCellValueFactory(new PropertyValueFactory<>("NomParent"));
        // PrenomParent.setCellValueFactory(new PropertyValueFactory<>("PrenomParent"));
         DateInscription.setCellValueFactory(new PropertyValueFactory<>("Date_Inscription"));
        // EmailParent.setCellValueFactory(new PropertyValueFactory<>("EmailParent"));
         
     
         addBullentinsButtonsToTable();
       
         fillcomboboxNiveau();
      
        ComboClasses.setItems(liste);
      
                User a=new User();

        ServiceUser ac =new ServiceUser();
       Elvlist=FXCollections.observableArrayList(ac.afficherEleves());
      
        
        TabEl.setItems(Elvlist);
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
    
    
    private void testAff(ActionEvent event) {
      // Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        libC.setCellValueFactory(new PropertyValueFactory<>("Classeeleve_id"));
       // NomParent.setCellValueFactory(new PropertyValueFactory<>("NomParent"));
        // PrenomParent.setCellValueFactory(new PropertyValueFactory<>("PrenomParent"));
      
         DateInscription.setCellValueFactory(new PropertyValueFactory<>("Date_Inscription"));
       //  EmailParent.setCellValueFactory(new PropertyValueFactory<>("EmailParent"));
         
        ServiceUser ac =new ServiceUser();
       Elvlist=FXCollections.observableArrayList(ac.afficherEleves());
      
        TabEl.setItems(Elvlist);
}

    public void fillcomboboxNiveau()
    {
        ServiceCoeff sr=new   ServiceCoeff();
        List<Classe> res = sr.fillNiv();
        
          for (Classe e:res)
    {
        String idC=e.getId();
        String name=e.getLibelle();
        liste.add(name);
    }
  
    }
 
    @FXML
    public void recher(ActionEvent event) {
         
       // Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
          libC.setCellValueFactory(new PropertyValueFactory<>("Classeeleve_id"));
        
        //NomParent.setCellValueFactory(new PropertyValueFactory<>("NomParent"));
       //  PrenomParent.setCellValueFactory(new PropertyValueFactory<>("PrenomParent"));
     
         DateInscription.setCellValueFactory(new PropertyValueFactory<>("Date_Inscription"));
        // EmailParent.setCellValueFactory(new PropertyValueFactory<>("EmailParent"));
         
        ServiceUser ac =new ServiceUser();
        
    
       Elvlist=FXCollections.observableArrayList(ac.rechercherEleveParClasse(ComboClasses.getSelectionModel().getSelectedItem()));
       TabEl.setItems(Elvlist); 
  
}
  
    
     private void addBullentinsButtonsToTable() {
        TableColumn<User, Void> colBtn = new TableColumn("Bulletin Du Trimestre 1 ");
        TableColumn<User, Void> colBtn1 = new TableColumn("Bulletin Du Trimestre 2 ");
         TableColumn<User, Void> colBtn3 = new TableColumn("Bulletin Du Trimestre 3 ");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("B 1 ");
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                
                                    // ServiceSeance serv = new ServiceSeance();
                                    BulletinPdf serv = new  BulletinPdf();
                            
                            try {
                                serv.Bullentin(Recup.getIdentifiant(),1,Recup.getClasseeleve_id());
                                //serv.SelectSeance(Recup.getId());
                                //serv.OpenPdf(Recup.getId());
                            } catch (IOException ex) {
                                Logger.getLogger(BulletinsElvController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                       TrayNotification tray = new TrayNotification();
                       AnimationType type = AnimationType.POPUP;
                       tray.setAnimationType(type);
                       tray.setTitle("Etat de téléchargement");
                       tray.setMessage("Le bulletin du trimestre 1 de l'élève "+ Recup.getNom()+"  "+Recup.getPrenom()+" à été telechargé avec succes");
                       tray.setNotificationType(NotificationType.SUCCESS);
                       tray.showAndDismiss(Duration.millis(5000));
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

        
      Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory1 = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell1 = new TableCell<User, Void>() {

                    
                    private final Button btn1 = new Button("B 2 ");
                    

                    {
                        btn1.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                
                                    
                                    BulletinPdf serv = new  BulletinPdf();
                            
                            try {
                                serv.Bullentin(Recup.getIdentifiant(),2,Recup.getClasseeleve_id());
                               
                            } catch (IOException ex) {
                                Logger.getLogger(BulletinsElvController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                         TrayNotification tray = new TrayNotification();
                       AnimationType type = AnimationType.POPUP;
                       tray.setAnimationType(type);
                       tray.setTitle("Etat de téléchargement");
                       tray.setMessage("Le bulletin du trimestre 2 de l'élève "+ Recup.getNom()+"  "+Recup.getPrenom()+" à été telechargé avec succes");
                       tray.setNotificationType(NotificationType.SUCCESS);
                       tray.showAndDismiss(Duration.millis(5000));
                       
                        });
                        
                    
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn1);
                        }
                    }
                };
                return cell1;
            }
        };
  
        
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory2 = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    
                    private final Button btn3 = new Button("B 3 ");
                    

                    {
                        btn3.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                
                                    
                                    BulletinPdf serv = new  BulletinPdf();
                            
                            try {
                                serv.Bullentin(Recup.getIdentifiant(),3,Recup.getClasseeleve_id());
                               
                            } catch (IOException ex) {
                                Logger.getLogger(BulletinsElvController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                       TrayNotification tray = new TrayNotification();
                       AnimationType type = AnimationType.POPUP;
                       tray.setAnimationType(type);
                       tray.setTitle("Etat de téléchargement");
                       tray.setMessage("Le bulletin du trimestre 3 de l'élève "+ Recup.getNom()+"  "+Recup.getPrenom()+" à été telechargé avec succes");
                       tray.setNotificationType(NotificationType.SUCCESS);
                       tray.showAndDismiss(Duration.millis(5000));
                        });
                        
                    
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn3);
                        }
                    }
                };
                return cell;
            }
        };
        
        
        colBtn.setCellFactory(cellFactory);
         colBtn1.setCellFactory(cellFactory1);
          colBtn3.setCellFactory(cellFactory2);

        TabEl.getColumns().add(colBtn);
        TabEl.getColumns().add(colBtn1);
        TabEl.getColumns().add(colBtn3);
  
    }

    @FXML
    private void testAff(KeyEvent event) {
    }
  
}
