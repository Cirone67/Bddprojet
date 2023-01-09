/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Utilisateur;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class InfoUtilisateur {

    private ObservableList<String> obToutesInfos;
    private PageAccueil paVuePrincipale;
    private Utilisateur user;
    private Stage sInfosUser;

    private Label lNomUser;
    private Label lNomUserCourant;
    private Label lPrenomUser;
    private Label lPrenomUserCourant;
    private Label lMailUser;
    private Label lMailUserCourant;
    private Label lCodePostalUser;
    private Label lCodePosatalUserCourant;
    private Label lIDUser;
    private Label lIDUSerCourant;
    private Label lStatutUser;
    private Label lStatutUserCourant;

    public InfoUtilisateur() {

    }

    public void afficherInfoUtilisateur(int utilisateurCourant) {
        try ( Connection con = defautConnect()) {
            this.lNomUser = new Label("Nom ------------------------------------------ ");
            this.lPrenomUser = new Label("Prénom --------------------------------------- ");
            this.lMailUser = new Label("Mail ------------------------------------------- ");
            this.lCodePostalUser = new Label("Code Postal ---------------------------------- ");
            this.lIDUser = new Label("Identifiant ------------------------------------ ");
            this.lStatutUser = new Label("Statut ----------------------------------------- \n(0 = administrateur, 1 = utilisateur simple) ");

            Utilisateur infoUtilisateurCourant = user.afficheInfoUtilisateur(con, utilisateurCourant);
            String sNomUser = infoUtilisateurCourant.getNom();
            String sPrenomUser = infoUtilisateurCourant.getPrenom();
            String sMailUser = infoUtilisateurCourant.getEmail();
            String sCodePostalUser = infoUtilisateurCourant.getCodePostal();
            int iIDUser = infoUtilisateurCourant.getIdUtilisateur();
            int iStatutUser = infoUtilisateurCourant.getStatut();

            this.lNomUserCourant = new Label(sNomUser);
            this.lPrenomUserCourant = new Label(sPrenomUser);
            this.lMailUserCourant = new Label(sMailUser);
            this.lCodePosatalUserCourant = new Label(sCodePostalUser);
            this.lIDUSerCourant = new Label("" + iIDUser);
            this.lStatutUserCourant = new Label("" + iStatutUser);
            
            GridPane gpUser = new GridPane();
            gpUser.add(lNomUser, 0, 0);
            gpUser.add(lNomUserCourant, 1, 0);
            gpUser.add(lPrenomUser, 0, 1);
            gpUser.add(lPrenomUserCourant, 1, 1);
            gpUser.add(lMailUser, 0, 2);
            gpUser.add(lMailUserCourant, 1, 2);
            gpUser.add(lCodePostalUser, 0, 3);
            gpUser.add(lCodePosatalUserCourant, 1, 3);
            gpUser.add(lIDUser, 0, 4);
            gpUser.add(lIDUSerCourant, 1, 4);
            gpUser.add(lStatutUser, 0, 5);
            gpUser.add(lStatutUserCourant, 1, 5);
            gpUser.setPadding(new Insets(5));
            gpUser.setVgap(5);
            gpUser.setHgap(5);

//            HBox hbNom = new HBox(lNomUser, lNomUserCourant);
//            HBox hbPrenom = new HBox(lPrenomUser, lPrenomUserCourant);
//            HBox hbMail = new HBox(lMailUser, lMailUserCourant);
//            HBox hbCodePostal = new HBox(lCodePostalUser, lCodePosatalUserCourant);
//            HBox hbID = new HBox(lIDUser, lIDUSerCourant);
//            HBox hbStatut = new HBox(lStatutUser, lStatutUserCourant);
//            VBox vbInfosUtilisateur = new VBox(hbNom, hbPrenom, hbMail, hbCodePostal, hbID, hbStatut);

            sInfosUser = new Stage();
            Scene sTemp = new Scene(gpUser);
            sInfosUser.setScene(sTemp);
            sInfosUser.getIcons().add(new Image("file:Ins_Enchère_v6.png"));
            sInfosUser.setTitle("Informations utilisateur");
            sInfosUser.show();

        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
