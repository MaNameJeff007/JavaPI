/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIController.EdTech;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import Entities.Test;
import Services.TestService;

/**
 * FXML Controller class
 *
 * @author 21654
 */
public class TestViewController implements Initializable {

    @FXML
    private ObservableList data;
    @FXML
    private TableView<Test> tableview;
    @FXML
    private Button newBtn;
    @FXML
    private TextField questionText;
    @FXML
    private TextField reponseText;
    @FXML
    private TextField typeText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //   tableview = new TableView();
        getCol();
        buildData();

    }

    public void getCol() {
        try {

            //ResultSet
            TestService c = new TestService();
            ResultSet rs = c.getAllTestResultSet();

            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                if (i == 0) {
                    col.setVisible(false);
                }

                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
                if (tableview.getColumns().equals("id")){
  

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data : " + e.getMessage());
        }
    }

    public void buildData() {
        data = FXCollections.observableArrayList();
        try {
            //ResultSet
            TestService c = new TestService();
            ResultSet rs = c.getAllTestResultSet();
            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }
            /*          TestService cs = new TestService();
             List<Test> ca = cs.getAllTests();
             for(Test b : ca){
             data.add(b);
             }
             */
            //FINALLY ADDED TO TableView
            tableview.setItems(data);

            tableview.setRowFactory(tv -> {
                TableRow<Test> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Object rowData = row.getItem();
                        System.out.println(rowData);
                        String[] test;
                        test = rowData.toString().split(",");
                        for (String s : test) {
                            System.out.println(s);
                        }
                        questionText.setText(test[1]);
                        reponseText.setText(test[2]);
                        typeText.setText(test[3].substring(0, 2));

                    }
                });
                return row;
            });
           
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data : " + e.getMessage());
        }
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        // I really don't recommend using a single handler like this,
        // but it will work
        int type;
        type = Integer.parseInt(typeText.getText().trim());
        if (type < 0 | type > 3) {
            return;
        }
        Test c = new Test(questionText.getText(), reponseText.getText(), type);
        TestService cs = new TestService();
        try {
            cs.ajouterTest(c);
            System.out.println("here");
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    private void handleDeleteClick(ActionEvent event) {
        Object person = tableview.getSelectionModel().getSelectedItem();
        //   Test course = (Test)person;
        String[] s;
        s = person.toString().split(",");
        String id;
        id = s[0].substring(1);
        TestService cs = new TestService();
        int id2;
        id2 = Integer.parseInt(id);

        try {
            cs.DeleteTest(id2);
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    private void handleUpdateClick(ActionEvent event) {
        Object person = tableview.getSelectionModel().getSelectedItem();
        //   Test course = (Test)person;
        String[] s;
        int type;
        s = person.toString().split(",");
        String id;
        id = s[0].substring(1).trim();
        TestService cs = new TestService();
        int id2;
        id2 = Integer.parseInt(id);
        type = Integer.parseInt(typeText.getText().trim());
        System.out.println("question : " + questionText.getText());
        System.out.println("reponse : " + reponseText.getText());
        System.out.println("score " + typeText.getText());
        System.out.println("id " + id2);

        Test c = new Test(id2, questionText.getText(), reponseText.getText(), type);

        try {
            cs.UpdateTest(c);
        } catch (Exception e) {
            System.out.println(e);
        }
        RefreshTable();
    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
        {
            System.out.println(tableview.getSelectionModel().getSelectedItem());

        }
    }

    private void RefreshTable() {
        /*  tableview.getColumns().get(0).setVisible(false);
         tableview.getColumns().get(0).setVisible(true);
         data.removeAll(data);
         //  tableview.refresh();
         buildData();
         */
        data.clear();
        buildData();
    }

}
