/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import GUIInterface.AfficherClassesController;
import Entities.Classe;
import Entities.User;
import static GUIInterface.AfficherClassesController.Recup;
import Services.ServiceCoeff;
import Services.ServiceUser;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.testPdf;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class EmploisEnsController implements Initializable {

    @FXML
    private TableView<User> ensT;
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    ObservableList<User> Elvlist ;
    @FXML
    private TableColumn<User, String> libC;
    @FXML
    private TableColumn<User, String> Email;
    @FXML
    private TableColumn<User, String> dateEmbauche;
    
    @FXML
    private Button Retour;
    @FXML
    private ComboBox<String> ComboClasses;
    final ObservableList<String> liste = FXCollections.observableArrayList();
  static User Recup;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
         libC.setCellValueFactory(new PropertyValueFactory<>("classeenseignant_id"));
         Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
          dateEmbauche.setCellValueFactory(new PropertyValueFactory<>("Date_Embauche"));
           fillcomboboxNiveau();
           addEmploiButtonToTable();
        ComboClasses.setItems(liste);
        
         ServiceUser ac =new ServiceUser();
      Elvlist=FXCollections.observableArrayList(ac.afficherEns1());
     
      
        
       ensT.setItems(Elvlist);
       
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
         nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
         libC.setCellValueFactory(new PropertyValueFactory<>("classeenseignant_id"));
         Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
          dateEmbauche.setCellValueFactory(new PropertyValueFactory<>("Date_Embauche"));
        ServiceUser ac =new ServiceUser();
        
    
       Elvlist=FXCollections.observableArrayList(ac.rechercherEns1ParClasse(ComboClasses.getSelectionModel().getSelectedItem()));
       ensT.setItems(Elvlist); 
  
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
    
 private void addEmploiButtonToTable() {
        TableColumn<User, Void> colBtn = new TableColumn("Emploi de temps ");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Consulter ");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                
                                    // ServiceSeance serv = new ServiceSeance();
                                    testPdf serv = new  testPdf();
                            try {
                                //serv.SelectSeance(Recup.getId());
                                serv.OpenPdfEns(Recup.getIdentifiant());
                                if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("C:\\Users\\dell\\Documents\\NetBeansProjects\\EcoleTest_Java\\test.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
               System.err.println(ex);
            }
        }   
                                 
                            } catch (DocumentException ex) {
                                Logger.getLogger(AfficherClassesController.class.getName()).log(Level.SEVERE, null, ex);
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
        

        ensT.getColumns().add(colBtn);
   
    }
  
    
    
    
}
