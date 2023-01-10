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
    public Utilisateur(int idUtilisateur, String email, String mdp, String codePostal, String nom, String prenom, int statut) {
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
    public static ArrayList<Utilisateur> afficheTousLesUtilisateur(Connection con, int idUtilisateurCourant) throws SQLException {
        ArrayList<Utilisateur> res = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            try (ResultSet tlu = st.executeQuery("select * from utilisateur")) {
                while (tlu.next()) {
                    if(tlu.getInt("idUtilisateur") !=idUtilisateurCourant ){
                    res.add(new Utilisateur(tlu.getInt("idUtilisateur"), tlu.getString("email"), tlu.getString("mdp"), tlu.getString("codePostal"), tlu.getString("nom"), tlu.getString("prenom"), tlu.getInt("statut")));
                    }
                    }
            }
        }
        return res;
    }

    public static Utilisateur afficheInfoUtilisateur(Connection con, int idUtilisateur) throws SQLException {
        Utilisateur res = new Utilisateur(0, "defaut", "defaut", "defaut", "defaut", "defaut", 0);
        try (PreparedStatement pst = con.prepareStatement(
                """
                select * from Utilisateur where idUtilisateur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.setCodePostal(rs.getString("codePostal"));
                    res.setEmail(rs.getString("email"));
                    res.setIdUtilisateur(rs.getInt("idUtilisateur"));
                    res.setMdp(rs.getString("mdp"));
                    res.setNom(rs.getString("nom"));
                    res.setPrenom(rs.getString("prenom"));
                    res.setStatut(rs.getInt("statut"));
                }
            }
        }
        return res;

    }

    //Effacer dans PGSQL-----------------------   
    public static void deleteUtilisateur(Connection con, int idUtilisateur) throws SQLException {
        //Supprimer le liste des articles enchéris
        System.out.print("2");
        con.setAutoCommit(false);
        try {
         try (PreparedStatement pst = con.prepareStatement(
                """
                delete from ListPosseseur
                where idUtilisateur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.executeUpdate();
        }catch (SQLException ex) {
            System.out.println("1");
        }
        //Supprimer la liste des articles qu'il possèdent qui ont été enchéri (par d'autre utilisateur)
        try (PreparedStatement pst = con.prepareStatement(
                """
                delete from ListPosseseur
                using Article
               where ListPosseseur.idArticle = Article.idArticle
               and Article.posseseur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.executeUpdate();
        }
        //Supprimer la liste des enchères qu'il possède
        try (PreparedStatement pst = con.prepareStatement(
                """
               delete from Enchere
               using Article 
               where Enchere.idArticle = Article.idArticle
               and Article.posseseur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.executeUpdate();
        }
        //Modifie l'achteur des articles qu'il avait le plus enchéri
                try (PreparedStatement pst = con.prepareStatement(
                """
                update Enchere set prix = (
                     select max(ListPosseseur.prix) from ListPosseseur
                    join Enchere on Enchere.idArticle = ListPosseseur.idArticle
                     where Enchere.acheteur = ?),
			   
              acheteur = (
                     select idUtilisateur from ListPosseseur
                      where Enchere.prix = (select max(ListPosseseur.prix) from ListPosseseur
                     join Enchere on Enchere.idArticle = ListPosseseur.idArticle
                     where Enchere.acheteur = ?) and Enchere.acheteur = ?)

               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.setInt(2, idUtilisateur);
            pst.setInt(3, idUtilisateur);
            pst.executeUpdate();
        }
        //Supprimer la liste des articles qu'il possède
        try (PreparedStatement pst = con.prepareStatement(
                """
               delete from Article
               where Article.posseseur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.executeUpdate();
        }
        //Supprime l'utilisateur
        try (PreparedStatement pst = con.prepareStatement(
                """
               delete from Utilisateur
               where Utilisateur.idUtilisateur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.executeUpdate();
        }
        con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
        System.out.print("c'est fait");
    }
    
//Affichage__________________________________________________________________________________________
//Envoie la liste des enchère à Affiche ses enchères en cours
    public static ArrayList<Affichage> afficheSesEnchères(Connection con, int idUtilisateur) throws SQLException {
        ArrayList<Affichage> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
                select Enchere.idArticle, Article.designation,Article.descriptionCourte,Article.descriptionLongue,Article.expedition,Enchere.prix,Enchere.dateDebut,Enchere.dateFin
                from Enchere
               join Article on Enchere.idArticle = Article.idArticle
               where Article.posseseur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Affichage(rs.getInt("idArticle"), rs.getString("designation"),
                            rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin")));
                }
                return res;
            }
        }
    }
////Affiche les enchères terminées et remportées

    public static ArrayList<Affichage> afficheEnchereRemporte(Connection con, int idUtilisateur) throws SQLException {
        ArrayList<Affichage> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
                select Enchere.idArticle, Article.designation,Article.descriptionCourte,Article.descriptionLongue,Article.expedition,Enchere.prix,Enchere.dateDebut,Enchere.dateFin
                from Enchere
                join Article on Enchere.idArticle = Article.idArticle
                where Enchere.acheteur = ? and Enchere.dateFin < ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Affichage(rs.getInt("idArticle"), rs.getString("designation"),
                            rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin")));
                }
                return res;
            }
        }
    }

//Affiche le gain de l'utilisateur
    public static double afficheGain(Connection con, int idUtilisateur) throws SQLException {
        double gain = 0;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select prix,dateFin, prixIni from Enchere 
               join Article on Enchere.idArticle = Article.idArticle
                where Article.posseseur = ? and Enchere.dateFin < ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    //if(java.sql.Date.valueOf(LocalDate.now()).after(rs.getDate("dateFin"))){
                    gain = (rs.getDouble("prix") - rs.getDouble("prixIni")) + gain;
                    //} //Si jamais ca marche pas dans sql
                }
                return gain;
            }
        }
    }
    //Affiche les enchères en cours que possède l'utilisateur/pour lequel il a fait la meilleur offre

    public static ArrayList<Affichage> afficheEnchereRemporteEnCours(Connection con, int idUtilisateur) throws SQLException {
        ArrayList<Affichage> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select Enchere.idArticle, Article.designation,Article.descriptionCourte,Article.descriptionLongue,Article.expedition,Enchere.prix,Enchere.dateDebut,Enchere.dateFin
               from Enchere
               join Article on Enchere.idArticle = Article.idArticle
                where Enchere.acheteur = ? and Enchere.dateFin > ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Affichage(rs.getInt("idArticle"), rs.getString("designation"),
                            rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin")));
                }
                return res;
            }
        }
    }

    //Affiche les enchères pour lesquelles il a enchéri mais qu'il ne possède plus
    public static ArrayList<Affichage> afficheEnchereNonRemporteEnCours(Connection con, int idUtilisateur) throws SQLException {
        ArrayList<Affichage> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                """
               select distinct Enchere.idArticle, Article.designation,Article.descriptionCourte,Article.descriptionLongue,Article.expedition,Enchere.prix,Enchere.dateDebut,Enchere.dateFin
               from Enchere
               inner join Article on Enchere.idArticle = Article.idArticle
               join ListPosseseur on Enchere.idArticle = ListPosseseur.idArticle
               where Enchere.acheteur != ? and Enchere.dateFin >= ? and ListPosseseur.idUtilisateur = ?
               """
        )) {
            pst.setInt(1, idUtilisateur);
            pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            pst.setInt(3, idUtilisateur);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Affichage(rs.getInt("idArticle"), rs.getString("designation"),
                            rs.getString("descriptionCourte"), rs.getString("descriptionLongue"), rs.getInt("expedition"), rs.getDouble("prix"), rs.getDate("dateDebut"), rs.getDate("dateFin")));
                }
                return res;
            }
        }
    }

    //Fonction renvoyant le statut de l'utilisateur
    public static int statutUtilisateur(Connection con, int idUtilisateurCourant) throws SQLException {
        int res = 1;
        try (PreparedStatement pst = con.prepareStatement(
                """
               select Utilisateur.statut
               from Utilisateur
               where Utilisateur.idUtilisateur = ?
               """
        )) {
            pst.setInt(1, idUtilisateurCourant);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res = rs.getInt("statut");
                }
                
            }
        }
     return res;
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

//
//    public static void main(String[] args) {
//        try (Connection con = defautConnect()) {
//            //          System.out.println("connectÃ© !!!");
////            Enchere.creeEnchere(con);
//            //          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//             //          Date dateDebut = new java.sql.Date(simpleDateFormat.parse("25/10/2022").getTime());
//            //           Date dateFin = new java.sql.Date(simpleDateFormat.parse("30/10/2022").getTime());
////            Enchere.createEnchere(con, 0, "vendeur", 0, 0,dateDebut,dateFin, 0, "acheteur");
//
//            //System.out.println(dateDebut);
////            Enchere enche =  new Enchere(0,"vendeur",0,0,dateDebut,dateFin,0,"acheteur");
//            //Utilisateur utilisateur = new Utilisateur("loic.lol@wanadoo.fr","12354","FR-67400","loic","lol",0);
////            System.out.println(java.sql.Date.valueOf(LocalDate.now()));   
//            //           enche.encherir(con,utilisateur,400);
////            Article.creeTableArticle(con);
//            //Article.createArticle(con, "deisnation", "descriptionCourte", "descriptionLongue", 0, 0, 0);
////            Article.createArticle(con, "ticket de tram", " deja utiliser mais en bonne etat", "", 0, 0, 0);
////            Article.createArticle(con, "ticket de bus", "descriptionCourte", "descriptionLongue", 0, 0, 0);
////            Article.createArticle(con, "Tschirt", "deja utiliser mais en bonne etat", "descriptionLongue", 0, 0, 0);
////            ArrayList<String> chercher = new ArrayList<String>();
////            chercher.add("ticket");
////            ArrayList<Article> res = Article.ChercheArticle(Article.actulisteTousArticle(con), chercher);
////            System.out.println(res.get(0).getDesignation());
////            System.out.println(res.get(1).getDesignation());
////            System.out.println(res.get(2).getDesignation());
//            //creeTableUtilisateur(con);
//            //createUtilisateur(con,"loic.lol@wanadoo.fr","12354","FR-67400","loic","lol",0);
//            //afficheTousLesUtilisateur(con);
//            //deleteSchemaUtilisateur(con);
//            //creeTableUtilisateur(con);
//            // createUtilisateur(con, "loic.lol@wanadoo.fr", "blabla", "FR-67400", "loic", "lol", 0);
//            // createUtilisateur(con, "joris.bolos@gmail.com", "pass", "FR-67400", "loic", "lol", 0);
//            //demandeConnection(con,"loic.lol@wanadoo.fr","blabla"));
//            //demandeConnection(con,"loic.lol@wanadoo.fr", "blabla");
//        } catch (Exception ex) {
//            throw new Error(ex);
//        }
//    }
}
