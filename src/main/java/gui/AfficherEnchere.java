/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        this.bTest = new Button (sRubrique);

        TableColumn<Utilisateur, String> columnNom = new TableColumn<Utilisateur, String>("Nom");
        TableColumn<Utilisateur, String> columnPrix = new TableColumn<Utilisateur, String>("Prix");
        TableColumn<Utilisateur, String> columnDateDebut = new TableColumn<Utilisateur, String>("Date de début");
        TableColumn<Utilisateur, String> columnDateFin = new TableColumn<Utilisateur, String>("Date de fin");
        TableColumn<Utilisateur, String> columnDescCourte = new TableColumn<Utilisateur, String>("Description courte");
        TableColumn<Utilisateur, String> columnDescLongue = new TableColumn<Utilisateur, String>("Description longue");
        TableColumn<Utilisateur, String> columnModeEnvoi = new TableColumn<Utilisateur, String>("Mode d'envoi");

        columnNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        columnDateDebut.setCellValueFactory(new PropertyValueFactory<>("Date de début"));
        columnDateFin.setCellValueFactory(new PropertyValueFactory<>("Date de fin"));
        columnDescCourte.setCellValueFactory(new PropertyValueFactory<>("Description courte"));
        columnDescLongue.setCellValueFactory(new PropertyValueFactory<>("Description longue"));
        columnModeEnvoi.setCellValueFactory(new PropertyValueFactory<>("Mode d'envoi"));

        tvEncheres.getColumns().addAll(columnNom, columnPrix, columnDateDebut, columnDateFin, columnDescCourte, columnDescLongue, columnModeEnvoi);
        tvEncheres.getItems().add(new Utilisateur("testMail", "TestMDP", "TestCodePostal", "TestNom", "TestPrenom", 1));

        ObservableList<UserAccount> list = getUserList();
        tvEncheres.setItems(list);

        VBox vbAfficherEnchere = new VBox(this.bTest);

        Scene sTemp = new Scene(vbAfficherEnchere);
        sAffichageEnchere.setScene(sTemp);
        sAffichageEnchere.show();

        
    }

    private ObservableList<UserAccount> getUserList() {

        UserAccount user1 = new UserAccount(1L, "smith", "smith@gmail.com", //
                "Susan", "Smith", true);
        UserAccount user2 = new UserAccount(2L, "mcneil", "mcneil@gmail.com", //
                "Anne", "McNeil", true);
        UserAccount user3 = new UserAccount(3L, "white", "white@gmail.com", //
                "Kenvin", "White", false);

        ObservableList<UserAccount> list = FXCollections.observableArrayList(user1, user2, user3);
        return list;
    }
}
