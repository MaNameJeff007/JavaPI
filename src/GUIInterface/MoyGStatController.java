/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIInterface;

import Entities.Classe;
import Entities.Matiere;
import Entities.User;
import Services.ServiceBulletin;
import Services.ServiceCoeff;
import java.awt.Color;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class MoyGStatController implements Initializable {

    @FXML
    private ComboBox<String> classes;
    @FXML
    private Button RMG;
    @FXML
    private Button RT1;
    @FXML
    private Button RT2;
    @FXML
    private Button RT3;
    final ObservableList<String> classesL = FXCollections.observableArrayList();
    @FXML
    private TableView<User> listep;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, Float> moy;
    ObservableList<User> list;
    @FXML
    private TableColumn<User, String> libC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillcomboboxNiveau();

        classes.setItems(classesL);
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        //libC.setCellValueFactory(new PropertyValueFactory<>("LibC"));
        ServiceBulletin bl = new ServiceBulletin();

        listep.setItems(list);
    }

    public void fillcomboboxNiveau() {
        ServiceCoeff sr = new ServiceCoeff();
        List<Classe> res = sr.fillNiv();

        for (Classe e : res) {
            String idC = e.getId();
            String name = e.getLibelle();
            classesL.add(name);
        }

    }

    @FXML
    public void Pie(ActionEvent event) {
        classes.setItems(classesL);
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        moy.setCellValueFactory(new PropertyValueFactory<>("MoyG"));
        //libC.setCellValueFactory(new PropertyValueFactory<>("LibC"));
        ServiceBulletin bl = new ServiceBulletin();
        ServiceCoeff sr = new ServiceCoeff();

        list = FXCollections.observableArrayList(bl.GetMaxMoyC(classes.getSelectionModel().getSelectedItem()));

        listep.setItems(list);
    }

    @FXML
    public void PieChart(ActionEvent event) {

        ServiceBulletin bl = new ServiceBulletin();
         bl.ajouterMG();
        ServiceCoeff sr = new ServiceCoeff();
        List<Classe> h = sr.ComboxGetIdClasse(classes.getSelectionModel().getSelectedItem());
        for (Classe e : h) {
            String idC = e.getId();
            List p1 = bl.StatMGC1(idC);
            List p2 = bl.StatMGC2(idC);
            List p3 = bl.StatMGC3(idC);
            List p4 = bl.StatMGC4(idC);

            for (Iterator it = p1.iterator(); it.hasNext();) {
                float e1 = (float) it.next();
                System.out.println(e1);
                for (Iterator it1 = p2.iterator(); it1.hasNext();) {
                    float e2 = (float) it1.next();
                    for (Iterator it2 = p3.iterator(); it2.hasNext();) {
                        float e3 = (float) it2.next();
                        for (Iterator it3 = p4.iterator(); it3.hasNext();) {
                            float e4 = (float) it3.next();
                            DefaultPieDataset pieDataset = new DefaultPieDataset();
                            pieDataset.setValue("Moyennes inférieures à 10", e1);
                            pieDataset.setValue("Moyennes entre 10 et 12 ", e2);
                            pieDataset.setValue("Moyennes entre 13 et 15 ", e3);
                            pieDataset.setValue("Moyennes > 16 ", e4);

                            JFreeChart chart = ChartFactory.createPieChart3D("pie chart", pieDataset, true, true, true);
                            PiePlot3D P = (PiePlot3D) chart.getPlot();
                            ChartFrame frame = new ChartFrame("pie chart", chart);
                            frame.setVisible(true);
                            frame.setSize(800, 550);
                        }
                    }
                }
            }
        }

    }

    @FXML
    public void StatTR1(ActionEvent event) {

        ServiceBulletin bl = new ServiceBulletin();
        // bl.ajouterMG();
        ServiceCoeff sr = new ServiceCoeff();
        List<Classe> h = sr.ComboxGetIdClasse(classes.getSelectionModel().getSelectedItem());
        for (Classe e : h) {
           String idC = e.getId();
            List p1 = bl.StatMT1(idC, 1);
            List p2 = bl.StatMT2(idC, 1);
            List p3 = bl.StatMT3(idC, 1);

            for (Iterator it = p1.iterator(); it.hasNext();) {
                float e1 = (float) it.next();
                System.out.println(e1);
                for (Iterator it1 = p2.iterator(); it1.hasNext();) {
                    float e2 = (float) it1.next();
                    for (Iterator it2 = p3.iterator(); it2.hasNext();) {
                        float e3 = (float) it2.next();

                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        dataset.setValue(e1, "Moyennes inférieures à 10", "moyennes");
                        dataset.setValue(e2, "Moyennes entre 10 et 14 ", "moyennes");
                        dataset.setValue(e3, "Moyennes supérieures à 14 ", "moyennes");

                        JFreeChart chart = ChartFactory.createBarChart("Moyennes du trimestre 1 de la classe " + classes.getSelectionModel().getSelectedItem() + " ", "Pourcentage ", " Intervalles ", dataset, PlotOrientation.HORIZONTAL, true, true, true);

                        CategoryPlot P = (CategoryPlot) chart.getPlot();
                        P.setRangeGridlinePaint(Color.BLACK);
                        ChartFrame frame = new ChartFrame("pie chart", chart);
                        frame.setVisible(true);
                        frame.setSize(800, 550);

                    }
                }
            }
        }

    }

    @FXML
    public void StatTR2(ActionEvent event) {

        ServiceBulletin bl = new ServiceBulletin();
        // bl.ajouterMG();
        ServiceCoeff sr = new ServiceCoeff();
        List<Classe> h = sr.ComboxGetIdClasse(classes.getSelectionModel().getSelectedItem());
        for (Classe e : h) {
            String idC = e.getId();
            List p1 = bl.StatMT1(idC, 2);
            List p2 = bl.StatMT2(idC, 2);
            List p3 = bl.StatMT3(idC, 2);

            for (Iterator it = p1.iterator(); it.hasNext();) {
                float e1 = (float) it.next();
                System.out.println(e1);
                for (Iterator it1 = p2.iterator(); it1.hasNext();) {
                    float e2 = (float) it1.next();
                    for (Iterator it2 = p3.iterator(); it2.hasNext();) {
                        float e3 = (float) it2.next();

                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        dataset.setValue(e1, "Moyennes inférieures à 10", "moyennes");
                        dataset.setValue(e2, "Moyennes entre 10 et 14 ", "moyennes");
                        dataset.setValue(e3, "Moyennes supérieures à 14 ", "moyennes");

                        JFreeChart chart = ChartFactory.createBarChart("Moyennes du trimestre 2 de la classe " + classes.getSelectionModel().getSelectedItem() + " ", "Pourcentage ", " Intervalles ", dataset, PlotOrientation.HORIZONTAL, true, true, true);

                        CategoryPlot P = (CategoryPlot) chart.getPlot();
                        P.setRangeGridlinePaint(Color.BLACK);
                        ChartFrame frame = new ChartFrame("pie chart", chart);
                        frame.setVisible(true);
                        frame.setSize(800, 550);

                    }
                }
            }
        }

    }

    @FXML
    public void StatTR3(ActionEvent event) {

        ServiceBulletin bl = new ServiceBulletin();
        // bl.ajouterMG();
        ServiceCoeff sr = new ServiceCoeff();
        List<Classe> h = sr.ComboxGetIdClasse(classes.getSelectionModel().getSelectedItem());
        for (Classe e : h) {
            String idC = e.getId();
            List p1 = bl.StatMT1(idC, 3);
            List p2 = bl.StatMT2(idC, 3);
            List p3 = bl.StatMT3(idC, 3);

            for (Iterator it = p1.iterator(); it.hasNext();) {
                float e1 = (float) it.next();
                System.out.println(e1);
                for (Iterator it1 = p2.iterator(); it1.hasNext();) {
                    float e2 = (float) it1.next();
                    for (Iterator it2 = p3.iterator(); it2.hasNext();) {
                        float e3 = (float) it2.next();

                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        dataset.setValue(e1, "Moyennes inférieures à 10", "moyennes");
                        dataset.setValue(e2, "Moyennes entre 10 et 14 ", "moyennes");
                        dataset.setValue(e3, "Moyennes supérieures à 14 ", "moyennes");

                        JFreeChart chart = ChartFactory.createBarChart("Moyennes du trimestre 3 de la classe " + classes.getSelectionModel().getSelectedItem() + " ", "Pourcentage ", " Intervalles ", dataset, PlotOrientation.HORIZONTAL, true, true, true);

                        CategoryPlot P = (CategoryPlot) chart.getPlot();
                        P.setRangeGridlinePaint(Color.BLACK);
                        ChartFrame frame = new ChartFrame("pie chart", chart);
                        frame.setVisible(true);
                        frame.setSize(800, 550);

                    }
                }
            }
        }

    }

}
