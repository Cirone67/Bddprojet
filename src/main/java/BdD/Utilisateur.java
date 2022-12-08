package BdD;

//package ProjetBdD.gui;
import gui.PageConnection;
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

    private int idUtilisateur;
    private String email;
    private String mdp;
    private String codePostal;
    private String nom;
    private String prenom;
    private int statut; //statut 0= administrateur/1 = utilisateur;
    private PageConnection pcConnection;

//Constructor-------------------------------------------------
    public Utilisateur(int idUtilisateur,String email, String mdp, String codePostal, String nom, String prenom, int statut) {
        this.idUtilisateur = idUtilisateur;
        this.email = email;
        this.mdp = mdp;
        this.codePostal = codePostal;
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

    public String getCodePostal() {
        return codePostal;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
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
        try (Statement st = con.createStatement()) {
            st.executeUpdate(
                    """
                    create table utilisateur (
                        idUtilisateur serial not null unique primary key,
                        email varchar(30) not null unique, 
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
    public static int createUtilisateur(Connection con, String email, String mdp, String codePostal, String nom, String prenom, int statut)
            throws SQLException, EmailExisteDejaException {
        // je me place dans une transaction pour m'assurer que la sÃ©quence
        // test du nom - crÃ©ation est bien atomique et isolÃ©e
        con.setAutoCommit(false);
        try (PreparedStatement chercheEmail = con.prepareStatement(
                "select email from utilisateur where email = ?")) {
            chercheEmail.setString(1, email);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new EmailExisteDejaException();
            }
            // lors de la creation du PreparedStatement, il faut que je prÃ©cise
            // que je veux qu'il conserve les clÃ©s gÃ©nÃ©rÃ©es
            try (PreparedStatement pst = con.prepareStatement(
                    """
                insert into utilisateur (email, mdp, codePostal, nom, prenom, statut) values (?,?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, email);
                pst.setString(2, mdp);
                pst.setString(3, codePostal);
                pst.setString(4, nom);
                pst.setString(5, prenom);
                pst.setInt(6, statut);
                pst.executeUpdate();
                con.commit();
            }
        } catch (Exception ex) {
            con.rollback();
            return -1;
            //throw ex;
        } finally {
            con.setAutoCommit(true);
        }
        return 1;
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

    public static int demandeConnection(Connection con, String email, String pass) throws SQLException {
        int res;
        res = -1;
//        try (Connection con = defautConnect()) {
        try (PreparedStatement pst = con.prepareStatement(
                """
               select mdp, idUtilisateur from Utilisateur where email = ?
               """
        )) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("mdp").equals(pass)) {
                        res = rs.getInt("idUtilisateur");
                    }
                }
            } catch (SQLException ex) {

            }
        }
//        } catch (Exception ex) {
//            throw new Error(ex);
        return res;
    }

    //    return res;
// Permet de modifier son mdp
    public static int demandeChangerMdp(Connection con, String email, String nvMdp) throws SQLException {
        int res;
        res = -1;

        try (PreparedStatement pst = con.prepareStatement(
                """
               select idUtilisateur from Utilisateur where email = ?
               """
        )) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res = rs.getInt("idUtilisateur");
                }
                if (res != -1) {
                    con.setAutoCommit(false);
                    try (PreparedStatement pst2 = con.prepareStatement(
                            """
               update Utilisateur set mdp = ?  where email = ?
               """
                    )) {
                        pst2.setString(1, nvMdp);
                        pst2.setString(2, email);
                        pst2.executeUpdate();
                        con.commit();
                        con.setAutoCommit(true);

                    }
                }
            } catch (SQLException ex) {

            }
            return res;
        }
    }

    //Lecture dans PGSQL----------------------
    public  static ArrayList<Utilisateur> afficheTousLesUtilisateur(Connection con) throws SQLException {
        ArrayList<Utilisateur> res = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            try (ResultSet tlu = st.executeQuery("select * from utilisateur")) {
                while (tlu.next()) {
                      res.add(new Utilisateur(tlu.getInt("idUtilisateur"),tlu.getString("email"),tlu.getString("mdp"), tlu.getString("codePostal"),tlu.getString("nom"),tlu.getString("prenom"),tlu.getInt("statut")));
                }
            }
        }
    return res;
    }

    //Effacer dans PGSQL-----------------------   
    public static void deleteSchemaUtilisateur(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
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

//Affichage__________________________________________________________________________________________
//Envoie la liste des enchère à Affiche ses enchères en cours
    public static ArrayList<Enchere> afficheSesEnchères(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,prixIni,prix,dateDebut,dateFin,etat,acheteur from Enchere
               join Enchere.idArticle = Article.idArticle
               where Article.posseseur = ?
               """
        )) {
            pst.setString(1, email);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),
                            rs.getDouble("prixIni"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getInt("acheteur")));
                }
                return res;
            }
        }
    }
////Affiche les enchères terminées et remportées

    public static ArrayList<Enchere> afficheEnchereRemporte(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,prixIni,prix,dateDebut,dateFin,acheteur from Enchere where acheteur = ? and dateFin < ?
               """
        )) {
            pst.setString(1, email);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),
                            rs.getDouble("prixIni"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getInt("acheteur")));
                }
                return res;
            }
        }
    }

//Affiche le gain de l'utilisateur
    public static int afficheGain(Connection con, String email) throws SQLException {
        int gain = 0;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select prix,dateFin from Enchere 
               join Enchere.idArticle = Article.idArticle
                where Article.posseseur = ? and Enchere.dateFin > ?
               """
        )) {
            pst.setString(1, email);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    //if(java.sql.Date.valueOf(LocalDate.now()).after(rs.getDate("dateFin"))){
                    gain = rs.getInt("prix") + gain;
                    //} //Si jamais ca marche pas dans sql
                }
                return gain;
            }
        }
    }
    //Affiche les enchères en cours que possède l'utilisateur/pour lequel il a fait la meilleur offre

    public static ArrayList<Enchere> afficheEnchereRemporteEnCours(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,prixIni,prix,dateDebut,dateFin,etat,acheteur from Enchere where acheteur = ? and dateFin > ?
               """
        )) {
            pst.setString(1, email);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),
                            rs.getDouble("prixIni"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getInt("acheteur")));
                }
                return res;
            }
        }
    }

    //Affiche les enchères pour lesquelles il a enchéri mais qu'il ne possède plus
    public static ArrayList<Enchere> afficheEnchereNonRemporteEnCours(Connection con, String email) throws SQLException {
        ArrayList<Enchere> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select idArticle,vendeur,prixIni,prix,dateDebut,dateFin,etat,acheteur from Enchere
               join ListPosseseur on Enchere.idArticle = ListPosseseur.idArticle
               where acheteur != ? and dateFin < ? and encherreur = ?
               """
        )) {
            pst.setString(1, email);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            pst.setString(3, email);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Enchere(rs.getInt("idArticle"),
                            rs.getDouble("prixIni"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getInt("acheteur")));
                }
                return res;
            }
        }
    }
    
    //Si l'utilisateur est un admin, il peut faire des stats: ( ce sont les boutons qui sont affichés ou non.
    //-% d'Enchere active
    public static double statEnchereActive(Connection con) throws SQLException {
        double i = 0;
        double j = 0;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select dateDebut,dateFin from Enchere
               where dateFin > ? and dateDebut < ?
               """
        )) {
            pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    i = i + 1;
                }
            }
        }
        try (PreparedStatement pst = con.prepareStatement(
                """
               select dateDebut,dateFin from Enchere
               """
        )) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    j = j + 1;
                }
            }
        }
        if (j != 0) {
            return (i / j) * 100;
        } else {
            return 0;
        }
    }

    //Prix moyen de l'augmentation des enchères ( moyenne des écarts relatifs)
    public static double statPrix(Connection con) throws SQLException {
        double res = 0;
        int i = 0;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select prixIni,prix from Enchere
               where dateFin < ?
               """
        )) {
            pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    i = i + 1;
                }
                rs.first();
                while (rs.next()) {
                    res = ((rs.getInt("prixIni") - rs.getInt("prix")) / rs.getInt("prixIni")) + res;
                }
                return res / (i - 1); //Je pense -1 car il ne faut pas retenir la 1ere ligne du tableau qui est le nom des colonnes
            }
        }
    }

    public static void main(String[] args) {
        try (Connection con = defautConnect()) {
            //          System.out.println("connectÃ© !!!");
//            Enchere.creeEnchere(con);
            //          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
             //          Date dateDebut = new java.sql.Date(simpleDateFormat.parse("25/10/2022").getTime());
            //           Date dateFin = new java.sql.Date(simpleDateFormat.parse("30/10/2022").getTime());
//            Enchere.createEnchere(con, 0, "vendeur", 0, 0,dateDebut,dateFin, 0, "acheteur");

            //System.out.println(dateDebut);
//            Enchere enche =  new Enchere(0,"vendeur",0,0,dateDebut,dateFin,0,"acheteur");
            //Utilisateur utilisateur = new Utilisateur("loic.lol@wanadoo.fr","12354","FR-67400","loic","lol",0);
//            System.out.println(java.sql.Date.valueOf(LocalDate.now()));   
            //           enche.encherir(con,utilisateur,400);
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
            //creeTableUtilisateur(con);
            // createUtilisateur(con, "loic.lol@wanadoo.fr", "blabla", "FR-67400", "loic", "lol", 0);
            // createUtilisateur(con, "joris.bolos@gmail.com", "pass", "FR-67400", "loic", "lol", 0);
            //demandeConnection(con,"loic.lol@wanadoo.fr","blabla"));
            //demandeConnection(con,"loic.lol@wanadoo.fr", "blabla");
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
