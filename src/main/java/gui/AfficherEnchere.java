/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author drumm
 */
public class AfficherEnchere {

    private PageAccueil vue;
    private Stage sAffichageEnchere;
    private Button bTest;

    public AfficherEnchere(PageAccueil vue) {
        this.vue = vue;
    }

    public void fenetreAffichageEnchere(String sRubrique) {
        TableView tvEncheres = new TableView();
        this.bTest = new Button("sRubrique");

        TableColumn<String, String> columnNom = new TableColumn<String, String>("Nom");
        TableColumn<String, String> columnPrix = new TableColumn<String, String>("Prix");
        TableColumn<String, String> columnDateDebut = new TableColumn<String, String>("Date de début");
        TableColumn<String, String> columnDateFin = new TableColumn<String, String>("Date de fin");
        TableColumn<String, String> columnDescCourte = new TableColumn<String, String>("Description courte");
        TableColumn<String, String> columnDescLongue = new TableColumn<String, String>("Description longue");
        TableColumn<String, String> columnModeEnvoi = new TableColumn<String, String>("Mode d'envoi");

//        columnNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
//        columnPrix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
//        columnDateDebut.setCellValueFactory(new PropertyValueFactory<>("Date de début"));
//        columnDateFin.setCellValueFactory(new PropertyValueFactory<>("Date de fin"));
//        columnDescCourte.setCellValueFactory(new PropertyValueFactory<>("Description courte"));
//        columnDescLongue.setCellValueFactory(new PropertyValueFactory<>("Description longue"));
//        columnModeEnvoi.setCellValueFactory(new PropertyValueFactory<>("Mode d'envoi"));

        tvEncheres.getColumns().addAll(columnNom, columnPrix, columnDateDebut, columnDateFin, columnDescCourte, columnDescLongue, columnModeEnvoi);
//        tvEncheres.getItems().add(new Utilisateur("testMail", "TestMDP", "FR-13250", "TestNom", "TestPrenom", 1));

        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(tvEncheres);

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Nom");
        tvEncheres.setItems(list);
//        columnNom.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>);
//        tvEncheres.getColumns().addAll("Nom"+"Prix"+"Date Début"+"Date Fin"+"Descrip Courte"+"Descrip longue"+"Mode Envoi");

//        ObservableList<UserAccount> list = getUserList();
//        tvEncheres.setItems(list);
        VBox vbAfficherEnchere = new VBox(root);

        sAffichageEnchere = new Stage();
        Scene sTemp = new Scene(vbAfficherEnchere);
        sAffichageEnchere.setScene(sTemp);
        sAffichageEnchere.show();

    }

//    private ObservableList<UserAccount> getUserList() {
//
//        UserAccount user1 = new UserAccount(1L, "smith", "smith@gmail.com", //
//                "Susan", "Smith", true);
//        UserAccount user2 = new UserAccount(2L, "mcneil", "mcneil@gmail.com", //
//                "Anne", "McNeil", true);
//        UserAccount user3 = new UserAccount(3L, "white", "white@gmail.com", //
//                "Kenvin", "White", false);
//
//        ObservableList<UserAccount> list = FXCollections.observableArrayList(user1, user2, user3);
//        return list;
//    }
}
