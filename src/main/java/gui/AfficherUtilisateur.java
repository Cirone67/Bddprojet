/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import BdD.Utilisateur;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
/**
 *
 * @author brenc
 */
public class AfficherUtilisateur extends TableView{
   
        private PageAccueil vue;
    private Stage sAffichageEnchere;
    private TextField tfSelectionner;
    private Button bSupprimer;
    private Label lSelectionner;
    private ObservableList<String> obTest;
    
    
    
    public void fenetreAffichageUtilisateur(PageAccueil main, ArrayList alUtilisateur, String sCategorie, Connection con) {
        this.bSupprimer = new Button("Supprimer");
        this.lSelectionner = new Label("Utilisateur selectionné : ");
        this.tfSelectionner = new TextField("Id Utilisateur");
        this.vue = main;
        TableView tvUtilisateur = new TableView();
        tvUtilisateur.setEditable(true);
        this.obTest = FXCollections.observableArrayList(alUtilisateur);
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Utilisateur, String> columIdUtilisateur = new TableColumn<>("ID Utilisateur");
        TableColumn<Utilisateur, String> columnEmail = new TableColumn<>("email");
        TableColumn<Utilisateur, Double> columnMdp = new TableColumn<>("mots de passe");
        TableColumn<Utilisateur, String> columnCP = new TableColumn<>("code postal");
        TableColumn<Utilisateur, String> columnNom = new TableColumn<>("nom");
        TableColumn<Utilisateur, String> columnPrenom = new TableColumn<>("prenom");
        TableColumn<Utilisateur, String> columnStatut = new TableColumn<>("statut");
        
        columIdUtilisateur.setCellValueFactory(new PropertyValueFactory<>("idUtilisateur"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnMdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        columnCP.setCellValueFactory(new PropertyValueFactory<>("codePostal"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
       
       
         tvUtilisateur.getColumns().addAll(columIdUtilisateur,columnEmail, columnMdp,columnCP,columnNom,columnPrenom,columnStatut);

        tvUtilisateur.setItems(obTest);
        HBox hbSelectUtilisateur = new HBox(lSelectionner,tfSelectionner,bSupprimer);
        VBox vbAfficherUtilisateur = new VBox(hbSelectUtilisateur,tvUtilisateur);
        tvUtilisateur.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        bSupprimer.setOnAction((t) -> {
            String sSelectioner = tfSelectionner.getText();
            
            try {
                System.out.println("dedans");
                Utilisateur.deleteUtilisateur(con,Integer.parseInt(sSelectioner));
            } catch (SQLException ex) {
                
            }
        });
        
        sAffichageEnchere = new Stage();
        Scene sTemp = new Scene(vbAfficherUtilisateur);
        sAffichageEnchere.setScene(sTemp);
        sAffichageEnchere.setTitle(sCategorie);
        sAffichageEnchere.show();
}
}
