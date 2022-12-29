/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

//import ProjetBdD.gui.SessionInfo;
import BdD.Utilisateur;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class PageConnection extends BorderPane {

    private Stage inStage;

    private TextField tfIdentifiant;
    private Label lIdentifiant;
    private PasswordField pfMotDePasse;
    private Label lMotDePasse;
    private Hyperlink hlMDPOublie;
    private Hyperlink hlNouvelUtilisateur;
    private Label lMail;
    private TextField tfMail;
    private Label lConfirmationMDP;
    private PasswordField pfConfirmationMDP;
    private Label lCodePostal;
    private TextField tfCodePostal;
    private Label lNom;
    private TextField tfNom;
    private Label lPrenom;
    private TextField tfPrenom;
    private Label lTitre;
    private Button bValiderConnection;
    private Label lProblemMDP;

    private Stage sMDPOublie;
    private Stage sNouvelUtilisateur;
    private Scene sPageAccueil;
    private Stage sErreur;

    private Utilisateur user;

    //private SessionInfo sessionInfo;
    public PageConnection(Stage inStage) {

        this.inStage = inStage;

        this.lTitre = new Label("Se connecter");
        lTitre.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 17));
        lTitre.setUnderline(true);

        this.tfIdentifiant = new TextField();
        this.lIdentifiant = new Label("Identifiant ----------------- ");
        this.lMotDePasse = new Label("Mot de Passe ------------- "); 
        this.pfMotDePasse = new PasswordField();
        this.hlMDPOublie = new Hyperlink("Mot de Passe oublié ?");
        this.hlNouvelUtilisateur = new Hyperlink("Nouvel Utilisateur ?");

        this.lMail = new Label("Mail : ");
        this.tfMail = new TextField("Mail");
        this.lConfirmationMDP = new Label("Confirmez le Mot de Passe : ");
        this.tfCodePostal = new TextField("Code Postal");
        this.lCodePostal = new Label("Code postal : ");
        this.tfNom = new TextField("Nom");
        this.lNom = new Label("Nom : ");
        this.tfPrenom = new TextField("Prénom");
        this.lPrenom = new Label("Prénom : ");
        this.bValiderConnection = new Button("Valider");

        this.lProblemMDP = new Label("Connexion impossible - Erreur MDP ou identifiant");
        
        GridPane gpAffichageConnection = new GridPane ();
        gpAffichageConnection.add(lTitre, 0, 0, 2, 1);
        GridPane.setHalignment(lTitre, HPos.CENTER);
        gpAffichageConnection.add(lIdentifiant, 0, 1);
        gpAffichageConnection.add(tfIdentifiant, 1, 1);
        gpAffichageConnection.add(lMotDePasse, 0, 2);
        gpAffichageConnection.add(pfMotDePasse, 1, 2);
        gpAffichageConnection.add(hlMDPOublie, 0, 3);
        gpAffichageConnection.add(hlNouvelUtilisateur, 0, 4);
        gpAffichageConnection.add(bValiderConnection, 0, 5, 2, 1);
        GridPane.setHalignment(bValiderConnection, HPos.CENTER);
        gpAffichageConnection.setPadding(new Insets(5));
        gpAffichageConnection.setVgap(5);
        gpAffichageConnection.setHgap(5);
        
        inStage.setResizable(false);

        this.inStage.setTitle("Se connecter");

        this.setLeft(gpAffichageConnection);

        hlMDPOublie.setOnAction(new EventHandler<ActionEvent>() {

            private Label lMDPOublie;
            private Label lMail;
            private TextField tfMail;
            private Label lMDP;
            private PasswordField pfMDP;
            private Label lConfirmationMDP;
            private PasswordField pfConfirmationMDP;
            private Button bValiderMDP;
            private Label lErreur;

            @Override
            public void handle(ActionEvent event) {
                this.lMDPOublie = new Label("Mot de passe oublié ?");
                lMDPOublie.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 17));
                lMDPOublie.setUnderline(true);
//                this.lNom = new Label("Nom : ");
//                this.tfNom = new TextField();
//                this.lPrenom = new Label("Prénom : ");
//                this.tfPrenom = new TextField();
                this.lMail = new Label ("Mail ------------------------- ");
                this.tfMail = new TextField ();
                this.lMDP = new Label("Nouveau mot de passe ---- ");
                this.pfMDP = new PasswordField();
                this.lConfirmationMDP = new Label("Confirmez le mot de passe : ");
                this.pfConfirmationMDP = new PasswordField();
                this.bValiderMDP = new Button("Valider");
                this.lErreur = new Label ("Le mail n'existe pas");

                GridPane gpMDPOublie = new GridPane ();
                gpMDPOublie.add(lMDPOublie, 0, 0, 2, 1);
                GridPane.setHalignment(lMDPOublie, HPos.CENTER);
                gpMDPOublie.add(lMail, 0, 1);
                gpMDPOublie.add(tfMail, 1, 1);
                gpMDPOublie.add(lMDP, 0, 2);
                gpMDPOublie.add(pfMDP, 1, 2);
                gpMDPOublie.add(bValiderMDP, 0, 3, 2, 1);
                GridPane.setHalignment(bValiderMDP, HPos.CENTER);
                gpMDPOublie.setPadding(new Insets(5));
                gpMDPOublie.setVgap(5);
                gpMDPOublie.setHgap(5);
                
                Scene sTemp = new Scene(gpMDPOublie);

                sMDPOublie = new Stage();
                sMDPOublie.setScene(sTemp);
                sMDPOublie.setTitle("Mot de passe oublié ?");
                sMDPOublie.setResizable(false);
                sMDPOublie.show();
                
                bValiderMDP.setOnAction((t) -> {
                    try ( Connection con = defautConnect()) {
                        int res;

                        String mail = tfMail.getText();
                        String nvMDP = pfMDP.getText();

                        int statut = 1;


                        res = user.demandeChangerMdp(con, mail, nvMDP);
                        if (res != -1) {
                            sMDPOublie.close();
                        } else {
                            HBox hbErreur = new HBox(this.lErreur);
                            hbErreur.setAlignment(Pos.CENTER);

                            Scene sTempErreur = new Scene(hbErreur);
                            sErreur = new Stage();
                            sErreur.setTitle("Erreur mot de passe !");
                            sErreur.setScene(sTempErreur);
                            sErreur.show();
                        }

                    } catch (Exception ex) {
                        throw new Error(ex);
                    }
                });
            }
        });

        hlNouvelUtilisateur.setOnAction(new EventHandler<ActionEvent>() {
            private Label lNouvelUtilisateur;
            private Label lNom;
            private TextField tfNom;
            private Label lPrenom;
            private TextField tfPrenom;
            private Label lMail;
            private TextField tfMail;
            private Label lMDP;
            private PasswordField pfMDP;
//            private Label lConfirmationMDP;
//            private PasswordField pfConfirmationMDP;
            private Label lCodePostal;
            private TextField tfCodePostal;
            private Button bValiderNU;
            private RadioButton rbAdmin;
            private RadioButton rbUtilisateur;
            private Label lErreur;

            @Override
            public void handle(ActionEvent event) {
                this.lNouvelUtilisateur = new Label("Nouvel Utilisateur");
                lNouvelUtilisateur.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 17));
                lNouvelUtilisateur.setUnderline(true);
                this.lNom = new Label("Nom ---------------- ");
                this.tfNom = new TextField();
                this.lPrenom = new Label("Prénom ------------- ");
                this.tfPrenom = new TextField();
                this.lMail = new Label("Mail ----------------- ");
                this.tfMail = new TextField();
                this.lMDP = new Label("Mot de Passe ------- ");
                this.pfMDP = new PasswordField();
                this.lCodePostal = new Label("Code postal --------- ");
                this.tfCodePostal = new TextField();
                this.bValiderNU = new Button("Valider");
                this.rbAdmin = new RadioButton("Administrateur");
                this.rbUtilisateur = new RadioButton("Utilisateur");
                this.lErreur = new Label("Le mail est déjà utilisé");
                
                ToggleGroup tgStatut = new ToggleGroup();
                this.rbAdmin.setToggleGroup(tgStatut);
                this.rbUtilisateur.setToggleGroup(tgStatut);
                
                GridPane gpNewUser = new GridPane ();
                gpNewUser.add(lNouvelUtilisateur, 0, 0, 2, 1);
                GridPane.setHalignment(lNouvelUtilisateur, HPos.CENTER);
                gpNewUser.add(lNom, 0, 1);
                gpNewUser.add(tfNom, 1, 1);
                gpNewUser.add(lPrenom, 0, 2);
                gpNewUser.add(tfPrenom, 1, 2);
                gpNewUser.add(lMail, 0, 3);
                gpNewUser.add(tfMail, 1, 3);
                gpNewUser.add(lMDP, 0, 4);
                gpNewUser.add(pfMDP, 1, 4);
                gpNewUser.add(lCodePostal, 0, 5);
                gpNewUser.add(tfCodePostal, 1, 5);
                gpNewUser.add(rbAdmin, 0, 6);
                gpNewUser.add(rbUtilisateur, 1, 6);
                gpNewUser.add(bValiderNU, 0, 7, 2, 1);
                GridPane.setHalignment(bValiderNU, HPos.CENTER);
                gpNewUser.setPadding(new Insets(5));
                gpNewUser.setVgap(5);
                gpNewUser.setHgap(5);

                Scene sTemp = new Scene(gpNewUser);

                sNouvelUtilisateur = new Stage();
                sNouvelUtilisateur.setScene(sTemp);
                sNouvelUtilisateur.setTitle("Nouvel Utilisateur");
                sNouvelUtilisateur.setResizable(false);
                sNouvelUtilisateur.show();

                bValiderNU.setOnAction((t) -> {
                    try ( Connection con = defautConnect()) {
                        int res;

                        String nom = tfNom.getText();
                        String prenom = tfPrenom.getText();
                        String mail = tfMail.getText();
                        String mdp = pfMDP.getText();
//                        String confMDP = pfConfirmationMDP.getText();
                        String codePostal = tfCodePostal.getText();

                        int statut = 1;

                        if (tgStatut.getSelectedToggle() != null) {
                            RadioButton button = (RadioButton) tgStatut.getSelectedToggle();
                            if (button.getText().equals("Administrateur")) {
                                statut = 0;
                            } else {
                                statut = 1;
                            }
                        }

                        res = user.createUtilisateur(con, mail, mdp, codePostal, nom, prenom, statut);
                        if (res == 1) {
//                                bValiderNU.setOnAction(new EventHandler<ActionEvent>() {
//                                    @Override
//                                    public void handle(ActionEvent t) {
//                                        sNouvelUtilisateur.close();
//                                    }
//                                });
                            sNouvelUtilisateur.close();
                        } else {
                            HBox hbErreur = new HBox(this.lErreur);
                            hbErreur.setAlignment(Pos.CENTER);

                            Scene sTempErreur = new Scene(hbErreur);
                            sErreur = new Stage();
                            sErreur.setTitle("Erreur création utilisateur !");
                            sErreur.setScene(sTempErreur);
                            sErreur.show();
                        }

                    } catch (Exception ex) {
                        throw new Error(ex);
                    }
                });

                //sNouvelUtilisateur.close();
            }
        }
        );

        bValiderConnection.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                int res;
                res = user.demandeConnection(con, tfIdentifiant.getText(), pfMotDePasse.getText());
                //int id = 3; // TODO : demandeConnection renvoie l'id ou -1
                if (res == -1) {
                    HBox hbErreur = new HBox(this.lProblemMDP);
                    hbErreur.setAlignment(Pos.CENTER);

                    Scene sTemp = new Scene(hbErreur);
                    sErreur = new Stage();
                    sErreur.setScene(sTemp);
                    sErreur.show();
                } else {
                    this.inStage.close();
                    sPageAccueil = new Scene(new PageAccueil(inStage, res));
//                    inStage.setMaximized(true);
                    inStage.setTitle("INS'Enchères");
                    inStage.setScene(sPageAccueil);
                    inStage.setResizable(true);
                    inStage.show();
                }
            } catch (Exception ex) {
                throw new Error(ex);
            }

//            this.inStage.close();
//            sPageAccueil = new Scene(new PageAccueil(inStage));
//            inStage.setScene(sPageAccueil);
//            inStage.show();
        }
        );
        
    }

    /**
     * @return the inStage
     */
    public Stage getInStage() {
        return inStage;
    }

//    public SessionInfo getSessionInfo() {
//        return sessionInfo;
//    }
    /**
     * @return the tfIdentifiant
     */
    public TextField getTfIdentifiant() {
        return tfIdentifiant;
    }

    /**
     * @param tfIdentifiant the tfIdentifiant to set
     */
    public void setTfIdentifiant(TextField tfIdentifiant) {
        this.tfIdentifiant = tfIdentifiant;
    }

    /**
     * @return the pfMotDePasse
     */
    public PasswordField getPfMotDePasse() {
        return pfMotDePasse;
    }

}
