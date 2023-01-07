/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Affichage;
import BdD.Article;
import BdD.Categorie;
import BdD.Utilisateur;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class PageAccueil extends BorderPane {

    private Controleur controleur;
    private Stage inStage;
    private Scene sPageConnection;

    private int utilisateurCourant;

    private TextField tfRechercher;
    private Label lGain;
    private Label lUserCourant;
    private Label lBienvenue;
    private Label lTextAccueil;
    private Label lTextQuiSertARien;

    private Button bAccueil;
    private Button bMultimedia;
    private Button bMaisonEtJardin;
    private Button bJouetsEtJeux;
    private Button bCultureEtLoisirs;
    private Button bAutoEtMoto;
    private Button bReconditionne;
    private Button bEnchere;
    private Button bInfoUtilisateur;
    private Button bAfficheSesEncheres;
    private Button bAfficheEnchereRemporte;
    private Button bAfficheEnchereNonRemporteEnCours;
    private Button bAfficheEnchereRemporteEnCours;
    private Button bValiderRecherche;
    private Button bDeconnexion;
    private Button bGererUtilisateurs;
    private Button bGererEncheres;

    private BorderPane bpEcranPrincipal;
    private CreerEnchere creerEnchere;
    private AfficherEnchere affichageEnchere;
    private InfoUtilisateur infoUtilisateur;
    private Rechercher search;
    private Article article;

    private ArrayList<Affichage> alCategorie;
    //private BorderPane bpEntete;

    public PageAccueil(Stage inStage, int utilisateurCourant, int statut) {
        this.utilisateurCourant = utilisateurCourant;
        this.inStage = inStage;
        this.controleur = new Controleur(this);
        this.creerEnchere = new CreerEnchere(this);
        this.affichageEnchere = new AfficherEnchere();
        this.infoUtilisateur = new InfoUtilisateur();
        this.search = new Rechercher();

        this.tfRechercher = new TextField("Rechercher");

        this.bAccueil = new Button("Accueil");
        this.bMultimedia = new Button("Multimédia");
        this.bMaisonEtJardin = new Button("Maison et Jardin");
        this.bJouetsEtJeux = new Button("Jouets et Jeux");
        this.bCultureEtLoisirs = new Button("Culture et Loisirs");
        this.bAutoEtMoto = new Button("Auto et Moto");
        this.bReconditionne = new Button("Reconditionné");
        this.bEnchere = new Button("Créer une enchère");
        this.alCategorie = new ArrayList<>();
        this.bInfoUtilisateur = new Button("Informations \nutilisateur");
        this.bAfficheSesEncheres = new Button("Mes Enchères");
        this.bAfficheEnchereNonRemporteEnCours = new Button("! Enchères à enchérir \nau plus vite !");
        this.bAfficheEnchereRemporte = new Button("Enchères remportées");
        this.bAfficheEnchereRemporteEnCours = new Button("Garder un oeil \nsur ses acquisitions");
        this.bValiderRecherche = new Button("Valider la recherche");
        this.lUserCourant = new Label("" + utilisateurCourant);
        this.lBienvenue = new Label("BIENVENUE");
        this.lTextAccueil = new Label("sur ce magnifique site d'enchères de l'INSA Strasbourg");
        this.lTextQuiSertARien = new Label("Fait par les étudiants, pour qui veut");
        this.bDeconnexion = new Button("Deconnexion");
        this.bGererUtilisateurs = new Button ("Gérer les \nutilisateurs");
        this.bGererEncheres = new Button ("Gérer les \nenchères");

        lBienvenue.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 50));
        lTextAccueil.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 20));
        lTextQuiSertARien.setFont(Font.font("", FontWeight.BOLD, FontPosture.REGULAR, 15));

        VBox vbBienvenue = new VBox(lBienvenue);
        vbBienvenue.setAlignment(Pos.CENTER);
        VBox vbTextAccueil = new VBox(lTextAccueil);
        vbTextAccueil.setAlignment(Pos.CENTER);
        VBox vbTextQuiSertARien = new VBox(lTextQuiSertARien);
        vbTextQuiSertARien.setAlignment(Pos.CENTER);

        VBox vbAccueil = new VBox(vbBienvenue, vbTextAccueil, vbTextQuiSertARien);
        vbAccueil.setAlignment(Pos.CENTER);

        bEnchere.setPrefSize(150, 70);
        bMultimedia.setPrefWidth(120);
        bMaisonEtJardin.setPrefWidth(120);
        bJouetsEtJeux.setPrefWidth(120);
        bCultureEtLoisirs.setPrefWidth(120);
        bAutoEtMoto.setPrefWidth(120);
        bReconditionne.setPrefWidth(120);
        bValiderRecherche.setPrefWidth(130);
        tfRechercher.setPrefWidth(630);

        bInfoUtilisateur.setPrefSize(180, 50);
        bAfficheSesEncheres.setPrefSize(180, 50);
        bAfficheEnchereRemporte.setPrefSize(180, 50);
        bAfficheEnchereNonRemporteEnCours.setPrefSize(180, 50);
        bAfficheEnchereRemporteEnCours.setPrefSize(180, 50);
        bDeconnexion.setPrefSize(180, 50);
        bGererUtilisateurs.setPrefSize(180, 50);
        bGererEncheres.setPrefSize(180, 50);

        Background bgSilver = new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, null));
        Background bgLightGrey = new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, null));

        try ( Connection con = defautConnect()) {
            double dGain = Utilisateur.afficheGain(con, utilisateurCourant);
            String sGain = "" + dGain;
            this.lGain = new Label("Gain : " + sGain + " €");
        } catch (Exception ex) {
            throw new Error(ex);
        }

        BorderPane bpEntete = new BorderPane();
        HBox hbCategories = new HBox(bMultimedia, bMaisonEtJardin, bJouetsEtJeux, bCultureEtLoisirs, bAutoEtMoto, bReconditionne);
        hbCategories.setSpacing(10);
        hbCategories.setPadding(new Insets(5));
        hbCategories.setAlignment(Pos.CENTER);
        HBox hbRecherche = new HBox(tfRechercher, bValiderRecherche);
        hbRecherche.setSpacing(10);
        hbRecherche.setPadding(new Insets(5));
        hbRecherche.setAlignment(Pos.CENTER);
        ImageView ivLogoINSA = new ImageView(new Image("file:Image_INSA.png"));
        VBox vbCentre = new VBox(hbRecherche, hbCategories);

        bpEntete.setLeft(ivLogoINSA);
        bpEntete.setCenter(vbCentre);
        bpEntete.setRight(bEnchere);

        VBox vbGain = new VBox(lGain);
        vbGain.setAlignment(Pos.CENTER);
        VBox vbUtilisateur = new VBox(vbGain, bInfoUtilisateur, bAfficheSesEncheres, bAfficheEnchereRemporte, bAfficheEnchereNonRemporteEnCours, 
                    bAfficheEnchereRemporteEnCours, bDeconnexion);  
        
        if (statut == 0) {
            vbUtilisateur.getChildren().add(bGererUtilisateurs);
            vbUtilisateur.getChildren().add(bGererEncheres);
        }
        
        vbUtilisateur.setSpacing(10);
        vbUtilisateur.setPadding(new Insets(5));

        bpEntete.setBackground(bgSilver);
        vbUtilisateur.setBackground(bgLightGrey);

        this.setTop(bpEntete);
        this.setLeft(vbUtilisateur);
        this.setCenter(vbAccueil);

        bEnchere.setOnAction((t) -> {
            this.creerEnchere.fenetreEnchere();
        });

        //root.getChildren().addAll(vbRubriques, sbAffichagePrincipal);
//        vbRubriques.setLayoutX(5);
//        vbRubriques.setSpacing(10);
        bMultimedia.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Multimédia");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Multimédia");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bMaisonEtJardin.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Maison et Jardin");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Maison Et Jardin");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bJouetsEtJeux.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Jouets et Jeux");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Jouets et Jeux");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bCultureEtLoisirs.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Culture et Loisirs");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Culture et Loisirs");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bAutoEtMoto.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Auto et Moto");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Auto et Moto");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bReconditionne.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Reconditionné");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Reconditionné");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bAfficheSesEncheres.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheSesEnchères(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Mes enchères");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bAfficheEnchereNonRemporteEnCours.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheEnchereNonRemporteEnCours(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Enchères non remportées en cours");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bAfficheEnchereRemporte.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheEnchereRemporte(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Enchères remportées");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bAfficheEnchereRemporteEnCours.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheEnchereRemporteEnCours(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this, this.alCategorie, "Enchères remportées en cours");
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

        bInfoUtilisateur.setOnAction((t) -> {
            infoUtilisateur.afficherInfoUtilisateur(this.utilisateurCourant);
        });

        bValiderRecherche.setOnAction((t) -> {
            String sRecherche = tfRechercher.getText();
            rechercherEnchere(sRecherche);
        });

        bDeconnexion.setOnAction((t) -> {
            this.inStage.close();
            sPageConnection = new Scene(new PageConnection(inStage));
            inStage.setTitle("INS'Enchères");
            inStage.setScene(sPageConnection);
            inStage.setResizable(true);
            inStage.show();

        });

    }

    public void rechercherEnchere(String sRecherche) {
        ArrayList<String> alRechercheBasique = new ArrayList<>();
        alRechercheBasique = this.article.decomposeRecherche(sRecherche);

        ArrayList<Affichage> alRechercheDecomposee = new ArrayList<>();

        try ( Connection con = defautConnect()) {
            alRechercheDecomposee = this.article.ChercheArticle(con, alRechercheBasique);
            this.affichageEnchere.fenetreAffichageEnchere(this, alRechercheDecomposee, "Recherche");
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    /**
     * @return the utilisateurCourant
     */
    public int getUtilisateurCourant() {
        return utilisateurCourant;
    }
}
