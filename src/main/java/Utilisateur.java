//package ProjetBdD.gui;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author brenc
 */
public class Utilisateur {

    private String email;
    private String mdp;
    private String codePostale;
    private String nom;
    private String prenom;
    private int statut; //statut 0= administrateur/1 = utilisateur;

//Constructor-------------------------------------------------
    public Utilisateur(String email, String mdp, String codePostale, String nom, String prenom, int statut) {
        this.email = email;
        this.mdp = mdp;
        this.codePostale = codePostale;
        this.nom = nom;
        this.prenom = prenom;
        this.statut = statut;
    }

//Get et Set---------------------------------------------------
    public int getStatut() {
        return statut;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setStatut(int statut) {
        this.statut = statut;
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

    //Creer la table Utilisateur-----------------------------------------------
    public static void creeTableUtilisateur(Connection con)
            throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table utilisateur (
                        email varchar(30) not null unique primary key, 
                        mdp varchar(30) not null,
                        codePostal varchar(30) not null,
                        nom varchar(30) not null,
                        prenom varchar(30) not null,
                        statut integer
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

    //Créer un utilisateur-------------------------------------------------------  
    public static void createUtilisateur(Connection con, String email, String mdp, String codePostal, String nom, String prenom, int statut)
            throws SQLException, EmailExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
                "select email from utilisateur where email = ?")) {
            chercheEmail.setString(1, email);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new EmailExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into utilisateur (email, mdp, codePostal, nom, prenom, statut) values (?,?,?,?,?,?)
                """)) {
                pst.setString(1, email);
                pst.setString(2, mdp);
                pst.setString(3, codePostal);
                pst.setString(4, nom);
                pst.setString(5, prenom);
                pst.setInt(6, statut);
                pst.executeUpdate();
                con.commit();

                // je peux alors rÃ©cupÃ©rer les clÃ©s crÃ©Ã©es comme un result set :
//                try ( ResultSet rid = pst.getGeneratedKeys()) {
//                    // et comme ici je suis sur qu'il y a une et une seule clÃ©, je
//                    // fait un simple next 
//                    rid.next();
//                    // puis je rÃ©cupÃ¨re la valeur de la clÃ© crÃ©Ã© qui est dans la
//                    // premiÃ¨re colonne du ResultSet
//                    int id = rid.getInt(1);
//                    return id;
//                }
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static class EmailExisteDejaException extends Exception {
    }

    public static void demandeNouvelUtilisateur(Connection con) throws SQLException {
        boolean existe = true;
        while (existe) {
            System.out.println("--- creation nouvel utilisateur");
            String email = Lire.S();
            String mdp = Lire.S();
            String codePostal = Lire.S();
            String nom = Lire.S();
            String prenom = Lire.S();
            int statut = Lire.i();
            try {
                createUtilisateur(con, email, mdp, codePostal, nom, prenom, statut);
                existe = false;
            } catch (EmailExisteDejaException ex) {
                System.out.println("cette email existe deja, choisissez en un autre");
            }
        }
    }
    
    public static boolean demandeConnection(Connection con, String email, String pass)throws SQLException{
        boolean res;
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select email,mdp from Utilisateur where email = ? and mdp = ?
               """
        )) {
            pst.setString(1, email);
            pst.setString(2, pass);
            pst.executeUpdate();
          try ( ResultSet rs = pst.executeQuery()) {
                //while (rs.next()) {
                    res = true ;           
                //}   
        return res;
    }catch(SQLException ex) {
       res = false; 
    }
    }
        return res;
    }
    //Lecture dans PGSQL----------------------

    public static void afficheTousLesUtilisateur(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            // pour effectuer une recherche, il faut utiliser un "executeQuery"
            // et non un "executeUpdate".
            // un executeQuery retourne un ResultSet qui contient le rÃ©sultat
            // de la recherche (donc une table avec quelques information supplÃ©mentaire)
            try ( ResultSet tlu = st.executeQuery("select * from utilisateur")) {
                // un ResultSet se manipule un peu comme un fichier :
                // - il faut le fermer quand on ne l'utilise plus
                //   d'oÃ¹ l'utilisation du try(...) ci-dessus
                // - il faut utiliser la mÃ©thode next du ResultSet pour passer
                //   d'une ligne Ã  la suivante.
                //   . s'il y avait effectivement une ligne suivante, next renvoie true
                //   . si l'on Ã©tait sur la derniÃ¨re ligne, next renvoie false
                //   . au dÃ©but, on est "avant la premiÃ¨re ligne", il faut donc
                //     faire un premier next pour accÃ©der Ã  la premiÃ¨re ligne
                //     Note : ce premier next peut renvoyer false si le rÃ©sultat
                //            du select Ã©tait vide
                // on va donc trÃ¨s souvent avoir un next
                //   . dans un if si l'on veut tester qu'il y a bien un rÃ©sultat
                //   . dans un while si l'on veut traiter l'ensemble des lignes
                //     de la table rÃ©sultat

                System.out.println("liste des utilisateurs :");
                System.out.println("------------------------");
                // ici, on veut lister toutes les lignes, d'oÃ¹ le while
                while (tlu.next()) {
                    // Ensuite, pour accÃ©der Ã  chaque colonne de la ligne courante,
                    // on a les mÃ©thode getInt, getString... en fonction du type
                    // de la colonne.

                    // on peut accÃ©der Ã  une colonne par son nom :
                    String email = tlu.getString("email");
                    // ou par son numÃ©ro (la premiÃ¨re colonne a le numÃ©ro 1)
                    String pass = tlu.getString(2);
                    System.out.println(email + " : " + " (" + pass + ")");
                }
            }
        }

    }

    //Effacer dans PGSQL-----------------------   
    public static void deleteSchemaUtilisateur(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            // pour Ãªtre sÃ»r de pouvoir supprimer, il faut d'abord supprimer les liens
            // puis les tables
            // suppression des liens
            try {
                st.executeUpdate(
                        """
                    alter table aime
                        drop constraint fk_aime_u1
                             """);
                System.out.println("constraint fk_aime_u1 dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    alter table aime
                        drop constraint fk_aime_u2
                    """);
                System.out.println("constraint fk_aime_u2 dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate(
                        """
                    drop table utilisateur1
                    """);
                System.out.println("dable aime dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    drop table utilisateur
                    """);
                System.out.println("table utilisateur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
        }
    }
//Envoie la liste des enchère à Affiche ses enchères en cours
    public static ArrayList<Enchere> afficheSesEnchères(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,vendeur,prixIni,prix,dateDebut,dateFin,etat,acheteur
                 from Enchere where vendeur = ?
               """
        )) {
           pst.setString(1,email);
            pst.executeUpdate();
        
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),rs.getString("vendeur"),
                            rs.getInt("prixIni"),rs.getInt("prix"), rs.getDate("dateDebut"),rs.getDate("dateFin"),rs.getInt("etat"),rs.getString("acheteur")));
                }
                return res;
            }
        }
    }
////Affiche les enchères terminées et remportées
    public static ArrayList<Enchere> afficheEnchereRemporte(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,vendeur,prixIni,prix,dateDebut,dateFin,etat,acheteur from Enchere where acheteur = ? and dateFin < ?
               """
        )) {
           pst.setString(1,email);
           pst.setDate(2,java.sql.Date.valueOf(LocalDate.now()));
           pst.executeUpdate();
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),rs.getString("vendeur"),
                            rs.getInt("prixIni"),rs.getInt("prix"), rs.getDate("dateDebut"),rs.getDate("dateFin"),rs.getInt("etat"),rs.getString("acheteur")));
                }
                return res;
            }
        }
    }

    
//Affiche le gain de l'utilisateur
    public static int afficheGain(Connection con, String email) throws SQLException {
        int gain = 0;
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select prix,dateFin
                 from Enchere where vendeur = ? and dateFin > ?
               """
        )) {
           pst.setString(1,email);
           pst.setDate(2,java.sql.Date.valueOf(LocalDate.now()));
            pst.executeUpdate();
        
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    //if(java.sql.Date.valueOf(LocalDate.now()).after(rs.getDate("dateFin"))){
                    gain =  rs.getInt("prix")+ gain;
                    //} //Si jamais ca marche pas dans sql
                    }
                return gain;
            }
        }
    }
 //Affiche les enchères en cours que possède l'utilisateur/pour lequel il a fait la meilleur offre
      public static ArrayList<Enchere> afficheEnchereRemporteEnCours(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,vendeur,prixIni,prix,dateDebut,dateFin,etat,acheteur from Enchere where acheteur = ? and dateFin > ?
               """
        )) {
           pst.setString(1,email);
           pst.setDate(2,java.sql.Date.valueOf(LocalDate.now()));
           pst.executeUpdate();
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),rs.getString("vendeur"),
                            rs.getInt("prixIni"),rs.getInt("prix"), rs.getDate("dateDebut"),rs.getDate("dateFin"),rs.getInt("etat"),rs.getString("acheteur")));
                }
                return res;
            }
        }
    }  
    
    
    
    public static void main(String[] args) {
        try ( Connection con = defautConnect()) {
            System.out.println("connectÃ© !!!");
//            Enchere.creeEnchere(con);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDebut = new java.sql.Date(simpleDateFormat.parse("25/10/2022").getTime());
            Date dateFin = new java.sql.Date(simpleDateFormat.parse("30/10/2022").getTime());
//            Enchere.createEnchere(con, 0, "vendeur", 0, 0,dateDebut,dateFin, 0, "acheteur");
            
            //System.out.println(dateDebut);
            Enchere enche =  new Enchere(0,"vendeur",0,0,dateDebut,dateFin,0,"acheteur");
            Utilisateur utilisateur = new Utilisateur("loic.lol@wanadoo.fr","12354","FR-67400","loic","lol",0);
            System.out.println(java.sql.Date.valueOf(LocalDate.now()));   
            enche.encherir(con,utilisateur,400);
//            Article.creeTableArticle(con);
            //Article.createArticle(con, "deisnation", "descriptionCourte", "descriptionLongue", 0, 0, 0);
//            Article.createArticle(con, "ticket de tram", " deja utiliser mais en bonne etat", "", 0, 0, 0);
//            Article.createArticle(con, "ticket de bus", "descriptionCourte", "descriptionLongue", 0, 0, 0);
//            Article.createArticle(con, "Tschirt", "deja utiliser mais en bonne etat", "descriptionLongue", 0, 0, 0);
//            ArrayList<String> chercher = new ArrayList<String>();
//            chercher.add("ticket");
//            ArrayList<Article> res = Article.ChercheArticle(Article.actulisteTousArticle(con), chercher);
//            System.out.println(res.get(0).getDesignation());
//            System.out.println(res.get(1).getDesignation());
//            System.out.println(res.get(2).getDesignation());
            //creeTableUtilisateur(con);
            //createUtilisateur(con,"loic.lol@wanadoo.fr","12354","FR-67400","loic","lol",0);
            //afficheTousLesUtilisateur(con);
            //deleteSchemaUtilisateur(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
