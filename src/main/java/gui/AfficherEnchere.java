/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Categorie;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class AfficherEnchere extends TableView {

    private PageAccueil vue;
    private Stage sAffichageEnchere;
    private Button bTest;
    private ObservableList<String> obTest;
    private ArrayList<String> lTest;
//    private ObservableList<Utilisateur> utilisateurs;

    public AfficherEnchere(PageAccueil vue) {
        this.vue = vue;
    }

    public void fenetreAffichageEnchere(ArrayList alEnchere) {
        TableView tvEncheres = new TableView();
        ArrayList<String> lTest = new ArrayList<>();
//        this.lTest = new ArrayList<String>();

//        this.utilisateurs = FXCollections.observableArrayList(utilisateurs);
//        lTest.add("1");
//        lTest.add("2");
//        lTest.add("3");
//        lTest.add("4");
//        lTest.add("5");
//        lTest.add("6");
//        lTest.add("7");
//        System.out.println(lTest);
        this.obTest = FXCollections.observableArrayList(alEnchere);
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        System.out.println(alEnchere);
//        this.bTest = new Button("sRubrique");

        TableColumn<Categorie, String> columIdArticle = new TableColumn<>("ID Article");
        TableColumn<Categorie, String> columnNom = new TableColumn<>("Nom");
        TableColumn<Categorie, String> columnPrix = new TableColumn<>("Prix");
        TableColumn<Categorie, String> columnDateDebut = new TableColumn<>("Date de début");
        TableColumn<Categorie, String> columnDateFin = new TableColumn<>("Date de fin");
        TableColumn<Categorie, String> columnDescCourte = new TableColumn<>("Description courte");
        TableColumn<Categorie, String> columnDescLongue = new TableColumn<>("Description longue");
        TableColumn<Categorie, String> columnModeEnvoi = new TableColumn<>("Mode d'envoi");

        columIdArticle.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        columnDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        columnDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        columnDescCourte.setCellValueFactory(new PropertyValueFactory<>("descriptionCourte"));
        columnDescLongue.setCellValueFactory(new PropertyValueFactory<>("descriptionLongue"));
        columnModeEnvoi.setCellValueFactory(new PropertyValueFactory<>("expedition"));

        columnPrix.setEditable(true);
        columnPrix.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrix.setOnEditCommit((CellEditEvent<Categorie, String> event) -> {
            TablePosition<Categorie, String> pos = event.getTablePosition();

            String newFullName = event.getNewValue();

//            int row = pos.getRow();
//            Categorie person = event.getTableView().getItems().get(row);
//
//            person.setFullName(newFullName);
            System.out.println("entré je suis");
        });

        tvEncheres.getColumns().addAll(columIdArticle, columnNom, columnPrix, columnDateDebut, columnDateFin, columnDescCourte, columnDescLongue, columnModeEnvoi);

        tvEncheres.setItems(obTest);
        VBox vbAfficherEnchere = new VBox(tvEncheres);
        tvEncheres.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
