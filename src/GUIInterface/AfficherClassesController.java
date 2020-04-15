/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import GUIController.BacKController;
import GUIInterface.AjouterClasseController;
import GUIInterface.MenuAController;
import GUIInterface.ModifierClasseController;
import Services.ServiceClasse;
import Services.ServiceSeance;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.MyListener;
import com.itextpdf.text.pdf.testPdf;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author dell
 */
public class AfficherClassesController implements Initializable {

    @FXML
    private TableView<Classe> tableClasses;
    
    private TableColumn<Classe, String> id;
    @FXML
    private TableColumn<Classe, String> libelle;
    @FXML
    private TableColumn<Classe, Integer> capacite;
    @FXML
    private TableColumn<Classe, Integer> niveau;
    private Button RetourC;
    @FXML
    private Button AjouterC;
    @FXML
    private Text listeC;
    @FXML
    private TextField RechC;
    @FXML
    private Text libR;
    @FXML
    private Line line;
     
   static Classe Recup ;
    
ObservableList<Classe> classeslist ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        capacite.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
       
          
         addButtonUpdateToTable();
         addEmploiButtonToTable();
         delete();
        ServiceClasse ac =new ServiceClasse();
        classeslist=FXCollections.observableArrayList(ac.afficherClasse());
      
        tableClasses.setItems(classeslist);
      
    }    

    @FXML
    public void recher(ActionEvent event) {
         
      //  id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        capacite.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
      
            ServiceClasse ac =new ServiceClasse();
        classeslist=FXCollections.observableArrayList(ac.rechercherParLibelle(RechC.getText()));
      
        tableClasses.setItems(classeslist);
  
}
    
 
    private void retourMenuA(ActionEvent event) {
          try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("BacK.fxml"));
            Parent root= loader.load();
            MenuAController rc= loader.getController();
            
            RetourC.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
        
    }    
    
    
    private void testAff(ActionEvent event) {
       // id.setCellValueFactory(new PropertyValueFactory<>("id"));
      libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            capacite.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
       
        ServiceClasse ac =new ServiceClasse();
       
         classeslist=FXCollections.observableArrayList(ac.afficherClasse());        
        tableClasses.setItems(classeslist);
                
}
    
    
     @FXML
    private void redirectionAjC(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("AjouterClasse.fxml"));
            Parent root= loader.load();
            AjouterClasseController rc= loader.getController();
            Scene sc = new Scene(root);
                               Stage second=new Stage();
                               second.setScene(sc);
                               second.show();
          //  AjouterC.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
    }
  
  
  private void addButtonUpdateToTable() {
        TableColumn<Classe, Void> colBtn = new TableColumn("Modifier Classe");

        Callback<TableColumn<Classe, Void>, TableCell<Classe, Void>> cellFactory = new Callback<TableColumn<Classe, Void>, TableCell<Classe, Void>>() {
            @Override
            public TableCell<Classe, Void> call(final TableColumn<Classe, Void> param) {
                final TableCell<Classe, Void> cell = new TableCell<Classe, Void>() {
                     
        //Image image = new Image("image/icons8-update-64.png",25,25,false,false);
        //ImageView imageView = new ImageView(image);

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                 try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierClasse.fxml"));
                                Parent root = loader.load();
                              ModifierClasseController rc = loader.getController();
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
        

        tableClasses.getColumns().add(colBtn);

    }
      
  private void addEmploiButtonToTable() {
        TableColumn<Classe, Void> colBtn = new TableColumn("Emploi de temps ");

        Callback<TableColumn<Classe, Void>, TableCell<Classe, Void>> cellFactory = new Callback<TableColumn<Classe, Void>, TableCell<Classe, Void>>() {
            @Override
            public TableCell<Classe, Void> call(final TableColumn<Classe, Void> param) {
                final TableCell<Classe, Void> cell = new TableCell<Classe, Void>() {

                    private final Button btn = new Button("Consulter ");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Recup =getTableView().getItems().get(getIndex());
                                
                                    // ServiceSeance serv = new ServiceSeance();
                                    testPdf serv = new  testPdf();
                            try {
                                //serv.SelectSeance(Recup.getId());
                                serv.OpenPdf(Recup.getId());
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
        

        tableClasses.getColumns().add(colBtn);
        
       
        

    }
  
  
  
   private void delete() {
        TableColumn<Classe, Void> colBtn = new TableColumn("Supprimer Classe");

        Callback<TableColumn<Classe, Void>, TableCell<Classe, Void>> cellFactory = new Callback<TableColumn<Classe, Void>, TableCell<Classe, Void>>() {
            @Override
            public TableCell<Classe, Void> call(final TableColumn<Classe, Void> param) {
                final TableCell<Classe, Void> cell = new TableCell<Classe, Void>() {
        // Image image = new Image("image/delete1.png",35, 35, false, false);
        Image image = new Image("images/icons8-delete-64.png",25,25,false,false);
        ImageView imageView = new ImageView(image);

                    private final Button btn = new Button("",imageView);
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           
                            Classe art = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez vraiment supprimer la classe dont l'Id " + art.getId() +"> ?");
                            Optional<ButtonType> action = alert.showAndWait();
                            
                            if (action.get() == ButtonType.OK) {
                                ServiceClasse ac = new ServiceClasse();
                                ac.supprimerClasse(art.getId());

                            }
                            
                           /* try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("BacK.fxml"));
                                Parent root = loader.load();
                                BacKController rc = loader.getController();
                               tableClasses.getScene().setRoot(root);
                                FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("BacK.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        
        stage.setScene(scene);
        stage.show();
        stage.toBack();
        ((Node) (event.getSource())).getScene().getWindow();
                                
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
        

        tableClasses.getColumns().add(colBtn);
       

    }

    @FXML
    private void testAff(KeyEvent event) {
         libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            capacite.setCellValueFactory(new PropertyValueFactory<>("Capacite"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
       
        ServiceClasse ac =new ServiceClasse();
       
         classeslist=FXCollections.observableArrayList(ac.afficherClasse());        
        tableClasses.setItems(classeslist);
    }
         
    
}
