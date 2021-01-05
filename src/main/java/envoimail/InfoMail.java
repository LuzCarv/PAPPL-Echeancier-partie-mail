/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envoimail;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author 96441
 */
public class InfoMail {

    public InfoMail() {
    }
     final static String url="jdbc:postgresql://localhost/pappl";
     final static String motDePass = "zhang99662";
    
    public void enregistrerMail (Boolean estRedevable,String messageAvant, String jourAvant,String messageApres, String jourApres)  {
        
        String messageAvantModif = messageAvant;
        String messageApresModif = messageApres;
        //String messageAvantModif = this.modifierApostrophe(messageAvant);
        //String messageApresModif = this.modifierApostrophe(messageApres);

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url,"postgres", motDePass);
            String requete1 = "BEGIN; "
                     +"UPDATE mail set corps_message=?, jour=? where mail.id_mail=?; "
                     +"UPDATE mail set corps_message=?, jour=? where mail.id_mail=?; "
                     +"End;";

            PreparedStatement  stmt=conn.prepareStatement(requete1);

            stmt.setString(1,messageAvantModif);
            stmt.setInt(2,Integer.parseInt(jourAvant));
            if(estRedevable){
               stmt.setString(3,"redevableAvant"); 
               stmt.setString(6,"redevableApres"); 
            }else{
              stmt.setString(3,"agentAvant");  
              stmt.setString(6,"agentApres"); 
            }       
            stmt.setString(4,messageApresModif);
            stmt.setInt(5,Integer.parseInt(jourApres));
          

            stmt.executeUpdate();
            stmt.close() ;
            conn.close() ; 
        }
    catch (SQLException e) {
             e.printStackTrace();
    }
    catch (java.lang.ClassNotFoundException e) {
        e.printStackTrace();
    }   
       
 
    }
    
    
 
    
    public ArrayList<String> lireInformationMail(Boolean estRedevable) {
           
        try {
            ArrayList<String>lignes=new ArrayList<>();
            Class.forName("org.postgresql.Driver");
            
            Connection conn = DriverManager.getConnection(url,"postgres", motDePass);
            String requete1 =  "SELECT corps_message,jour FROM mail WHERE mail.id_mail=? OR mail.id_mail=?";
            PreparedStatement  stmt = conn.prepareStatement(requete1) ;
            if(estRedevable){
               stmt.setString(1,"redevableAvant"); 
               stmt.setString(2,"redevableApres"); 
            }else{
              stmt.setString(1,"agentAvant");  
              stmt.setString(2,"agentApres"); 
            }
            ResultSet res = stmt.executeQuery();
            
            if (res.next()) {

                do {
                    lignes.add(res.getString("corps_message"));
                    lignes.add(res.getString("jour"));
                } while (res.next());
            }
            System.out.println(lignes.get(0));
            System.out.println(lignes.get(1));
            System.out.println(lignes.get(2));
            System.out.println(lignes.get(3));
            return lignes;
    }catch (SQLException e) {
             e.printStackTrace();
    }
    catch (java.lang.ClassNotFoundException e) {
        e.printStackTrace();
    }
        return null;
}
    
    public String modifierApostrophe(String message){
        int i = message.indexOf("'");
        ArrayList<Integer> positions = new ArrayList<>();
        while(i >= 0) {
            positions.add(i);
            i = message.indexOf("'", i+1);
        }
        StringBuffer str= new StringBuffer(message); 
        for(int position: positions){
            str.insert(position,"'"); 
        }
        String messageFinal = str.toString();
        return messageFinal; 
    }
}

