package BdD;

//package ProjetBdD.gui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author brenc
 */
public class Article {

    private int idArticle;
    private String designation;
    private String descriptionCourte;
    private String descriptionLongue;
    private int expedition; //(0 = livrer,1 = a toi de le chercher)
    private String URLPhoto;
//  private int idCategorie;
    private int posseseur;

    //Constructor  
    public Article(int idArticle, String designation, String descriptionCourte, String descriptionLongue, int expedition, String URLPhoto, int posseseur) {
        this.idArticle = idArticle;
        this.designation = designation;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.expedition = expedition;
//        this.idCategorie = categorie;
        this.posseseur = posseseur;
        this.URLPhoto = URLPhoto;
    }

//Get et Set  
    public String getDesignation() {
        return designation;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public String getDescriptionCourte() {
        return descriptionCourte;
    }

    public String getURLPhoto() {
        return URLPhoto;
    }

    public String getDescriptionLongue() {
        return descriptionLongue;
    }

    public int getExpedition() {
        return expedition;
    }

    public int getPosseseur() {
        return posseseur;
    }

    public void setURLPhoto(String URLPhoto) {
        this.URLPhoto = URLPhoto;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setDescriptionCourte(String descriptionCourte) {
        this.descriptionCourte = descriptionCourte;
    }

    public void setDescriptionLongue(String descriptionLongue) {
        this.descriptionLongue = descriptionLongue;
    }

    public void setExpedition(int expedition) {
        this.expedition = expedition;
    }

    public void setPosseseur(int posseseur) {
        this.posseseur = posseseur;
    }
    
//Lien avec PGSQL------------------------------------------------

    public static Connection connectGeneralPostGres(String host, int port, String database, String user, String pass) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public static Connection defautConnect()
            throws ClassNotFoundException, SQLException {
        try {
            return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
        } catch (SQLException ex) {
            return connectGeneralPostGres("localhost", 5432, "postgres", "postgres", "pass");
        }
    }
//Creer la table Article-----------------------------------------------

    public static void creeTableArticle(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Article (
                        idArticle serial primary key,
                        designation varchar(30),
                        descriptionCourte varchar(100),
                        descriptionLongue varchar(500),
                        expedition integer not null,
                        URLPhoto varchar(500),
                        posseseur integer not null
                    )
                    """);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
//Créer une Article------------------------------------
//Deamnde en entrée la liste des désignations des catégories de l'article
    public static int createArticle(Connection con, String designation, String descriptionCourte, String descriptionLongue, int expedition, ArrayList<String> desiCategorie, int posseseur, String URLPhoto)
            throws SQLException, idArticleExisteDejaException {
        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement(
                """
                
                insert into Article (designation, descriptionCourte,  descriptionLongue,  expedition,  posseseur,URLPhoto) values (?,?,?,?,?,?)                
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, designation);
            pst.setString(2, descriptionCourte);
            pst.setString(3, descriptionLongue);
            pst.setInt(4, expedition);
            pst.setInt(5, posseseur);
            pst.setString(6, URLPhoto);
            pst.executeUpdate();
            con.commit();
        }
        int i = 0;
        //Un Article appartient à plusieurs catégories d'où:
        while (i < desiCategorie.size()) {
            try (PreparedStatement pst = con.prepareStatement(
                    """   
                    insert into JoinCategorieArticle (idCategorie,idArticle) values ((select idCategorie from Categorie where designation = ?) ,(select max(idArticle) from Article where designation = ?));
                
                """)) {
                pst.setString(2, designation);
                pst.setString(1, desiCategorie.get(i));
                pst.executeUpdate();
                con.commit();
                System.out.println(desiCategorie.get(i));
                i = i + 1;
            }

        }
        con.setAutoCommit(true);
        int res = 0;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select max(idArticle) from Article
               """
        )) {
            //pst.setString(1, designation);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res = rs.getInt(1);
                }
            } catch (SQLException ex) {
            }
        }
        return res;
    }

//Lecture dans PGSQL----------------------
    public static ArrayList<Article> actulisteTousArticle(Connection con) throws SQLException {
        ArrayList<Article> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,designation,descriptionCourte,descriptionLongue,expedition,URLPhoto,posseseur
                 from Article
               """
        )) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {

                    res.add(new Article(rs.getInt("idArticle"), rs.getString("designation"),
                            rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getString("URLPhoto"), rs.getInt("posseseur")));

                }
                return res;
            }
        }
    }

    //Liste des Articles par 
    //Fonction qui associe nom et désignation d'un article
    public static int nomconnecttodesignation(Connection con, String designation) throws SQLException {
        int res;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idArticle from Article where designation = ?
               """
        )) {
            pst.setString(1, designation);
            try (ResultSet rs = pst.executeQuery()) {
                //while (rs.next()) {
                res = rs.getInt("idArticle");
                //}   
                return res;
            }
        }
    }

    
    
    //Décompose une phrase en liste de mot
    public static ArrayList<String> decomposeRecherche(String recherche) {
        ArrayList<String> res = new ArrayList<>();
        String[] resint = recherche.split(" ");
        for (int i = 0; i < resint.length; i++) {
            res.add(resint[i]);
        }
        return res;
    }
    
    //  cherche Article à afficher
    public static ArrayList<Affichage> ChercheArticle(Connection con, ArrayList<String> chercher) throws SQLException {
        ArrayList<Affichage> res = new ArrayList<>();
        for (int j = 0; j < chercher.size(); j++) {
            try (PreparedStatement pst = con.prepareStatement(
                    """
                            select Enchere.idArticle, Article.designation,Article.descriptionCourte,Article.descriptionLongue,Article.expedition,Enchere.prix,Enchere.dateDebut,Enchere.dateFin
                            from Enchere
                            inner join Article on Enchere.idArticle = Article.idArticle
                            WHERE strpos(Article.designation,? ) > 0
                              OR strpos(Article.descriptionCourte,? ) > 0
                              OR strpos(Article.descriptionLongue,? ) > 0                

               """
            )) {
                pst.setString(1, chercher.get(j));
                pst.setString(2, chercher.get(j));
                pst.setString(3, chercher.get(j));

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Affichage aff = new Affichage(rs.getInt("idArticle"), rs.getString("designation"),
                                rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin"));
                        int drapeau = 0;
                        for (int i = 0; i < res.size(); i++) {
                            if (res.get(i).getIdArticle() == rs.getInt("idArticle")) {
                                drapeau = 1;
                            }
                        }

                        if (drapeau == 0) {
                            res.add(aff);
                        }

                    }
                }

            }
        }
        return res;
    }

//          
//          
//          
//           for( int i =0;i< article.size();i++){
//               int compteur = 0;
//               for( int j = 0; j<chercher.size();j++){
//            int index1 = article.get(i).designation.indexOf(chercher.get(j));      
//            int index2 = article.get(i).descriptionCourte.indexOf(chercher.get(j));       
//            int index3 = article.get(i).descriptionLongue.indexOf(chercher.get(j));
//            if (index1 != -1 || index1 != -1 ||  index1 != -1 ){
//                compteur = compteur +1;
//            }  
//           }
//               if (compteur == chercher.size()){
//                   res.add(article.get(i));
//               }
//           }
//           return res ;
//}
//chercher en spécifiant les catégories des articles.
//    public static ArrayList<Article> ChercheArticleparCategories(ArrayList<Article> article, ArrayList<String> chercher) {
//        ArrayList<Article> res = new ArrayList<>();
//
//        return res;
//
//    }

    public static class idArticleExisteDejaException extends Exception {
    }

}
