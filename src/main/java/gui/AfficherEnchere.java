/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Affichage;
import BdD.Enchere;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

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
    private TableView tvEncheres;
    private int iIDArticleSelectionne;
//    private ObservableList<Utilisateur> utilisateurs;

    public AfficherEnchere() {

    }

    public void fenetreAffichageEnchere(ArrayList alEnchere, String sCategorie) {
        TableView tvEncheres = new TableView();
        tvEncheres.setEditable(true);
        ArrayList<String> lTest = new ArrayList<>();
        this.obTest = FXCollections.observableArrayList(alEnchere);
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Affichage, String> columIdArticle = new TableColumn<>("ID Article");
        TableColumn<Affichage, String> columnNom = new TableColumn<>("Nom");
        TableColumn<Affichage, Double> columnPrix = new TableColumn<>("Prix");
        TableColumn<Affichage, String> columnDateDebut = new TableColumn<>("Date de début");
        TableColumn<Affichage, String> columnDateFin = new TableColumn<>("Date de fin");
        TableColumn<Affichage, String> columnDescCourte = new TableColumn<>("Description courte");
        TableColumn<Affichage, String> columnDescLongue = new TableColumn<>("Description longue");
        TableColumn<Affichage, String> columnModeEnvoi = new TableColumn<>("Mode d'envoi");
        TableColumn<Affichage, ImageView> columnPhoto = new TableColumn<>("Images");
        
        columIdArticle.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        columnDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        columnDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        columnDescCourte.setCellValueFactory(new PropertyValueFactory<>("descriptionCourte"));
        columnDescLongue.setCellValueFactory(new PropertyValueFactory<>("descriptionLongue"));
        columnModeEnvoi.setCellValueFactory(new PropertyValueFactory<>("expedition"));
        columnPhoto.setCellValueFactory(new PropertyValueFactory<>("image"));    
        columIdArticle.setVisible(false);
        columnPrix.setEditable(true);
        columnPrix.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        tvEncheres.getSelectionModel().setCellSelectionEnabled(true);
        tvEncheres.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       
        tvEncheres.setOnMouseClicked((t) -> {
            TablePosition tablePosition = (TablePosition) tvEncheres.getSelectionModel().getSelectedCells().get(0);
            int row = tablePosition.getRow();
            Affichage item = (Affichage) tvEncheres.getItems().get(row);
            TableColumn tableColumn = tablePosition.getTableColumn();
            iIDArticleSelectionne = (int) tableColumn.getCellObservableValue(item).getValue();
            System.out.println(iIDArticleSelectionne);
        });
       

        columnPrix.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Affichage, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Affichage, Double> t) {
                try ( Connection con = defautConnect()) {
                    Affichage cTest = t.getRowValue();
                    Enchere.encherir(con, vue.getUtilisateurCourant(), t.getNewValue(), iIDArticleSelectionne);
                } catch (Exception ex) {
                    throw new Error(ex);
                }
            }
        });

        tvEncheres.getColumns().addAll(columIdArticle, columnNom, columnPrix, columnDateDebut, columnDateFin, columnDescCourte, columnDescLongue, columnModeEnvoi, columnPhoto);

        tvEncheres.setItems(obTest);
        VBox vbAfficherEnchere = new VBox(tvEncheres);
        tvEncheres.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        sAffichageEnchere = new Stage();
        Scene sTemp = new Scene(vbAfficherEnchere);
        sAffichageEnchere.setScene(sTemp);
        sAffichageEnchere.setTitle(sCategorie);
        sAffichageEnchere.show();

    }    
}
