/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import BdD.Affichage;
import BdD.Categorie;
import BdD.Utilisateur;
import static BdD.Utilisateur.defautConnect;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author drumm
 */
public class PageAccueil extends BorderPane {

    private Controleur controleur;
    private Stage inStage;

    private int utilisateurCourant;

    private TextField tfRechercher;
    private Label lGain;

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
    
    
    private BorderPane bpEcranPrincipal;
    private CreerEnchere creerEnchere;
    private AfficherEnchere affichageEnchere;
    private InfoUtilisateur infoUtilisateur;

    private ArrayList<Affichage> alCategorie;
    //private BorderPane bpEntete;

    public PageAccueil(Stage inStage, int utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
        this.inStage = inStage;
        this.controleur = new Controleur(this);
        this.creerEnchere = new CreerEnchere(this);
        this.affichageEnchere = new AfficherEnchere(this);

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
        this.bInfoUtilisateur = new Button("Info Utilisateur");
        this.bAfficheSesEncheres = new Button("Mes Enchères");
        this.bAfficheEnchereNonRemporteEnCours = new Button("! Enchère à enchérir au plus vite !");
        this.bAfficheEnchereRemporte = new Button("Enchère remportée");
        this.bAfficheEnchereRemporteEnCours = new Button("Garder un oeil sur ses acquisitions");

//        try ( Connection con = defautConnect()) {
//            double dGain = Utilisateur.afficheGain(con, utilisateurCourant);
//            String sGain = "" + dGain;
//            this.lGain = new Label (sGain);
//        } catch (Exception ex) {
//            throw new Error(ex);
//        }
        
        VBox vbEnchereUser = new VBox(bInfoUtilisateur, bEnchere);
        BorderPane bpEntete = new BorderPane();
        ImageView ivLogoINSA = new ImageView(new Image("file:Image_INSA.png"));
        bpEntete.setCenter(this.tfRechercher);
//        VBox vbGain = new VBox (this.lGain);
//        vbGain.setAlignment(Pos.CENTER);
//        bpEntete.setBottom(vbGain); 
        bpEntete.setLeft(ivLogoINSA);
        bpEntete.setRight(vbEnchereUser);
        Background bgGrey = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, null));
        bpEntete.setBackground(bgGrey);


        VBox vbRubriques = new VBox(this.bAccueil, this.bMultimedia, this.bMaisonEtJardin, this.bJouetsEtJeux, this.bCultureEtLoisirs,
                this.bAutoEtMoto, this.bReconditionne, this.bAfficheSesEncheres,this.bAfficheEnchereRemporte,this.bAfficheEnchereNonRemporteEnCours,this.bAfficheEnchereRemporteEnCours);
        vbRubriques.setSpacing(8);

        vbRubriques.setBackground(bgGrey);

        this.setTop(bpEntete);
        this.setLeft(vbRubriques);

//        bMultimedia.setOnAction((t) -> {
//            this.afficherEnchere.fenetreEnchere("Multimedia");
//        });
        bEnchere.setOnAction((t) -> {
            this.creerEnchere.fenetreEnchere();
        });

        bInfoUtilisateur.setOnAction((t) -> {
            this.infoUtilisateur.afficherInfoUtilisateur(utilisateurCourant);
        });

        //Group root = new Group();
        ScrollBar sbAffichagePrincipal = new ScrollBar();
        sbAffichagePrincipal.setMin(0);
        sbAffichagePrincipal.setMax(100);
        sbAffichagePrincipal.setOrientation(Orientation.VERTICAL);
        sbAffichagePrincipal.setLayoutX(this.getWidth()/* - sbAffichagePrincipal.getWidth()*/);
        sbAffichagePrincipal.setPrefHeight(180);
        sbAffichagePrincipal.setMax(360);
        //root.getChildren().addAll(vbRubriques, sbAffichagePrincipal);

        vbRubriques.setLayoutX(5);
        vbRubriques.setSpacing(10);

        sbAffichagePrincipal.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                vbRubriques.setLayoutY(-new_val.doubleValue());
            }
        });

        this.setRight(sbAffichagePrincipal);

        bMultimedia.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Multimédia");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bMaisonEtJardin.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Maison et Jardin");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bJouetsEtJeux.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Jouets et Jeux");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bCultureEtLoisirs.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Culture et Loisirs");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bAutoEtMoto.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Auto et Moto");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bReconditionne.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Categorie.EnchereEtArticleParCategorie(con, "Reconditionné");
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bAfficheSesEncheres.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheSesEnchères(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bAfficheEnchereNonRemporteEnCours.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheEnchereNonRemporteEnCours(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bAfficheEnchereRemporte.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheEnchereRemporte(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });
        
        bAfficheEnchereRemporteEnCours.setOnAction((t) -> {
            try ( Connection con = defautConnect()) {
                this.alCategorie = Utilisateur.afficheEnchereRemporteEnCours(con, utilisateurCourant);
                //System.out.println(alCategorie);
                this.affichageEnchere.fenetreAffichageEnchere(this.alCategorie);
            } catch (Exception ex) {
                throw new Error(ex);
            }
        });

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
