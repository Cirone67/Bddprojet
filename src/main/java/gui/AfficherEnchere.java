/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Utilisateur;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
//        this.bTest = new Button("sRubrique");

        TableColumn<Utilisateur, String> columnNom = new TableColumn<Utilisateur, String>("Nom");
        TableColumn<Utilisateur, String> columnPrix = new TableColumn<Utilisateur, String>("Prix");
        TableColumn<Utilisateur, String> columnDateDebut = new TableColumn<Utilisateur, String>("Date de début");
        TableColumn<Utilisateur, String> columnDateFin = new TableColumn<Utilisateur, String>("Date de fin");
        TableColumn<Utilisateur, String> columnDescCourte = new TableColumn<Utilisateur, String>("Description courte");
        TableColumn<Utilisateur, String> columnDescLongue = new TableColumn<Utilisateur, String>("Description longue");
        TableColumn<Utilisateur, String> columnModeEnvoi = new TableColumn<Utilisateur, String>("Mode d'envoi");

        columnNom.setCellValueFactory(new PropertyValueFactory<>("Article.designation"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("Enchere.prix"));
        columnDateDebut.setCellValueFactory(new PropertyValueFactory<>("Enchere.dateDebut"));
        columnDateFin.setCellValueFactory(new PropertyValueFactory<>("Enchere.dateFin"));
        columnDescCourte.setCellValueFactory(new PropertyValueFactory<>("Article.descriptionCourte"));
        columnDescLongue.setCellValueFactory(new PropertyValueFactory<>("Article.descriptionLongue"));
        columnModeEnvoi.setCellValueFactory(new PropertyValueFactory<>("Article.expedition"));

        tvEncheres.getColumns().addAll(columnNom, columnPrix, columnDateDebut, columnDateFin, columnDescCourte, columnDescLongue, columnModeEnvoi);

        tvEncheres.setItems(obTest);
        VBox vbAfficherEnchere = new VBox(tvEncheres);

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
