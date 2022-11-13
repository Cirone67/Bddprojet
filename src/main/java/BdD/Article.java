package BdD;

//package ProjetBdD.gui;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
  //private  idPhoto
  private int idCategorie;
  private String posseseur;

 //Constructor  
  public Article(int idArticle,String designation, String descriptionCourte, String descriptionLongue, int expedition, int categorie, String posseseur) {
        this.idArticle = idArticle;
        this.designation = designation;
        this.descriptionCourte = descriptionCourte;
        this.descriptionLongue = descriptionLongue;
        this.expedition = expedition;
        this.idCategorie = categorie;
        this.posseseur = posseseur;
    }
 
//Get et Set  
    public String getDesignation() {
        return designation;
    }

    public int getIdCategorie() {  
        return idCategorie;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public String getDescriptionCourte() {
        return descriptionCourte;
    }

    public String getDescriptionLongue() {
        return descriptionLongue;
    }

    public int getExpedition() {
        return expedition;
    }

    public int getCategorie() {
        return idCategorie;
    }

    public String getPosseseur() {
        return posseseur;
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

    public void setCategorie(int categorie) {
        this.idCategorie = categorie;
    }

    public void setPosseseur(String posseseur) {
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
        try{
        return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
        }catch(SQLException ex){
        return connectGeneralPostGres("localhost", 5432, "postgres", "postgres", "pass"); 
        }
    }
//Creer la table Article-----------------------------------------------
        public static void creeTableArticle(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table Article (
                        idArticle serial primary key,
                        designation varchar(30),
                        descriptionCourte varchar(100),
                        descriptionLongue varchar(500),
                        expedition integer not null,
                        idCategorie integer not null,
                        posseseur varchar(30) not null
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
    public static void createArticle(Connection con,String designation, String descriptionCourte, String descriptionLongue, int expedition, int idCategorie, String posseseur )
            throws SQLException, idArticleExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
//        try ( PreparedStatement chercheEmail = con.prepareStatement(
//                "select idArticle from Article where idArticle = ?")) {
//            chercheEmail.setInt(1, idArticle);
//            ResultSet testEmail = chercheEmail.executeQuery();
//            if (testEmail.next()) {
//                throw new idArticleExisteDejaException();
//            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into Article (designation, descriptionCourte,  descriptionLongue,  expedition,  idCategorie,  posseseur) values (?,?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, designation);
                pst.setString(2, descriptionCourte);
                pst.setString(3, descriptionLongue);
                pst.setInt(4, expedition);
                pst.setInt(5, idCategorie);
                pst.setString(6, posseseur);
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
//Lecture dans PGSQL----------------------
   public static ArrayList<Article> actulisteTousArticle(Connection con) throws SQLException {
        ArrayList<Article> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,designation,descriptionCourte,descriptionLongue,expedition,idCategorie,posseseur
                 from Article
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
//                  for (int i = 0;i<=res.size();i++){
//                      for(int j = 0; j<=i;j++)
//                    if(i!= 0){
//                    if(rs.getInt("idArticle") !=res.get(j).idArticle ){
                    res.add(new Article(rs.getInt("idArticle"),rs.getString("designation"),
                            rs.getString("descriptionCourte"),rs.getString("descriptionLongue"), rs.getInt("expedition"),rs.getInt("idCategorie"),rs.getString("posseseur")));
//                    }
//                    }
//                    if(i==0){
//                     res.add(new Article(rs.getInt("idArticle"),rs.getString("designation"),
//                            rs.getString("descriptionCourte"),rs.getString("descriptionLongue"), rs.getInt("expedition"),rs.getInt("idCategorie"),rs.getInt("posseseur")));   
//                    }
//                    }
                }
                return res;
            }
        }
    }
   
   //Liste des Articles par 
      //Fonction qui associe nom et désignation d'un article
    public static int nomconnecttodesignation(Connection con, String designation)throws SQLException{
        int res;
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idArticle from Article where designation = ?
               """
        )) {
            pst.setString(1, designation);
            pst.executeUpdate();
          try ( ResultSet rs = pst.executeQuery()) {
                //while (rs.next()) {
                    res = rs.getInt("idArticle");               
                //}   
        return res;
    }
    }
    }
    
    //Décompose une phrase en liste de mot
    public static ArrayList<String> decomposeRecherche(String recherche){
        ArrayList<String> res = new ArrayList<>();
        String[] resint = recherche.split(" ");
        for(int i=0; i < resint.length;i++){
            res.add(resint[i]);
        }
      return res;   
    }
     //  cherche Article à afficher
      public static ArrayList<Article> ChercheArticle (ArrayList<Article> article,ArrayList<String> chercher){
 
          ArrayList<Article> res = new ArrayList<>();
           for( int i =0;i< article.size();i++){
               int compteur = 0;
               for( int j = 0; j<chercher.size();j++){
            int index1 = article.get(i).designation.indexOf(chercher.get(j));      
            int index2 = article.get(i).descriptionCourte.indexOf(chercher.get(j));       
            int index3 = article.get(i).descriptionLongue.indexOf(chercher.get(j));
            if (index1 != -1 || index1 != -1 ||  index1 != -1 ){
                compteur = compteur +1;
            }  
           }
               if (compteur == chercher.size()){
                   res.add(article.get(i));
               }
           }
           return res ;  
      }
        public static class idArticleExisteDejaException extends Exception {
    }  

}
