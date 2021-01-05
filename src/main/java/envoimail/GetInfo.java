/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package envoimail;

//import daos.DaoHistorique;
//import daos.DaoHistorique;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.mail.MessagingException;
/**
 *
 * @author 96441
 */
public class GetInfo{
    private InfoMail daomail;

    public GetInfo() {
        this.daomail =new InfoMail();
    }
    
    public void VerifierListe() throws IOException, MessagingException{
        Envoi sendmail=new Envoi();
        ArrayList<DateMail> listDatemail=this.getListeEchenaceMail();
        LocalDate date=LocalDate.now();
        ArrayList<String> jourMessageRedevable=daomail.lireInformationMail(true);
        ArrayList<String> jourMessageAgent=daomail.lireInformationMail(false);
        for (DateMail dm: listDatemail){
            System.out.println("mas" + date.plusDays(Long.parseLong(jourMessageRedevable.get(1))));
            System.out.println("menos" + date.minusDays(Long.parseLong(jourMessageRedevable.get(3))));
           if  (date.plusDays(Long.parseLong(jourMessageRedevable.get(1))).isEqual(dm.getDatedeadline())){
               System.out.println("entro1");
               sendmail.send(dm.getMailredevable(), "Echéance à payer, Date deadline:"+dm.getDatedeadline(),jourMessageRedevable.get(0));
               
           }
           if  (date.minusDays(Long.parseLong(jourMessageRedevable.get(3))).isEqual(dm.getDatedeadline())){
                System.out.println("entro2");
               sendmail.send(dm.getMailredevable(),"Echéance non payée, Date deadline:"+dm.getDatedeadline(),jourMessageRedevable.get(2));
              
           }
           if  (date.plusDays(Long.parseLong(jourMessageAgent.get(1))).isEqual(dm.getDatedeadline())){
               System.out.println("entro3");
               sendmail.send(dm.getMailagent(), "Echéance à payer, Date deadline:"+dm.getDatedeadline(),jourMessageAgent.get(0) + "\n Le redevable est " + dm.getNom());
               
           }
           if  (date.minusDays(Long.parseLong(jourMessageAgent.get(3))).isEqual(dm.getDatedeadline())){
               System.out.println("entro4");
               sendmail.send(dm.getMailagent(), "Echéance non payée, Date deadline:"+dm.getDatedeadline(),jourMessageAgent.get(2) + "\n Le redevable est " + dm.getNom());
               
           }
        } 
    }
    
    public ArrayList<DateMail> getListeEchenaceMail(){
          ArrayList<DateMail> listDatemail = new ArrayList<>();
      
      try {
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(InfoMail.url,"postgres", InfoMail.motDePass);
        String requete1 =  "SELECT echeance.date_deadline, dette.adresse_mail_redevable,agent_comptable.adresse_mail_agent, redevable.nom_redevable "
                        +"FROM echeance JOIN dette ON (echeance.id_dette=dette.id_dette) JOIN agent_comptable ON (dette.id_agent=agent_comptable.id_agent)"
                        +" JOIN redevable ON (dette.adresse_mail_redevable=redevable.adresse_mail_redevable)WHERE echeance.statut_paiement=? AND echeance.statut_annulation=?";
         
        PreparedStatement  stmt = conn.prepareStatement(requete1) ;
        stmt.setBoolean(1, false);
        stmt.setBoolean(2, false);
        ResultSet res = stmt.executeQuery();
        
        if(res.next()){
        
         do{  
             DateMail dm = new DateMail();
             dm.setMailagent(res.getString("adresse_mail_agent"));
             dm.setMailredevable(res.getString("adresse_mail_redevable"));
             dm.setDatedeadline(res.getDate("date_deadline").toLocalDate());
             dm.setNom(res.getString("nom_redevable"));
             listDatemail.add(dm);
        }while (res.next()); 
            
        }
         stmt.close() ;
         conn.close() ; 
        
         }
    catch (SQLException e) {
             e.printStackTrace();
    }
    catch (java.lang.ClassNotFoundException e) {
        e.printStackTrace();
    }   
       return listDatemail; 
    }
}