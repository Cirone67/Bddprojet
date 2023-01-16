package BdD;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author brenc
 */
public class Categorie {

    private int idcategorie;
    private String designation;
    private int idSurCategorie;

//Contructor-----------------------------------------------
    public Categorie(int idcategorie, String designation, int idSurCategorie) {
        this.idcategorie = idcategorie;
        this.designation = designation;
        this.idSurCategorie = idSurCategorie;
    }

//Set et Get-----------------------------------------
    public int getIdcategorie() {
        return idcategorie;
    }

    public String getDesignation() {
        return designation;
    }

    public int getIdSurCategorie() {
        return idSurCategorie;
    }

    public void setIdcategorie(int idcategorie) {
        this.idcategorie = idcategorie;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setIdSurCategorie(int idSurCategorie) {
        this.idSurCategorie = idSurCategorie;
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
//Creer la table Categorie-----------------------------------------------

    public static void creeTableCategorie(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Categorie (
                        idCategorie serial primary key,
                        designation varchar(30) not null unique,
                        idSurCategorie integer
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
    
//Creer Table JoinCategorieArticle-------------------------------------

    public static void creeTableJoinCategorieArticle(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table JoinCategorieArticle (
                        idCategorie integer,
                        idArticle integer
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

//Créer une categorie------------------------------------
    public static void createCategorie(Connection con, String designation, int idSurCategorie)
            throws SQLException, idCategorieExisteDejaException {
        con.setAutoCommit(false);
        try (PreparedStatement chercheEmail = con.prepareStatement(
                "select designation from Categorie where designation = ?")) {
            chercheEmail.setString(1, designation);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new idCategorieExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try (PreparedStatement pst = con.prepareStatement(
                    """
                insert into Categorie (designation,idSurCategorie) values (?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, designation);
                pst.setInt(2, idSurCategorie);
                pst.executeUpdate();
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public static class idCategorieExisteDejaException extends Exception {
    }
    //Créer un JoinCategorieArticle-----------------------------------

    public static void createJoinCategorieArticle(Connection con, int idArticle, int idCategorie)
            throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pst = con.prepareStatement(
                """
                insert into JoinCategorieArticle (idArticle, idCategorie) values (?,?)
                """)) {
            pst.setInt(1, idArticle);
            pst.setInt(2, idCategorie);

            pst.executeUpdate();
            con.commit();
//            }
//        } catch (Exception ex) {
//            con.rollback();
//            throw ex;
//        } finally {
            con.setAutoCommit(true);
        }
    }
    //Renvoie la liste des catégories:--------------------------

    public static List<Categorie> tousLesCategories(Connection con) throws SQLException {
        List<Categorie> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idCategorie,designation,idSurCategorie
                 from Categorie
               """
        )) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Categorie(rs.getInt("idCategorie"),
                            rs.getString("designation"), rs.getInt("IdSurCategorie")));
                }
                return res;
            }
        }
    }

    //Trouver une categorie par sa désignation:
//        public static Categorie Categorieavecdesi(Connection con, String designation) throws SQLException {
//        try (PreparedStatement pst = con.prepareStatement(
//                """
//               select idCategorie,designation,idSurCategorie
//                 from Categorie where designation = ?
//               """
//        )) {
//            pst.setString(1,designation);
//            try (ResultSet rs = pst.executeQuery()) {
//                while (rs.next()) {
//                    Categorie res = new Categorie(rs.getInt("idCategorie"),rs.getString("designation"), rs.getInt("IdSurCategorie"));
//                 return res;              
//                }
//                 
//            }
//        }
//    }
    
//Renvoie la liste des articles dans un groupe de catégorie
    public static ArrayList<Article> articleParCategorie(Connection con, ArrayList<String> desiCategorie) throws SQLException {
        ArrayList<Article> res = new ArrayList<>();
        for (int i = 0; i < desiCategorie.size(); i++) {
            try (PreparedStatement pst = con.prepareStatement(
                    """
               select distinct idArticle,designation,descriptionCourte,descriptionLongue,expedition,URLPhoto,posseseur from Article
               join JoinCategorieArticle on Article.idArticle = JoinCategorieArticle.idArticle
               join JoinCategorieArticle on JoinCategorieArticle.idCategorie = Categorie.idCategorie
               where Categorie.designation = ?
               """
            )) {
                pst.setString(1, desiCategorie.get(i));
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
//                    if(res.size()!= 0){
//                       int m=0;
//                    for(int j=0;j<res.size();j++){
//                      if(res.get(j)){
                        res.add(new Article(rs.getInt("idArticle"), rs.getString("designation"),
                                rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getString("URLPhoto"), rs.getInt("posseseur")));
                    }
                }
//                    }else{
//                      res.add(rs.getInt("idArticle") );  
//                    }                 
            }
            if (desiCategorie.isEmpty()) {
                try (PreparedStatement pst = con.prepareStatement(
                        """
               select distinct idArticle,designation,descriptionCourte,descriptionLongue,expedition,URLPhoto,posseseur from Article
               """
                )) {
                    try (ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            res.add(new Article(rs.getInt("idArticle"), rs.getString("designation"),
                                    rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getString("URLPhoto"), rs.getInt("posseseur")));
                        }
                    }
                }
            }

        }
        return res;
    }

    //Renvoie liste des éléments (Enchères et Articles)  que l'on veut afficher par Catégorie
    public static ArrayList<Affichage> EnchereEtArticleParCategorie(Connection con, String desiCategorie) throws SQLException, IOException {
        ArrayList<Affichage> res = new ArrayList<>();
        //for (int i = 0; i < desiCategorie.size(); i++) {
        try (PreparedStatement pst = con.prepareStatement(
                """
               select Article.URLPhoto,Enchere.idArticle, Article.designation,Article.descriptionCourte,Article.descriptionLongue,Article.expedition,Enchere.prix,Enchere.dateDebut,Enchere.dateFin
               from Enchere
               join Article on Enchere.idArticle = Article.idArticle
               inner join JoinCategorieArticle on Article.idArticle = JoinCategorieArticle.idArticle
               inner join Categorie on JoinCategorieArticle.idCategorie = Categorie.idCategorie
               where Categorie.designation = ?
               """
        )) {
//                System.out.println(desiCategorie);
            pst.setString(1, desiCategorie);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
//                    if(res.size()!= 0){
//                       int m=0;
//                    for(int j=0;j<res.size();j++){
//                      if(res.get(j)){
                    res.add(new Affichage(rs.getInt("idArticle"), rs.getString("designation"),
                            rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getString("URLPhoto")));
                }
            }
//                    }else{
//                      res.add(rs.getInt("idArticle") );  
//                    }                 
        }
//            if (desiCategorie.isEmpty()) {
//                try (PreparedStatement pst = con.prepareStatement(
//                        """
//               select distinct idArticle,designation,descriptionCourte,descriptionLongue,expedition,URLPhoto,posseseur from Article
//               """
//                )) {
//                    try (ResultSet rs = pst.executeQuery()) {
//                        while (rs.next()) {
//                            res.add(new Article(rs.getInt("idArticle"), rs.getString("designation"),
//                                    rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getString("URLPhoto"), rs.getInt("posseseur")));
//                        }
//                    }
//                }
//            }
//
//        }(new FileInputStream(res.get(i).getURLPhoto()) 
//        System.out.println("res à Loic");

    //Fonction pour trouver l'image sur le net
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).getURLPhoto() != null) {
                try{
                System.out.println("photo");
                //Image image = new Image(new URL(res.get(i).getURLPhoto()).openStream() ,100,200,false,false);
                //Image image = new Image(new URL(res.get(i).getURLPhoto()).openStream());
                //InputStream stream = new FileInputStream(res.get(i).getURLPhoto());
                InputStream stream = new URL(res.get(i).getURLPhoto()).openStream();
                BufferedImage image = ImageIO.read(stream);
                ImageView imageView = new ImageView(convertToFxImage(image));
                imageView.setX(10);
                imageView.setY(10);
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
                System.out.println(imageView);
                res.set(i, new Affichage(res.get(i).getIdArticle(), res.get(i).getDesignation(), res.get(i).getDescriptionCourte(), res.get(i).getDescriptionLongue(), res.get(i).getExpedition(), res.get(i).getPrix(), res.get(i).getDateDebut(), res.get(i).getDateFin(), imageView));
                } catch( IOException ex){
                }
            
                }
        }
        return res;
    }
    
    private static Image convertToFxImage(BufferedImage image) {
    WritableImage wr = null;
    if (image != null) {
        wr = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
    }

    return new ImageView(wr).getImage();
}
}
