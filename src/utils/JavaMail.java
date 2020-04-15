/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dell
 */
public class JavaMail {
   public static void sendMail(String recepient,String msg) throws Exception{
        System.out.println("in progress");
        
    Properties properties = new Properties();
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable","true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    String myAccount="bennouri.olfa@gmail.com";
    String password="espritpi12345";
    
    Session session=Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(myAccount, password);
        }      
});
   Message message=prepareMessage(session,myAccount,recepient,msg);
        Transport.send(message);
        System.out.println("sent successfully");
        
    
  
    }
    private static Message prepareMessage(Session session,String myAccount,String recepient,String msg )
    {
        try {
            Message message= new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(msg+ " d'une seance dans votre emploi de temps");
            message.setText("Nous vous informons q'un changement a occuré dans votre emploi de temps  \n ");
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, ex);
        }
          return null;
    }

}
