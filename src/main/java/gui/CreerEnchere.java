/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Enchere;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class CreerEnchere extends BorderPane {

    private PageAccueil vue;
    private Stage sEnchere;
    private Button bTest;
    private Enchere creerEnchere;

    private Label lPrixInitial;
    private TextField tfPrixInitial;
    private Label lDateDebut;
    private DatePicker dpDateDebut;
    private Label lDateFin;
    private DatePicker dpDateFin;
    private Label lNom;
    private TextField tfNom;
    private Label lDescriptionCourte;
    private TextField tfDescriptionCourte;
    private Label lDescriptionLongue;
    private TextField tfDescriptionLongue;
    private Label lMoyenExpedition;
    private TextField tfURLPhoto;
    private Label lURLPhoto;
    private RadioButton rbEnvoiPostal;
    private RadioButton rbVenirChercher;
    private Label lTitreFenetre;
    private Button bValiderEnchere;
    private CheckBox cbMultimdia;
    private CheckBox cbMaisonEtJardin;
    private CheckBox cbJouetsEtJeux;
    private CheckBox cbCultureEtLoisirs;
    private CheckBox cbAutoEtMoto;
    private CheckBox cbReconditionne;
    private Label lCategorie;

    public CreerEnchere(PageAccueil vue) {
        this.vue = vue;
    }

    public void fenetreEnchere() {

        this.lTitreFenetre = new Label("Créer une enchère");
        lTitreFenetre.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 17));
        lTitreFenetre.setUnderline(true);
        this.lNom = new Label("Nom de l'objet -------- ");
        this.tfNom = new TextField();
        this.lPrixInitial = new Label("Prix Initial -------------- ");
        this.tfPrixInitial = new TextField();
        this.lDateDebut = new Label("Date de début --------- ");
        this.dpDateDebut = new DatePicker();
        this.lDateFin = new Label("Date de fin ------------ ");
        this.dpDateFin = new DatePicker();
        this.lDescriptionCourte = new Label("Description courte ---- ");
        this.tfDescriptionCourte = new TextField();
        this.lDescriptionLongue = new Label("Description longue --- ");
        this.tfDescriptionLongue = new TextField();
        this.lURLPhoto = new Label("URL Photo ------------ ");
        this.tfURLPhoto = new TextField();
        this.lMoyenExpedition = new Label("Mode d'envoi --------- ");
        this.rbEnvoiPostal = new RadioButton("Envoi Postal");
        this.rbVenirChercher = new RadioButton("Se déplacer");
        this.bValiderEnchere = new Button("Valider");
        this.cbMultimdia = new CheckBox("Multimédia");
        this.cbMaisonEtJardin = new CheckBox("Maison et Jardin");
        this.cbJouetsEtJeux = new CheckBox("Jouets et Jeux");
        this.cbCultureEtLoisirs = new CheckBox("Culture et Loisirs");
        this.cbAutoEtMoto = new CheckBox("Auto et Moto");
        this.cbReconditionne = new CheckBox("Reconditionné");
        this.lCategorie = new Label("Choisir la catégorie correspondante");
        lCategorie.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 17));
        lCategorie.setUnderline(true);

        this.bTest = new Button("Test");

        ToggleGroup tgModeEnvoi = new ToggleGroup();
        this.rbEnvoiPostal.setToggleGroup(tgModeEnvoi);
        this.rbVenirChercher.setToggleGroup(tgModeEnvoi);
        
        HBox hbExpedition = new HBox (rbEnvoiPostal, rbVenirChercher);
        hbExpedition.setSpacing(5);
        
        GridPane gpAffichageCreation = new GridPane ();
        gpAffichageCreation.add(lTitreFenetre, 0, 0, 2, 1);
        GridPane.setHalignment(lTitreFenetre, HPos.CENTER);
        gpAffichageCreation.add(lNom, 0, 1);
        gpAffichageCreation.add(tfNom, 1, 1);
        gpAffichageCreation.add(lPrixInitial, 0, 2);
        gpAffichageCreation.add(tfPrixInitial, 1, 2);
        gpAffichageCreation.add(lDateDebut, 0, 3);
        gpAffichageCreation.add(dpDateDebut, 1, 3);
        gpAffichageCreation.add(lDateFin, 0, 4);
        gpAffichageCreation.add(dpDateFin, 1, 4);
        gpAffichageCreation.add(lDescriptionCourte, 0, 5);
        gpAffichageCreation.add(tfDescriptionCourte, 1, 5);
        gpAffichageCreation.add(lDescriptionLongue, 0, 6);
        gpAffichageCreation.add(tfDescriptionLongue, 1, 6);
        gpAffichageCreation.add(lURLPhoto, 0, 7);
        gpAffichageCreation.add(tfURLPhoto, 1, 7);
        gpAffichageCreation.add(lMoyenExpedition, 0, 8);
        gpAffichageCreation.add(hbExpedition, 1, 8);
        gpAffichageCreation.setPadding(new Insets(5));
        gpAffichageCreation.setAlignment(Pos.CENTER);
        gpAffichageCreation.setVgap(5);
        
        GridPane gpCategorie = new GridPane ();
        gpCategorie.add(lCategorie, 0, 0, 3, 1);
        GridPane.setHalignment(lCategorie, HPos.CENTER);
        gpCategorie.add(cbMultimdia, 0, 1);
        gpCategorie.add(cbMaisonEtJardin, 1, 1);
        gpCategorie.add(cbJouetsEtJeux, 2, 1);
        gpCategorie.add(cbCultureEtLoisirs, 0, 2);
        gpCategorie.add(cbAutoEtMoto, 1, 2);
        gpCategorie.add(cbReconditionne, 2, 2);
        gpCategorie.add(bValiderEnchere, 1, 3);
        GridPane.setHalignment(bValiderEnchere, HPos.CENTER);
        gpCategorie.setPadding(new Insets(5));
        gpCategorie.setAlignment(Pos.CENTER);
        gpCategorie.setVgap(5);
        gpCategorie.setHgap(5);

        VBox vbFenetre = new VBox (gpAffichageCreation, gpCategorie);

        Scene sTemp = new Scene(vbFenetre);

        sEnchere = new Stage();
        sEnchere.setScene(sTemp);
        sEnchere.getIcons().add(new Image("file:Ins_Enchère_v6.png"));
        sEnchere.setTitle("Créer une enchère");
        sEnchere.setHeight(420);
        sEnchere.setWidth(360);
        sEnchere.setResizable(false);
        sEnchere.show();

        bValiderEnchere.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                String sNom = tfNom.getText();
                double dPrixInitial = getDoubleFromTextField(tfPrixInitial);
                LocalDate dDateDebut = dpDateDebut.getValue();
                LocalDate dDateFin = dpDateFin.getValue();
                String sDescriptionCourte = tfDescriptionCourte.getText();
                String sDescriptionLongue = tfDescriptionLongue.getText();
                String sURLPhoto = tfURLPhoto.getText();
                ArrayList<String> alRubriques = new ArrayList<>();
                int iModeEnvoi = 0;

                if (rbEnvoiPostal.isSelected()) {
                    iModeEnvoi = 1;
                }

                if (rbVenirChercher.isSelected()) {
                    iModeEnvoi = 2;
                }

                if (cbMultimdia.isSelected()) {
                    alRubriques.add("Multimédia");
                }
                if (cbMaisonEtJardin.isSelected()) {
                    alRubriques.add("Maison et Jardin");
                }
                if (cbJouetsEtJeux.isSelected()) {
                    alRubriques.add("Jouets et Jeux");
                }
                if (cbCultureEtLoisirs.isSelected()) {
                    alRubriques.add("Culture et Loisirs");
                }
                if (cbAutoEtMoto.isSelected()) {
                    alRubriques.add("Auto et Moto");
                }
                if (cbReconditionne.isSelected()) {
                    alRubriques.add("Reconditionné");
                }

                LocalDate ldDateDebut = dpDateDebut.getValue();
                String dDebut = ldDateDebut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                SimpleDateFormat simpleDateFormatDebut = new SimpleDateFormat("yyyy-MM-dd");
                Date dDebutString = new java.sql.Date(simpleDateFormatDebut.parse(dDebut).getTime());
                
                LocalDate ldDateFin = dpDateFin.getValue();
                String dFin = ldDateFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                SimpleDateFormat simpleDateFormatFin = new SimpleDateFormat("yyyy-MM-dd");
                Date dFinString = new java.sql.Date(simpleDateFormatFin.parse(dFin).getTime());

                creerEnchere.createElement(con, dPrixInitial, dDebutString, dFinString, vue.getUtilisateurCourant(), sNom, sDescriptionCourte, sDescriptionLongue,
                        iModeEnvoi, alRubriques,sURLPhoto);
                
                sEnchere.close();
                
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
    }

    public static double getDoubleFromTextField(TextField textField) {
        String text = textField.getText();
        return Double.parseDouble(text);
    }

}
