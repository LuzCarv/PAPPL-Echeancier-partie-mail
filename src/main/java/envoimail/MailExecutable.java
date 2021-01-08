package envoimail;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author 96441
 */
public class MailExecutable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("hola amigoos");
            GetInfo  info=new GetInfo();
            info.VerifierListe();// TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(MailExecutable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailExecutable.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
    
